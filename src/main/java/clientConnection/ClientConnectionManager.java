package clientConnection;

import Connection.connector.download.ClientSocketStreamReader;
import Connection.connector.download.ClientSocketStreamReader;
import Connection.manager.ConnectionManager;

import java.io.IOException;

import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ClientConnectionManager extends ConnectionManager {
    private Socket serverSocket;
    private ClientSocketStreamReader socketStreamReader;
    private ClientSendHandler sendHandler;

    ClientConnectionManager() {
        isOnline = false;
    }


    private boolean openNewSocket() {
        try {
            serverSocket = new Socket(ConnectionSettings.HOST, ConnectionSettings.PORT);
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().info("Connection problem " + e.getMessage());
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    @Override
    public void restartService() throws ConnectException {

    }

    @Override
    public void startService() throws ConnectException {
        new Thread(() -> {
            int numberOfTries = 0;
            while (numberOfTries < ConnectionSettings.NUMBER_OF_CONNECTION_TRIES) {
                numberOfTries++;
                if (openNewSocket()) {
                    isOnline = true;
                    Logger.getAnonymousLogger().info("Connected to Server");
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(ConnectionSettings.RECONNECTION_PAUSE);
                } catch (InterruptedException ignored) {
                }
            }

        }).start();

        if (!isOnline)
            throw new ConnectException();

        try {
            startReceiver();
            sendHandler = new ClientSendHandler(serverSocket);

        } catch (IOException e) {
            throw new ConnectException();
        }

    }

    public void startReceiver() throws IOException {
        socketStreamReader = new ClientSocketStreamReader(serverSocket.getInputStream(), new ClientReceiveHandler());
        socketStreamReader.start();
    }


}
