package edu.planuj.serverConnection.abstraction;

import edu.planuj.Connection.manager.ConnectionManager;

public interface ServerConnectionManger extends ConnectionManager {
    void acceptLogOut();
    void FinishConnection();
}
