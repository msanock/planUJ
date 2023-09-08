package clientConnection;

public class ClientConnectionFactory {
    private static class Holder{
        private static final ClientConnectionFactory INSTANCE = new ClientConnectionFactory();
    }

    private ClientConnectionFactory() {}

    public static ClientConnectionFactory getInstance() {
        return Holder.INSTANCE;
    }

    public ClientConnectionManager getClientConnection() {
        return new ClientConnectionManager(
                new ClientSendHandler()
        );
    }
}
