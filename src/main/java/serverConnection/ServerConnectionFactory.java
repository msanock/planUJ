package serverConnection;

import Presentation.database.DatabaseFactory;
import Server.database.Database;
import serverConnection.abstraction.ServerConnectionManger;
import serverConnection.abstraction.SocketSelector;

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
                socketSelector
        );
    }


}
