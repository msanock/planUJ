package edu.planuj.Connection.connector.download;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.clientConnection.ClientReceiveHandler;

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
