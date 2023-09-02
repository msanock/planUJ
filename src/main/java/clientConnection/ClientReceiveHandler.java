package clientConnection;

import Connection.protocol.Packable;


import java.net.Socket;


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
    public void onLostConnection() {

    }
}
