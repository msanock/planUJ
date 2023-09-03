package Connection.connector.upload;


import Connection.protocol.Packable;

import java.net.Socket;

//

public abstract class SendHandler {
    private boolean isConnected;
    public abstract void send(Packable pack, Socket socket);

}
