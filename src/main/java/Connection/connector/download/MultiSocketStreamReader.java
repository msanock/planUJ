package Connection.connector.download;



import serverConnection.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MultiSocketStreamReader {
    //private HashMap<Socket, SocketStreamReader> socketsReaders; //maybe in socket selector ?
    private final ReceiveHandler handler;

    public MultiSocketStreamReader(ReceiveHandler handler) {
        //socketsReaders = new HashMap<>();
        this.handler = handler;

    }

    public ServerSocketStreamReader addNewReader(Client client) throws IOException {
        ServerSocketStreamReader socketStreamReader = new ServerSocketStreamReader(client, handler);
        //socketsReaders.put(socket, socketStreamReader); // maybe in socket selector ?

        //handler.onNewConnection(socket);

        socketStreamReader.start();
        return socketStreamReader;
    }

}
