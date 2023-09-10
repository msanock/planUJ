package clientConnection;

import Connection.ObjectOutputFactory;
import Connection.SocketFactory;
import Connection.connector.download.ClientSocketStreamReaderFactory;
import Connection.connector.download.ObjectInputFactory;

import java.util.concurrent.Executors;

public class ClientConnectionFactory {
    private static class Holder{
        private static final ClientConnectionFactory INSTANCE = new ClientConnectionFactory();
    }

    private ClientConnectionFactory() {}

    public static ClientConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public ClientConnectionManager getClientConnection() {
        ClientSendHandler sendHandler = new ClientSendHandler();
        ClientReceiveHandler receiveHandler = new ClientReceiveHandler(sendHandler, Executors.newCachedThreadPool());
        return new ClientConnectionManager(
                sendHandler,
                new ObjectInputFactory(),
                receiveHandler,
                new ClientRequestHandlerImplementation(receiveHandler),
                new ObjectOutputFactory(),
                new SocketFactory(),
                new ClientSocketStreamReaderFactory(),
                new ClientPackageVisitorImplementation()
        );
    }
}
