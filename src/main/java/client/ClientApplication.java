package client;

import Server.database.Database;
import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;
import clientConnection.ClientRequestHandlerImplementation;
import javafx.application.Application;
import javafx.stage.Stage;

import java.net.ConnectException;

public class ClientApplication extends Application {
    Database database;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ClientConnectionManager connectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        try {
            connectionManager.startService();

        } catch (ConnectException e) {
            //Platform.exit();
            throw new RuntimeException(e);
        }
        database = new OperationsOnServerProxy(new ClientRequestHandlerImplementation()); // maybe TODO: it should probably also be a singleton or come from Factory, I'm sure debug team will handle this

    }
}
