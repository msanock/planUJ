package edu.planuj.Connection.connector.download;

import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;

public class ServerSocketStreamReaderFactory {
    public ServerSocketStreamReader createServerSocketStreamReader(ServerClient client, ObjectInputFactory objectInputFactory, ServerReceiveHandler handler) throws IOException {
        return new ServerSocketStreamReader(client, handler, objectInputFactory);
    }
}
