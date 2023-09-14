package edu.planuj.Connection.connector.download;



import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;

public class MultiSocketStreamReader {
    private final ServerReceiveHandler handler;
    private final ObjectInputFactory objectInputFactory;
    private final ServerSocketStreamReaderFactory serverSocketStreamReaderFactory;

    MultiSocketStreamReader(ServerReceiveHandler handler, ObjectInputFactory objectInputFactory, ServerSocketStreamReaderFactory serverSocketStreamReaderFactory) {
        this.handler = handler;
        this.objectInputFactory = objectInputFactory;
        this.serverSocketStreamReaderFactory = serverSocketStreamReaderFactory;
    }

    public ServerSocketStreamReader addNewReader(ServerClient client) throws IOException {
        ServerSocketStreamReader socketStreamReader = serverSocketStreamReaderFactory.createServerSocketStreamReader(client, objectInputFactory, handler);
        socketStreamReader.start();
        return socketStreamReader;
    }

}
