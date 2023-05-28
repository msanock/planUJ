package Server;

import Server.sql.Notifiable;

import java.util.ArrayList;

public class ServerHandler implements Notifiable {
    private final ArrayList<Session> sessions;
    private final SessionFactory sessionFactory;

    public ServerHandler (SessionFactory sessionFactory){
        sessions = new ArrayList<Session>();
        this.sessionFactory = sessionFactory;
    }

    public void notify(String message, IConnector sender) {
        if(message == "login"){
            addSession("login", sender);
        }
    }

    public void addSession(String username, IConnector sender){
        Session session = sessionFactory.session(username);
        sessions.add(session);
        sender.send(session.getAdress());
    }
}
