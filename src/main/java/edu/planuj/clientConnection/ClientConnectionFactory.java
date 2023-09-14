package edu.planuj.clientConnection;

import edu.planuj.Connection.ObjectOutputFactory;
import edu.planuj.Connection.SocketFactory;
import edu.planuj.Connection.connector.download.ClientSocketStreamReaderFactory;
import edu.planuj.Connection.connector.download.ObjectInputFactory;

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
