package edu.planuj.serverConnection;

import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.SocketSelector;
import javafx.util.Pair;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

// Not the best name for it, generally it should store information about all connected users and their sockets

public class SocketSelectorImplementation implements SocketSelector {
    private final ConcurrentHashMap<Long, ServerClient> loggedClients;
    private final Set<ServerClient>  unspecifiedClients;

    public static class Holder {
        public static final SocketSelectorImplementation INSTANCE = new SocketSelectorImplementation();
    }

    public static SocketSelectorImplementation getInstance() {
        return Holder.INSTANCE;
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

    @Override
    public ServerClient getClientFromId(Long clientId) {
        return loggedClients.get(clientId);
    }

    @Override
    public Stream<Pair<Long, ServerClient>> getExistingClientsFromId(List<Long> list) {
        return loggedClients
                .entrySet()
                .stream()
                .filter((entry) -> list.contains(entry.getKey()))
                .map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
    }

    @Override
    public void removeClient(ServerClient client) {
        loggedClients.remove(client.getClientID());
    }


}
