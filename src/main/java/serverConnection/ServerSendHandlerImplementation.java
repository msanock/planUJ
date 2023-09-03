package serverConnection;

import Connection.protocol.Packable;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerSendHandler;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSendHandlerImplementation implements ServerSendHandler {

    public ServerSendHandlerImplementation() {

    }

//    public void send(Packable pack, Socket socket) {
//
//    }


    public void send(Packable pack, ServerClient client) throws IOException {
        ObjectOutputStream outputStream = client.getObjectOutputStream();
        synchronized(outputStream){
            outputStream.writeObject(pack);
        }

    }

}
