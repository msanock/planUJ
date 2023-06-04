package Server;

import Server.IServer;

public interface Notifiable {
    void notify(Packet message);
}
