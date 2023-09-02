package serverConnection;

import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerSendHandler  {

    public ServerSendHandler() {

    }

    public void send(Packable pack, Socket socket) {

    }


    public void send(Packable pack, ServerClient client) throws IOException {
        ObjectOutputStream stream = client.getObjectOutputStream();
        synchronized(stream){
            stream.writeObject(pack);
        }

    }
}
