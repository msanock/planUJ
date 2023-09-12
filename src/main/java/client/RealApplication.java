package client;

import Presentation.database.DatabaseFactory;
import Server.database.Database;
import Utils.TaskInfo;
import Utils.UserInfo;
import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RealApplication extends Application {
    private static Database database;

    public static Database getDatabase() {
        return database;
    }

    @Override
    public void start(Stage stage) throws IOException, DatabaseFactory.DatabaseFactoryException {
        ClientConnectionManager connectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        database = DatabaseFactory.getInstance().getServerDatabase(connectionManager.getRequestHandler());

        FXMLLoader fxmlLoader = new FXMLLoader(RealApplication.class.getResource("base-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();



        try {
            connectionManager.startService();
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while connecting to server: ", e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}
