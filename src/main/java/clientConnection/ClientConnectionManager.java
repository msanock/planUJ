package clientConnection;

import Connection.ObjectOutputFactory;
import Connection.SocketFactory;
import Connection.connector.download.ClientSocketStreamReader;
import Connection.connector.download.ClientSocketStreamReaderFactory;
import Connection.connector.download.ObjectInputFactory;
import Connection.manager.ClientPackageVisitor;
import Connection.manager.ConnectionManager;
import Utils.UserInfo;
import clientConnection.abstraction.ClientRequestHandler;

import javax.net.ServerSocketFactory;
import java.io.IOException;

import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientConnectionManager implements ConnectionManager {

    private Socket serverSocket;
    private ClientSocketStreamReader socketStreamReader;
    private ClientSendHandler sendHandler;
    private AtomicBoolean isOnline;
    private ClientReceiveHandler receiveHandler;
    private ClientRequestHandler requestHandler;
    private ObjectInputFactory objectInputFactory;
    private ObjectOutputFactory objectOutputFactory;
    private SocketFactory serverSocketFactory;
    private ClientSocketStreamReaderFactory clientSocketStreamReaderFactory;
    private ClientPackageVisitor packageVisitor;




    public ClientConnectionManager(
            ClientSendHandler sendHandler,
            ObjectInputFactory objectInputFactory,
            ClientReceiveHandler receiveHandler,
            ClientRequestHandler requestHandler,
            ObjectOutputFactory objectOutputFactory,
            SocketFactory socketFactory,
            ClientSocketStreamReaderFactory clientSocketStreamReaderFactory,
            ClientPackageVisitor packageVisitor
            ) {
        isOnline = new AtomicBoolean(false);
        this.sendHandler = sendHandler;
        this.receiveHandler = receiveHandler;
        this.requestHandler = requestHandler;
        this.objectInputFactory = objectInputFactory;
        this.objectOutputFactory = objectOutputFactory;
        this.serverSocketFactory = socketFactory;
        this.clientSocketStreamReaderFactory = clientSocketStreamReaderFactory;
        this.packageVisitor = packageVisitor;
    }


    private boolean openNewSocket() {
        try {
            serverSocket = serverSocketFactory.createSocket(ConnectionSettings.HOST, ConnectionSettings.PORT);
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().info("Connection problem: " + e.getMessage() + '\n' + e.getStackTrace());
            return false;
        } catch (IOException e) {
            Logger.getAnonymousLogger().info("IO problem: " + e.getMessage() + '\n' + e.getStackTrace());
            return false;
        }
        return true;
    }


    @Override
    public void restartService() throws ConnectException {
        throw new UnsupportedOperationException();
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
            } catch (InterruptedException ignored) {}
        }
        if (!isOnline.get()) {
            throw new ConnectException();
        }
        try {
            startReceiver();
            sendHandler.trySetOutputStream(objectOutputFactory.createObjectOutput(serverSocket));
        } catch (IOException e) {
                Logger.getAnonymousLogger().log(Level.SEVERE,"Unable to start : " + e.getMessage() + " " + e.getStackTrace());
        }
    }



    private void startReceiver() throws IOException {
        socketStreamReader = clientSocketStreamReaderFactory.createClientSocketStreamReader(serverSocket, receiveHandler, objectInputFactory.createObjectInput(serverSocket.getInputStream()), packageVisitor);
        socketStreamReader.start();
    }


    public ClientRequestHandler getRequestHandler() {
        return requestHandler;
    }
}
