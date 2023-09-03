package clientConnection;

public class ClientConnectionFactory {
    private static ClientConnectionFactory instance;

    private ClientConnectionFactory() {}

    public static ClientConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ClientConnectionFactory();
        }
        return instance;
    }

    public ClientConnectionManager getClientConnection() {
        return new ClientConnectionManager(
                new ClientSendHandler()
        );
    }
}
