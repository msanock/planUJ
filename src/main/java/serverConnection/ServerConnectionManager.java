package serverConnection;

import clientConnection.ClientReceiveHandler;
import clientConnection.ConnectionSettings;
import Connection.connector.download.MultiSocketStreamReader;
import Connection.connector.download.SocketStreamReader;
import Connection.connector.upload.SendHandler;
import Connection.manager.ConnectionManager;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerConnectionManager extends ConnectionManager {

    private MultiSocketStreamReader multiSocketStreamReader;
    private ServerSendHandler SendHandler;

    ServerConnectionManager() {
        isOnline = false;
    }

    private void acceptNewConnection(ServerSocket serverSocket) throws IOException {

        Socket clientSocket = serverSocket.accept();
        Logger.getAnonymousLogger().info("New Connection");
        multiSocketStreamReader.addNewReader(clientSocket);

    }
    @Override
    public void startService() throws ConnectException {
        isOnline = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(ConnectionSettings.PORT)) {
                multiSocketStreamReader = new MultiSocketStreamReader(new ServerReceiveHandler());
                while (true) {
                    Logger.getAnonymousLogger().info("Ready for connection");
                    acceptNewConnection(serverSocket);

                }
            } catch (Exception exception) {
                Logger.getAnonymousLogger().warning("Server exception: " + exception.getMessage());
            } finally {
                isOnline = false;
                restartService();
            }
        }).start();
    }

    public void restartService() {

    }
}
