package Connection.connector.download;

import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerReceiveHandler;

import java.io.IOException;

public class ServerSocketStreamReaderFactory {
    public ServerSocketStreamReader createServerSocketStreamReader(ServerClient client, ObjectInputFactory objectInputFactory, ServerReceiveHandler handler) throws IOException {
        return new ServerSocketStreamReader(client, handler, objectInputFactory);
    }
}
