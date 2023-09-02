package serverConnection.manager;

import Connection.manager.PackageVisitor;
import Connection.protocol.packs.UserInfoRequestPack;
import clientConnection.ConnectionSettings;
import Connection.connector.download.MultiSocketStreamReader;
import Connection.manager.ConnectionManager;
import serverConnection.ServerClient;
import serverConnection.ServerReceiveHandler;
import serverConnection.ServerSendHandler;
import serverConnection.SocketSelector;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerConnectionManager extends ConnectionManager {

    private MultiSocketStreamReader multiSocketStreamReader;
    private ServerSendHandler sendHandler;
    private PackageVisitor packageVisitor;
    private SocketSelector socketSelector;

    public ServerConnectionManager(ServerSendHandler sendHandler) {
        isOnline = false;
        this.sendHandler = sendHandler;

    }



    @Override
    public void restartService() throws ConnectException {

    }

    @Override
    public void startService() throws ConnectException {
        isOnline = true;
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(ConnectionSettings.PORT)) {
                multiSocketStreamReader = new MultiSocketStreamReader(new ServerReceiveHandler(sendHandler, packageVisitor));
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
        ServerClient newClient = new ServerClient(clientSocket);
        sendHandler.send(new UserInfoRequestPack(), newClient);
        newClient.setSocketStreamReader(multiSocketStreamReader.addNewReader(newClient)); // ??
        socketSelector.AddNewClient(newClient);
    }

    public void acceptNewLogIn() {}
}
