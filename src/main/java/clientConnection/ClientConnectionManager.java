package clientConnection;

import connection.connector.download.ReceiveHandler;
import connection.connector.download.SocketStreamReader;
import connection.connector.upload.SendHandler;
import connection.manager.ConnectionManager;

import java.io.IOException;

import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ClientConnectionManager extends ConnectionManager {
    private Socket serverSocket = null;
    private SocketStreamReader socketStreamReader;


    private boolean connectToServer() {
        try {
            serverSocket = new Socket(ConnectionSettings.HOST, ConnectionSettings.PORT);
            // objectOutputStream = new ObjectOutputStream(newServerSocket.getOutputStream());
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().info("Connection problem " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public void startService() {
        new Thread(() -> {
            int attempts = 0;
            while (!connectToServer()) {
                Logger.getAnonymousLogger().info(attempts + ". ATTEMPT: FAILED");
                attempts++;
                try {
                    TimeUnit.MILLISECONDS.sleep(ConnectionSettings.reconnectTime); // reconnect time
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            socketStreamReader = new SocketStreamReader(serverSocket, new ClientReceiveHandler());

            socketStreamReader.start();

            Logger.getAnonymousLogger().info("Connected +");
        }).start();
    }




}
