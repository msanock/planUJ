package serverConnection;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serverConnection.abstraction.ServerClient;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SocketSelectorImplementationTest {

    @Test
    void getInstance() {
        //given
        SocketSelectorImplementation socketSelectorImplementation = SocketSelectorImplementation.getInstance();
        SocketSelectorImplementation socketSelectorImplementation2 = SocketSelectorImplementation.getInstance();

        //then
        assertEquals(socketSelectorImplementation, socketSelectorImplementation2);
    }

    @Test
    void addGetSetNewClient() {
        //given
        SocketSelectorImplementation socketSelectorImplementation = SocketSelectorImplementation.getInstance();
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        Mockito.when(serverClient.getClientID()).thenReturn(1L);

        //when
        socketSelectorImplementation.AddNewClient(serverClient);
        socketSelectorImplementation.setClientID(1L, serverClient);

        //then
        assertEquals(serverClient, socketSelectorImplementation.getClientFromId(serverClient.getClientID()));
    }

    @Test
    void getExistingClientsFromId() {
        //given
        SocketSelectorImplementation socketSelectorImplementation = SocketSelectorImplementation.getInstance();
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        ServerClient serverClient2 = Mockito.mock(ServerClient.class);
        Mockito.when(serverClient.getClientID()).thenReturn(1L);
        Mockito.when(serverClient2.getClientID()).thenReturn(2L);

        //when
        socketSelectorImplementation.AddNewClient(serverClient);
        socketSelectorImplementation.AddNewClient(serverClient2);
        socketSelectorImplementation.setClientID(1L, serverClient);
        socketSelectorImplementation.setClientID(2L, serverClient2);

        //then
        Stream<Pair<Long, ServerClient>> clients = socketSelectorImplementation.getExistingClientsFromId(List.of(1L, 2L));
        List<Pair<Long, ServerClient>> clientsList = clients.toList();
        assertEquals(2, clientsList.size());
        assertEquals(1L, clientsList.get(0).getKey());
        assertEquals(2L, clientsList.get(1).getKey());
        assertEquals(serverClient, clientsList.get(0).getValue());
        assertEquals(serverClient2, clientsList.get(1).getValue());
    }

    @Test
    void noLoggedClients(){
        //given
        SocketSelectorImplementation socketSelectorImplementation = SocketSelectorImplementation.getInstance();
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        ServerClient serverClient2 = Mockito.mock(ServerClient.class);
        Mockito.when(serverClient.getClientID()).thenReturn(1L);
        Mockito.when(serverClient2.getClientID()).thenReturn(2L);

        //when
        socketSelectorImplementation.AddNewClient(serverClient);
        socketSelectorImplementation.AddNewClient(serverClient2);

        //then
        assertNull(socketSelectorImplementation.getClientFromId(1L));
        assertNull(socketSelectorImplementation.getClientFromId(2L));
        List<Pair<Long, ServerClient>> clients = socketSelectorImplementation.getExistingClientsFromId(List.of(1L, 2L)).toList();
        assertEquals(0, clients.size());
    }
}