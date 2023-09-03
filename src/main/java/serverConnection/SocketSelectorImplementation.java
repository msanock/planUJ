package serverConnection;

import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.SocketSelector;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// Not the best name for it, generally it should store information about all connected users and their sockets

public class SocketSelectorImplementation implements SocketSelector {
    private final ConcurrentHashMap<Long, ServerClient> loggedClients;
    private final Set<ServerClient>  unspecifiedClients;

    public static SocketSelectorImplementation instance;

    public static SocketSelectorImplementation getInstance() {
        if (instance == null) {
            instance = new SocketSelectorImplementation();
        }
        return instance;
    }

    private SocketSelectorImplementation() {
        unspecifiedClients = ConcurrentHashMap.newKeySet();
        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public void AddNewClient(ServerClient newClient) {
        unspecifiedClients.add(newClient);
    }

    @Override
    public void setClientID(Long clientID, ServerClient client) {
        unspecifiedClients.remove(client);
        loggedClients.put(clientID, client);

    }


}
