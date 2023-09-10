package Connection.connector.download;

import Connection.manager.ClientPackageVisitor;
import clientConnection.ClientReceiveHandler;

import java.io.IOException;
import java.io.ObjectInput;
import java.net.Socket;

public class ClientSocketStreamReaderFactory {
    public ClientSocketStreamReader createClientSocketStreamReader(
            Socket socket,
            ClientReceiveHandler handler,
            ObjectInput objectInput,
            ClientPackageVisitor packageVisitor
    ) throws IOException {
        return new ClientSocketStreamReader(socket, handler, objectInput, packageVisitor);
    }
}
