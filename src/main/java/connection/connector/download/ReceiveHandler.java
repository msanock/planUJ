package connection.connector.download;

import connection.protocol.Packable;

import java.net.Socket;

public abstract class ReceiveHandler {



    public abstract void onNewConnection();

    public abstract void onNewPackage(Packable pack, Socket socket); // usun socketa
    public abstract void onReconnection();
    public abstract void onLostConnection(Socket socket);
}
