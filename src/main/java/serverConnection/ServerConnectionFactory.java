package serverConnection;

import Connection.connector.download.MultiSocketStreamReaderFactory;
import Presentation.database.DatabaseFactory;
import Server.database.Database;
import serverConnection.abstraction.ServerConnectionManger;
import serverConnection.abstraction.SocketSelector;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerConnectionFactory {

    private static class Holder{
        private static final ServerConnectionFactory INSTANCE = new ServerConnectionFactory();
    }

    private ServerConnectionFactory() {}

    public static ServerConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public ServerConnectionManger createServerConnection() {
        SocketSelector socketSelector = SocketSelectorImplementation.getInstance();
        Database database = DatabaseFactory.getInstance().getLocalDatabase();
        return new ServerConnectionManagerImplementation(
                new ServerSendHandlerImplementation(),
                new ServerPackageVisitorImplementation(database, socketSelector),
                socketSelector,
                new ServerSocketFactory() {
                    @Override
                    public ServerSocket createServerSocket(int i) throws IOException {
                        return new ServerSocket(i);
                    }

                    @Override
                    public ServerSocket createServerSocket(int i, int i1) throws IOException {
                        return null;
                    }

                    @Override
                    public ServerSocket createServerSocket(int i, int i1, InetAddress inetAddress) throws IOException {
                        return null;
                    }
                },
                new MultiSocketStreamReaderFactory(),
                new ServerClientFactory(),
                new ObjectOutputFactory()
        );
    }


}
