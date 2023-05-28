package Server.sql;

import Server.IConnector;

public interface Notifiable {
    void notify(String message, IConnector sender);
}
