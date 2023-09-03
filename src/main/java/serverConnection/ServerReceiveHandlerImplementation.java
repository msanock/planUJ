package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerReceiveHandler;
import serverConnection.abstraction.ServerSendHandler;
import serverConnection.abstraction.SocketSelector;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerReceiveHandlerImplementation implements ServerReceiveHandler {
    ExecutorService executorService;
    ServerSendHandler sendHandler;
    SocketSelector socketSelector;
    PackageVisitor packageVisitor;

    public ServerReceiveHandlerImplementation(ServerSendHandler sendHandler, PackageVisitor packageVisitor) {
        executorService = Executors.newCachedThreadPool();
        this.sendHandler = sendHandler;
        this.packageVisitor = packageVisitor;
    }

    @Override
    public void onNewPackage(Packable pack, ServerClient serverClient) {
        executorService.submit(() -> {
            RespondInformation respondInformation = pack.accept(packageVisitor, serverClient);
            sendOutResponses(respondInformation, socketSelector);
        });
    }

    @Override
    public void onLostConnection(ServerClient client) {

    }

    private void sendOutResponses(RespondInformation respondInformation,SocketSelector socketSelector){
        //TODO
        // shouldn't it be in ServerSendHandler ?
    }

}
