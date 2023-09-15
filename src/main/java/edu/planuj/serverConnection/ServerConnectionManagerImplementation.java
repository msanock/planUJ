package edu.planuj.serverConnection;
import edu.planuj.Connection.ObjectOutputFactory;
import edu.planuj.Connection.connector.download.MultiSocketStreamReaderFactory;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;
import edu.planuj.clientConnection.ConnectionSettings;
import edu.planuj.Connection.connector.download.MultiSocketStreamReader;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.ServerConnectionManger;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ServerConnectionManagerImplementation implements ServerConnectionManger {

    private MultiSocketStreamReader multiSocketStreamReader;
    private final ServerSendHandler sendHandler;
    private final PackageVisitor packageVisitor;
    private final SocketSelector socketSelector;
    private boolean isOnline;
    private final ServerSocketFactory serverSocketFactory;
    private final MultiSocketStreamReaderFactory multiSocketStreamReaderFactory;
    private final ServerClientFactory serverClientFactory;
    private final ObjectOutputFactory objectOutputFactory;


    public ServerConnectionManagerImplementation(
            ServerSendHandler sendHandler,
            PackageVisitor packageVisitor,
            SocketSelector socketSelector,
            ServerSocketFactory serverSocketFactory,
            MultiSocketStreamReaderFactory multiSocketStreamReaderFactory,
            ServerClientFactory serverClientFactory,
            ObjectOutputFactory objectOutputFactory
            ) {
        isOnline = false;
        this.serverSocketFactory = serverSocketFactory;
        this.sendHandler = sendHandler;
        this.packageVisitor = packageVisitor;
        this.socketSelector = socketSelector;
        this.multiSocketStreamReaderFactory = multiSocketStreamReaderFactory;
        this.serverClientFactory = serverClientFactory;
        this.objectOutputFactory = objectOutputFactory;
    }



    @Override
    public void restartService() throws ConnectException {
        //TODO
    }

    @Override
    public void startService() throws ConnectException {
        isOnline = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = serverSocketFactory.createServerSocket(ConnectionSettings.PORT)) {
                multiSocketStreamReader = multiSocketStreamReaderFactory.createMultiSocketStreamReader(
                        sendHandler, packageVisitor, SocketSelectorImplementation.getInstance(), Executors.newCachedThreadPool());
                while (true) {
                    Logger.getAnonymousLogger().info("Ready for connection");
                    acceptNewConnection(serverSocket);
                }
            } catch (IOException exception) {
                Logger.getAnonymousLogger().warning("Server exception: " + exception.getMessage());
            } finally {
                isOnline = false;
            }
        }).start();
    }

    void acceptNewConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        Logger.getAnonymousLogger().info("New Connection");

        ServerClient newClient = serverClientFactory.createServerClient(clientSocket);
        newClient.setSocketStreamReader(multiSocketStreamReader.addNewReader(newClient), objectOutputFactory.createObjectOutput(clientSocket));

        socketSelector.AddNewClient(newClient);

        sendHandler.send(new UserInfoRequestPackage(), newClient);
    }

    @Override
    public void acceptLogOut() {
        // TODO
        //should it be here ?
        // probably SocketSelector
    }

    @Override
    public void FinishConnection() {
        // TODO
        // like one above
    }
}
