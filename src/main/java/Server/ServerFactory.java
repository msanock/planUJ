package Server;

public class ServerFactory {
    public static void startServer() {
        ServerHandler serverHandler = new ServerHandler(new SessionFactory());
    }
}
