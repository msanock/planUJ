package Connection.connector.download;

import Connection.protocol.Packable;
import serverConnection.ServerClient;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ServerSocketStreamReader extends Thread {
    private final InputStream stream;
    private final ReceiveHandler handler;
    private boolean isActive;

    private final ServerClient client;

    public ServerSocketStreamReader(ServerClient client, ReceiveHandler handler) throws IOException {
        this.client = client;
        this.stream = client.getInputStream();
        this.handler = handler;
        isActive = false;

        this.setDaemon(true);
    }


    @Override
    public void run() {
        //handler.onNewConnection(socket);

        try {
            ObjectInputStream objectStream = new ObjectInputStream(stream);
            isActive = true;
            while(true) {
                Packable newPackage = (Packable) objectStream.readObject();
                if (newPackage == null)
                    break;

                handler.onNewPackage(newPackage, client); // onNewPackage, może coś bez socketa?
            }
        } catch (Exception e) {
//
            // ???
            isActive = false;
            //handler.onLostConnection(socket);
        }
    }


    public boolean isActive() {
        return isActive;
    }
}
