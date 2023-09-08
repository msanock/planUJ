package Connection.connector.download;



import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;

public class MultiSocketStreamReader {
    private final ServerReceiveHandler handler;

    public MultiSocketStreamReader(ServerReceiveHandler handler) {

        this.handler = handler;

    }

    public ServerSocketStreamReader addNewReader(ServerClient client) throws IOException {
        ServerSocketStreamReader socketStreamReader = new ServerSocketStreamReader(client, handler);
        socketStreamReader.start();
        return socketStreamReader;
    }

}
