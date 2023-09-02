package serverConnection;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

// Not the best name for it, generally it should store information about all connected users and their sockets

//zrób interface chuju
public class SocketSelector {
    private final ConcurrentHashMap<Long, ServerClient> loggedClients;
    private final Set<ServerClient>  unspecifiedClients;

    public SocketSelector() {
        unspecifiedClients = ConcurrentHashMap.newKeySet();
        loggedClients = new ConcurrentHashMap<>();
    }


    public void AddNewClient(ServerClient newClient) {
        unspecifiedClients.add(newClient);
    }



}
