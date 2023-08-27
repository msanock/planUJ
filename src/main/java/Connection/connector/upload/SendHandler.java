package Connection.connector.upload;


import Connection.protocol.Packable;

public abstract class SendHandler {
    private boolean isConnected;
    public abstract void send(Packable pack);

}
