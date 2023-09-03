package Connection.manager;

import Connection.connector.download.ReceiveHandler;
import Connection.connector.upload.SendHandler;

import java.net.ConnectException;
import java.net.Socket;


// ConnectionEvents interface is probably useless now
public interface ConnectionManager {

   void restartService() throws ConnectException;

    void startService() throws ConnectException;

    // some getters


}
