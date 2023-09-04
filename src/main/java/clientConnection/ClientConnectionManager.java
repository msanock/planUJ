package clientConnection;

import Connection.connector.download.ClientSocketStreamReader;
import Connection.connector.download.ClientSocketStreamReader;
import Connection.connector.upload.SendHandler;
import Connection.manager.ConnectionManager;
import Server.sql.DatabaseException;
import Server.sql.PsqlEngine;

import java.io.IOException;

import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientConnectionManager implements ConnectionManager {

    static private ClientConnectionManager instance;
    private Socket serverSocket;
    private ClientSocketStreamReader socketStreamReader;
    private ClientSendHandler sendHandler;
    private AtomicBoolean isOnline;

    public static ClientConnectionManager getInstance() {
        if (instance == null) {
            instance = new ClientConnectionManager(new ClientSendHandler());

        }
        return instance;
    }

    public ClientConnectionManager(ClientSendHandler sendHandler) {
        isOnline = new AtomicBoolean(false);
        this.sendHandler = sendHandler;
    }


    private boolean openNewSocket() {
        try {
            serverSocket = new Socket(ConnectionSettings.HOST, ConnectionSettings.PORT);
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().info("Connection problem " + e.getMessage() + '\n' + e.getStackTrace());
            return false;
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("IO problem " + e.getMessage() + '\n' + e.getStackTrace());
            return false;
        }
        return true;

    }


    @Override
    public void restartService() throws ConnectException {

    }

    public boolean isOnline() {
        return isOnline.get();
    }


    @Override
    public void startService() throws ConnectException {
        int numberOfTries = 0;
        while (numberOfTries < ConnectionSettings.NUMBER_OF_CONNECTION_TRIES) {
            numberOfTries++;
            if (openNewSocket()) {
                isOnline.set(true);
                Logger.getAnonymousLogger().info("Connected to Server");
                break;
            }
            try {
                TimeUnit.MILLISECONDS.sleep(ConnectionSettings.RECONNECTION_PAUSE);
            } catch (InterruptedException ignored) {
            }
        }

        if (!isOnline.get()) {
            return;
        }

        try {
            startReceiver();
            sendHandler.trySetOutputStream(serverSocket.getOutputStream());
        } catch (IOException e) {
                Logger.getAnonymousLogger("Unable to start : " + e.getMessage() + " " + e.getStackTrace());
        }
    }


    public void startReceiver() throws IOException {
        socketStreamReader = new ClientSocketStreamReader(serverSocket.getInputStream(), new ClientReceiveHandler());
        socketStreamReader.start();

    }


}
