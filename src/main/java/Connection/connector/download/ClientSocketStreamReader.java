package Connection.connector.download;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Presentation.database.DatabaseFactory;
import Server.database.Database;
import clientConnection.ClientPackageVisitorImplementation;
import clientConnection.ClientReceiveHandler;
import clientConnection.abstraction.ClientRequestHandler;
import oracle.ons.Cli;


import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientSocketStreamReader extends Thread {
    private final ObjectInputStream stream;
    private final Socket socket;
    private final ClientReceiveHandler handler;
    private volatile Boolean active;
    private volatile AtomicBoolean continueReading;

    public ClientSocketStreamReader(Socket socket, ClientReceiveHandler handler) throws IOException {
        this.socket = socket;
        this.stream = new ObjectInputStream(socket.getInputStream());
        this.handler = handler;
        active = false;
        continueReading = new AtomicBoolean(true);

        this.setDaemon(true);
    }

    private ClientPackageVisitor getClientPackageVisitor() {
        return new ClientPackageVisitorImplementation();
    }


    @Override
    public void run() {
        ClientPackageVisitor packageVisitor = getClientPackageVisitor();

        try {
            active = true;
            while(true) {
                    Packable newPackage = (Packable) stream.readObject();
                    if (newPackage == null)
                        break;
                    if (newPackage instanceof ClientPackable)
                        handler.onNewPackage((ClientPackable) newPackage, packageVisitor);
                    /**
                     * else
                     *      subscriber.notify() czy coś takiego
                     */
            }
        } catch (IOException e) {
            handler.onLostConnection();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ClientSocketStreamReader", e);
            active = false;
        } catch (ClassNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ClientSocketStreamReader", e);
        } finally {
            try {
                stream.close();
            } catch (IOException ignore) { }
            try {
                socket.close();
            } catch (IOException ignore) { }
        }
    }


    public boolean getActive() {
        return active;
    }

    public boolean suspendReading() {
        if (!continueReading.compareAndSet(true, false))
            return false;
        while (active){
            try {
                active.wait();
            } catch (InterruptedException ignore) { }

        }
        return true;
    }

    public boolean continueReading() {
        return continueReading.compareAndSet(false, true);
    }
}