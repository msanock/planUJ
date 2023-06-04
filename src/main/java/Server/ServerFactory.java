package Server;

import Server.database.Engine;
import Server.sql.PsqlEngine;
import Server.sql.PsqlEngineFactory;

import java.net.Inet6Address;

public class ServerFactory {
    public static void startServer() {
        PsqlEngine engine = PsqlEngineFactory.engine();

        IServer server = null;
        ServerHandler serverHandler = new ServerHandler(server, engine);
    }
}
