package edu.planuj.Connection.connector.upload;


import edu.planuj.Connection.protocol.Packable;

import java.net.Socket;

//

public abstract class SendHandler {
    private boolean isConnected;
    public abstract void send(Packable pack, Socket socket);

}
