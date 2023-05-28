package Server;

public class SessionFactory {

    public Session session(String username ) {
        IConnector connector  = null;
        Session session = new Session(username, connector);
        return session;
    }
}
