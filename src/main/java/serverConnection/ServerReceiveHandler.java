package serverConnection;

import Connection.connector.download.ReceiveHandler;
import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;

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
    public void onNewPackage(Packable pack, Client socket) {

        executorService.submit(() -> {

            Packable response = pack.accept(packageVisitor); // maybe something which would also specify other receivers and different responses




        });


    }

    @Override
    public void onLostConnection(Socket socket) {

    }
}
