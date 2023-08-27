package serverConnection;

import Connection.connector.download.ReceiveHandler;
import Connection.protocol.Packable;

import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerReceiveHandler extends ReceiveHandler {
    ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    public void onNewConnection() {

    }

    @Override
    public void onNewPackage(Packable pack, Socket socket) {


    }

    @Override
    public void onReconnection() {

    }

    @Override
    public void onLostConnection(Socket socket) {

    }
}
