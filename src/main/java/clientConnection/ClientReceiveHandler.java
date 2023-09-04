package clientConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;


import java.net.Socket;


public class ClientReceiveHandler /*extends ReceiveHandler*/ {
    private Socket serversSocket;
    private PackageVisitor packageVisitor;


//    @Override
//    public void onNewConnection(Socket socket) {
//
//    }

    //@Override
    public void onNewPackage(ClientPackable pack) {
        pack.accept(packageVisitor);
    }

//    @Override
//    public void onReconnection() {
//
//    }

   // @Override
    public void onLostConnection() {

    }
}
