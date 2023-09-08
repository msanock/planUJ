package Server;

import serverConnection.ServerConnectionFactory;
import serverConnection.abstraction.ServerConnectionManger;

import java.net.ConnectException;
import java.util.logging.Logger;

public class ServerApplication {

    public static void main(String[] args) {
        //TODO: add javafx, but this shit doesnt work
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() throws Exception{
        ServerConnectionFactory serverConnectionFactory = ServerConnectionFactory.getInstance();
        ServerConnectionManger serverConnectionManger = serverConnectionFactory.createServerConnection();
        try {
            serverConnectionManger.startService();
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().warning("Server is not available " + e.getMessage());
        }
    }
}
