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
        UserInfo userInfo = Mockito.mock(UserInfo.class);
        Mockito.when(userInfo.getUsername()).thenReturn("username");
        LoginPacket packet = Mockito.mock(LoginPacket.class);
        Mockito.when(packet.getAsUserInfo()).thenReturn(userInfo);
        Mockito.when(packet.getIp()).thenReturn(null);

        ServerHandler serverHandler = new ServerHandler(server, engine);
        serverHandler.notify(packet);

        Mockito.verify(engine, Mockito.times(1)).addUser(new UserInfo("username", 0));
        serverHandler.Dispose();
    }

}