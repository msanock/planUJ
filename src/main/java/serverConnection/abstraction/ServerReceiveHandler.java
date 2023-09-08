package serverConnection.abstraction;

import Connection.protocol.Packable;

import java.net.Socket;

public interface ServerReceiveHandler {
    void onNewPackage(Packable pack, ServerClient client);
    void onLostConnection(ServerClient client);

}
