package serverConnection.abstraction;

import Connection.manager.ConnectionManager;

import java.io.IOException;
import java.net.ServerSocket;

public interface ServerConnectionManger extends ConnectionManager {
    void acceptLogOut();
    void FinishConnection();
}
