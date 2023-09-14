package edu.planuj.serverConnection.abstraction;

import edu.planuj.Connection.protocol.Packable;

public interface ServerReceiveHandler {
    void onNewPackage(Packable pack, ServerClient client);
    void onLostConnection(ServerClient client);

}
