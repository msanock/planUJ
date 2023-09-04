package client;

import Presentation.database.DatabaseFactory;
import Server.database.Database;
import Utils.OperationResults.IdResult;
import Utils.UserInfo;
import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;
import clientConnection.ClientRequestHandlerImplementation;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.ConnectException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApplication{
    static Database database;
    //TODO: add javafx, but this shit doesnt work
    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() throws Exception {
        ClientConnectionManager connectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        try {
            connectionManager.startService();
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while connecting to server: ", e);
            throw new RuntimeException(e);
        }
        database = DatabaseFactory.getInstance().getServerDatabase(connectionManager.getSendHandler());

        IdResult result = database.addUser(new UserInfo("bobus amougs", 0));

    }
}
