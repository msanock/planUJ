package Connection.connector.download;



import serverConnection.ServerClient;

import java.io.IOException;

public class MultiSocketStreamReader {
    //private HashMap<Socket, SocketStreamReader> socketsReaders; //maybe in socket selector ?
    private final ReceiveHandler handler;

    public MultiSocketStreamReader(ReceiveHandler handler) {
        //socketsReaders = new HashMap<>();
        this.handler = handler;

    }

    public ServerSocketStreamReader addNewReader(ServerClient client) throws IOException {
        ServerSocketStreamReader socketStreamReader = new ServerSocketStreamReader(client, handler);
        //socketsReaders.put(socket, socketStreamReader); // maybe in socket selector ?

        //handler.onNewConnection(socket);

        socketStreamReader.start();
        return socketStreamReader;
    }

}
