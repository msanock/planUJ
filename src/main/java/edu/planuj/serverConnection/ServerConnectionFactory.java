package edu.planuj.serverConnection;

import edu.planuj.Connection.ObjectOutputFactory;
import edu.planuj.Connection.connector.download.MultiSocketStreamReaderFactory;
import edu.planuj.Presentation.database.DatabaseFactory;
import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.PsqlEngine;
import edu.planuj.serverConnection.abstraction.ServerConnectionManger;
import edu.planuj.serverConnection.abstraction.SocketSelector;

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
                new ServerSendHandlerImplementation(
                        new NotificationPackageVisitorImplementation((PsqlEngine) database)
                ),
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
