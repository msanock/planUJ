package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.packages.UserInfoRequestPackage;
import clientConnection.ConnectionSettings;
import Connection.connector.download.MultiSocketStreamReader;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerConnectionManger;
import serverConnection.abstraction.SocketSelector;
import serverConnection.abstraction.ServerSendHandler;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.logging.Logger;

public class ServerConnectionManagerImplementation implements ServerConnectionManger {

    private MultiSocketStreamReader multiSocketStreamReader;
    private final ServerSendHandler sendHandler;
    private final PackageVisitor packageVisitor;
    private final SocketSelector socketSelector;
    private boolean isOnline;


    public ServerConnectionManagerImplementation(ServerSendHandler sendHandler, PackageVisitor packageVisitor, SocketSelector socketSelector) {
        isOnline = false;
        this.sendHandler = sendHandler;
        this.packageVisitor = packageVisitor;
        this.socketSelector = socketSelector;
    }



    @Override
    public void restartService() throws ConnectException {

    }

    @Override
    public void startService() throws ConnectException {
        isOnline = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(ConnectionSettings.PORT)) {
                multiSocketStreamReader = new MultiSocketStreamReader(new ServerReceiveHandlerImplementation(sendHandler, packageVisitor));
                while (true) {
                    Logger.getAnonymousLogger().info("Ready for connection");
                    acceptNewConnection(serverSocket);
                }
            } catch (Exception exception) {
                Logger.getAnonymousLogger().warning("Server exception: " + exception.getMessage());
            } finally {
                isOnline = false;
            }
        }).start();
    }

    private void acceptNewConnection(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();
        Logger.getAnonymousLogger().info("New Connection");

        ServerClient newClient = new ServerClientImplementation(clientSocket);
        newClient.setSocketStreamReader(multiSocketStreamReader.addNewReader(newClient));
        //newClient.startSocketStreamReader();

        socketSelector.AddNewClient(newClient);

        sendHandler.send(new UserInfoRequestPackage(), newClient);
    }

    @Override
    public void acceptLogOut() {
        //should it be here ?
        // probably SocketSelector
    }

    @Override
    public void FinishConnection() {
        // like one above
    }
}
