package serverConnection;

import Connection.connector.download.ReceiveHandler;
import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerReceiveHandler extends ReceiveHandler {
    ExecutorService executorService;
    ServerSendHandler sendHandler;
    SocketSelector socketSelector;

    public ServerReceiveHandler(ServerSendHandler sendHandler, PackageVisitor packageVisitor) {
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

    private void sendOutResponses(RespondInformation respondInformation,SocketSelector socketSelector){
        //TODO
    }

    @Override
    public void onLostConnection(Socket socket) {

    }
}
