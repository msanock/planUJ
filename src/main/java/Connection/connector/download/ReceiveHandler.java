package Connection.connector.download;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import serverConnection.abstraction.ServerClient;

import java.net.Socket;

// delete
public abstract class ReceiveHandler {
    protected PackageVisitor packageVisitor;

    //public abstract void onNewConnection(Socket socket);

    Socket socket;
    public abstract void onNewPackage(Packable pack, ServerClient client); // usun socketa
//    public abstract void onReconnection();
    public abstract void onLostConnection(Socket socket);
}
