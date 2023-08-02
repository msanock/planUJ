package connection.connector.download;

import connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.channels.Selector;
import java.util.ArrayList;

public class MultiStreamReader extends Thread {
    private final ArrayList<Socket> sockets;
    private final ReceiveHandler handler; // ??? no chyba nie
    private boolean isActive;

    public MultiStreamReader(ReceiveHandler handler) {
        sockets = new ArrayList<>();
        this.handler = handler;

        handler.onNewConnection();
        this.setDaemon(true);
    }



    @Override
    public void run() {
        

    }


    public boolean isActive() {
        return isActive;
    }
}
