package clientConnection;

import Connection.connector.download.ReceiveHandler;
import Connection.connector.download.SocketStreamReader;
import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ClientReceiveHandler /*extends ReceiveHandler*/ {
    private Socket serversSocket;


//    @Override
//    public void onNewConnection(Socket socket) {
//
//    }

    //@Override
    public void onNewPackage(Packable pack) {

    }

//    @Override
//    public void onReconnection() {
//
//    }

   // @Override
    public void onLostConnection(Socket socket) {

    }
}
