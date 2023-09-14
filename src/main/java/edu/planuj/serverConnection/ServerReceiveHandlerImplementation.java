package edu.planuj.serverConnection;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerReceiveHandler;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class ServerReceiveHandlerImplementation implements ServerReceiveHandler {
    ExecutorService executorService;
    ServerSendHandler sendHandler;
    SocketSelector socketSelector;
    PackageVisitor packageVisitor;

    public ServerReceiveHandlerImplementation(
            ServerSendHandler sendHandler,
            PackageVisitor packageVisitor,
            SocketSelector socketSelector,
            ExecutorService executorService
    ) {
        this.executorService = executorService;
        this.sendHandler = sendHandler;
        this.packageVisitor = packageVisitor;
        this.socketSelector = socketSelector;
    }

    @Override
    public void onNewPackage(Packable pack, ServerClient serverClient) {
        executorService.submit(() -> {
            RespondInformation respondInformation = pack.accept(packageVisitor, serverClient);
            sendHandler.sendResponses(respondInformation, socketSelector);
        });
    }

    @Override
    public void onLostConnection(ServerClient client) {
        Logger.getGlobal().info("Lost connection with client: " + client.getClientID());
        socketSelector.removeClient(client);
    }

}
