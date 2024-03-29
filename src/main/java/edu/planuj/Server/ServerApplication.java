package edu.planuj.Server;

import edu.planuj.serverConnection.ServerConnectionFactory;
import edu.planuj.serverConnection.abstraction.ServerConnectionManger;

import java.net.ConnectException;
import java.util.logging.Logger;

public class ServerApplication {

    public static void main(String[] args) {
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
