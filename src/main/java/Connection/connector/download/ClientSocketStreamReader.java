package Connection.connector.download;

import Connection.protocol.Packable;
import clientConnection.ClientReceiveHandler;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ref.Cleaner;
import java.net.Socket;

public class ClientSocketStreamReader extends Thread {
    private final Socket socket;
    private final ClientReceiveHandler handler;
    private boolean isActive;

    public ClientSocketStreamReader(Socket socket, ClientReceiveHandler handler) {
        this.socket = socket;
        this.handler = handler;
        isActive = false;


        this.setDaemon(true);
    }


    @Override
    public void run() {
        try {
            ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
            isActive = true;
            while(true) {
                Packable newPackage = (Packable) stream.readObject();
                if (newPackage == null)
                    break;

                handler.onNewPackage(newPackage); // onNewPackage, może coś bez socketa?
            }
        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // ???
            isActive = false;
            handler.onLostConnection(socket);
        }
    }


    public boolean isActive() {
        return isActive;
    }
}