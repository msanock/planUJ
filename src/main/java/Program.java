
import serverConnection.ServerConnectionFactory;
import serverConnection.abstraction.ServerConnectionManger;

import java.net.ConnectException;
import java.util.logging.Logger;

class Program {
    public static void main(String[] args) {
        ServerConnectionFactory serverConnectionFactory = ServerConnectionFactory.getInstance();
        ServerConnectionManger serverConnectionManger = serverConnectionFactory.createServerConnection();
        try {
            serverConnectionManger.startService();
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().warning("Server is not available " + e.getMessage());
        }
    }
}
