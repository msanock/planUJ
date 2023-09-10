package serverConnection;

import serverConnection.abstraction.ServerClient;

import java.net.Socket;

public class ServerClientFactory {

    public ServerClient createServerClient(Socket socket) {
        return new ServerClientImplementation(socket);
    }
}
