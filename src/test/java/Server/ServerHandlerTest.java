package Server;

import Server.database.Engine;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ServerHandlerTest {
    @Test
    void testGetServer() {
        assertDoesNotThrow(() -> {
            IServer server = Mockito.mock(IServer.class);
            Engine engine = Mockito.mock(Engine.class);
            ServerHandler serverHandler = new ServerHandler(server, engine);
            serverHandler.Dispose();
        });
    }
    @Test
    void testNotifyLoginPacket() {
        IServer server = Mockito.mock(IServer.class);
        Engine engine = Mockito.mock(Engine.class);
        LoginPacket packet = Mockito.mock(LoginPacket.class);
        Mockito.when(packet.getUsername()).thenReturn("username");
        Mockito.when(packet.getIp()).thenReturn(null);

        ServerHandler serverHandler = new ServerHandler(server, engine);
        serverHandler.notify(packet);

        Mockito.verify(engine, Mockito.times(1)).addUser("username");
        serverHandler.Dispose();
    }

}