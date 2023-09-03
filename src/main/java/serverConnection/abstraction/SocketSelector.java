package serverConnection.abstraction;

import serverConnection.abstraction.ServerClient;

public interface SocketSelector {
    void AddNewClient(ServerClient client);

    void setClientID(Long clientID, ServerClient client);

}
