package clientConnection;

import connection.connector.download.ReceiveHandler;
import connection.connector.download.SocketStreamReader;
import connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ClientReceiveHandler extends ReceiveHandler {
    private Socket serversSocket;


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
