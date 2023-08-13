package Server;

import Server.database.Database;
import Utils.UserInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ServerHandlerTest {
    @Test
    void testGetServer() {
        assertDoesNotThrow(() -> {
            IServer server = Mockito.mock(IServer.class);
            Database engine = Mockito.mock(Database.class);
            ServerHandler serverHandler = new ServerHandler(server, engine);
            serverHandler.Dispose();
        });
    }
    @Test
    void testNotifyLoginPacket() {
        IServer server = Mockito.mock(IServer.class);
        Database engine = Mockito.mock(Database.class);
        LoginPacket packet = Mockito.mock(LoginPacket.class);
        Mockito.when(packet.getAsUserInfo().name()).thenReturn("username");
        Mockito.when(packet.getIp()).thenReturn(null);

        ServerHandler serverHandler = new ServerHandler(server, engine);
        serverHandler.notify(packet);

        Mockito.verify(engine, Mockito.times(1)).addUser(new UserInfo("username"));
        serverHandler.Dispose();
    }

}