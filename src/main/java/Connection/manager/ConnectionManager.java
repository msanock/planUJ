package Connection.manager;

import Connection.connector.download.ReceiveHandler;
import Connection.connector.upload.SendHandler;

import java.net.ConnectException;
import java.net.Socket;


// ConnectionEvents interface is probably useless now
public abstract class ConnectionManager {
    protected SendHandler sendHandler;
    protected ReceiveHandler receiveHandler;

    protected static boolean isOnline;


    public abstract void restartService() throws ConnectException;

    public abstract void startService() throws ConnectException;

    // some getters


}
