package Connection.manager;

import java.net.ConnectException;


// ConnectionEvents interface is probably useless now
public interface ConnectionManager {

   void restartService() throws ConnectException;

    void startService() throws ConnectException;


    // some getters


}
