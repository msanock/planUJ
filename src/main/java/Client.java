import Server.database.Database;
import client.OperationsOnServerProxy;
import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;
import clientConnection.ClientRequestHandlerImplementation;

import java.net.ConnectException;

public class Client {
    Database database;
    Client() {
        ClientConnectionManager connectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        try {
            connectionManager.startService();

        } catch (ConnectException e) {
            //Platform.exit();
            throw new RuntimeException(e);
        }
        database = new OperationsOnServerProxy(new ClientRequestHandlerImplementation()); // maybe TODO: it should probably also be a singleton or come from Factory, I'm sure debug team will handle this
    }
    public static void main(String[] args) {
        Client client = new Client();
    }
}
