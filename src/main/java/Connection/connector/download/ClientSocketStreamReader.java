package Connection.connector.download;

import Connection.protocol.Packable;
import clientConnection.ClientReceiveHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;

public class ClientSocketStreamReader extends Thread {
    private final ObjectInputStream stream;
    private final ClientReceiveHandler handler;
    private boolean isActive;

    public ClientSocketStreamReader(InputStream stream, ClientReceiveHandler handler) throws IOException {
        this.stream = new ObjectInputStream(stream);;
        this.handler = handler;
        isActive = false;


        this.setDaemon(true);
    }


    @Override
    public void run() {
        try {

            isActive = true;
            while(true) {
                Packable newPackage = (Packable) stream.readObject();
                if (newPackage == null)
                    break;

                handler.onNewPackage(newPackage);
            }
        } catch (Exception e) {
            handler.onLostConnection();
            // ???
            isActive = false;

        }
    }


    public boolean isActive() {
        return isActive;
    }
}