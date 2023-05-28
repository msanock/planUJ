package Server;

import Server.sql.Notifiable;

public class Session implements Notifiable {
    private final String username;
    private final IConnector connector;

    public Session(String username, IConnector connector){
        this.username = username;
        this.connector = connector;
    }

    public String getUsername() {
        return username;
    }

    public String getAdress() {
        return null;
    }

    @Override
    public void notify(String message, IConnector sender) {

    }
}
