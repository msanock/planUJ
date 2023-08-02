package connection.connector.upload;


import connection.protocol.Packable;

public abstract class SendHandler {
    private boolean isConnected;
    public abstract void send(Packable pack);

}
