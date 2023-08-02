package connection.connector.download;

import connection.protocol.Packable;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class SocketStreamReader extends Thread {
    private final Socket socket;
    private final ReceiveHandler handler; // ??? no chyba nie
    private boolean isActive;

    public SocketStreamReader(Socket socket, ReceiveHandler handler) {
        this.socket = socket;
        this.handler = handler;
        isActive = false;

        handler.onNewConnection();
        this.setDaemon(true);
    }



    @Override
    public void run() {
        try {
            ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
            isActive = true;
            while(true) {
                Packable newPackage = (Packable) stream.readObject();
                handler.onNewPackage(newPackage, socket); // onNewPackage, może coś bez socketa?
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
