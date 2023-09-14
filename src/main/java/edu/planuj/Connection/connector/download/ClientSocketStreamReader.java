package edu.planuj.Connection.connector.download;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.clientConnection.ClientReceiveHandler;
import java.io.IOException;
import java.io.ObjectInput;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientSocketStreamReader extends Thread {
    private final ObjectInput stream;
    private final Socket socket;
    private final ClientReceiveHandler handler;
    private volatile AtomicBoolean continueReading;
    private volatile ClientPackageVisitor packageVisitor;

    public ClientSocketStreamReader(
            Socket socket,
            ClientReceiveHandler handler,
            ObjectInput stream,
            ClientPackageVisitor packageVisitor
    ) throws IOException {
        this.socket = socket;
        this.stream = stream;
        this.handler = handler;
        continueReading = new AtomicBoolean(true);
        this.setDaemon(true);
        this.packageVisitor = packageVisitor;
    }



    @Override
    public void run() {
        try {
            continueReading.set(true);
            while(continueReading.get()) {
                    Packable newPackage = (Packable) stream.readObject();
                    if (newPackage == null)
                        break;
                    if (newPackage instanceof ClientPackable)
                        handler.onNewPackage((ClientPackable) newPackage, packageVisitor);
            }
        } catch (IOException e) {
            handler.onLostConnection();
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ClientSocketStreamReader", e);
        } catch (ClassNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ClientSocketStreamReader", e);
        } finally {
            continueReading.set(false);
            try {
                stream.close();
                socket.close();
            } catch (IOException ignore) { }
        }
    }
}