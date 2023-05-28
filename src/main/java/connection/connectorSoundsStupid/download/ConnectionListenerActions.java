package connection.connectorSoundsStupid.download;

/**
 * Listener actions used for handling most basic events both on server and client side
 * Listener implementation will probably be different?
 */
public interface ConnectionListenerActions {

    void onNewConnection();
    void onNewPackage();
    void onReconnection();
    void onLostConnection();
}
