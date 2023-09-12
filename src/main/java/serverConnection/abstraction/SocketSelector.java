package serverConnection.abstraction;

import javafx.util.Pair;

import java.util.List;
import java.util.stream.Stream;

public interface SocketSelector {
    void AddNewClient(ServerClient client);

    void setClientID(Long clientID, ServerClient client);

    ServerClient getClientFromId(Long clientId);

    Stream<Pair<Long, ServerClient>> getExistingClientsFromId(List<Long> list);

    void removeClient(ServerClient client);

}
