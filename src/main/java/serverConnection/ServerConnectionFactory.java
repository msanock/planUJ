package serverConnection;

import Presentation.database.DatabaseFactory;
import Server.database.Database;
import serverConnection.abstraction.ServerConnectionManger;
import serverConnection.abstraction.SocketSelector;

public class ServerConnectionFactory {

    private static ServerConnectionFactory instance;

    private ServerConnectionFactory() {}

    public static ServerConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ServerConnectionFactory();
        }
        return instance;
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
