package Connection.connector.download;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import serverConnection.Client;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

public abstract class ReceiveHandler {
    protected PackageVisitor packageVisitor;

    //public abstract void onNewConnection(Socket socket);

    Socket socket;
    public abstract void onNewPackage(Packable pack, Client client); // usun socketa
//    public abstract void onReconnection();
    public abstract void onLostConnection(Socket socket);
}
