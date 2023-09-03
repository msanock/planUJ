import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;

import java.net.ConnectException;

public class Client {
    public static void main(String[] args) {
        ClientConnectionManager clientConnectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        try {
            clientConnectionManager.startService();


        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
    }
}
