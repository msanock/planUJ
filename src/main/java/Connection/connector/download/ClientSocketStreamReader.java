package Connection.connector.download;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Presentation.database.DatabaseFactory;
import Server.database.Database;
import clientConnection.ClientPackageVisitorImplementation;
import clientConnection.ClientReceiveHandler;
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
        try {
            Database database = DatabaseFactory.getInstance().getServerDatabase(handler);
            return new ClientPackageVisitorImplementation(database);
        } catch (DatabaseFactory.DatabaseFactoryException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while connecting to server, sender not online: ", e);
            return null;
        }
    }


    @Override
    public void run() {
        ClientPackageVisitor packageVisitor = getClientPackageVisitor();
        if(packageVisitor == null) {
            return;
        }

        try {
            active = true;
            while(true) {
                if (continueReading.get()) {
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
                else {
                    synchronized (active) {
                        active = false;
                        active.notify();
                    }
                    try {
                        continueReading.wait();
                    } catch (InterruptedException ignore) { }
                    if (continueReading.get())
                        active = true;
                }
            }
        } catch (Exception e) {
            handler.onLostConnection();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ClientSocketStreamReader", e);
            active = false;

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