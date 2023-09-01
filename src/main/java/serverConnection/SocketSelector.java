package serverConnection;

import java.net.Socket;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// Not the best name for it, generally it should store information about all connected users and their sockets
public class SocketSelector {
    private final ConcurrentHashMap<Long, Client> loggedClients;
    private final Set<Client>  unspecifiedClients;

    public SocketSelector() {
        unspecifiedClients = ConcurrentHashMap.newKeySet();
        loggedClients = new ConcurrentHashMap<>();
    }


    public void AddNewClient(Client newClient) {
        unspecifiedClients.add(newClient);
    }
}
