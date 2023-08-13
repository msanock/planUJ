package Server;

import Server.sql.PsqlEngine;
public class ServerFactory {
    public static void startServer() {
        PsqlEngine engine = PsqlEngine.getInstance();

        IServer server = null;
        ServerHandler serverHandler = new ServerHandler(server, engine);
    }
}
