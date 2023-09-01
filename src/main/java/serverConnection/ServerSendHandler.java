package serverConnection;

import Connection.connector.upload.SendHandler;
import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSendHandler extends SendHandler {

    public ServerSendHandler() {

    }
    @Override
    public void send(Packable pack, Socket socket) {

    }
    

    public void send(Packable pack, Client client) throws IOException {
        ObjectOutputStream stream = client.getObjectOutputStream();
        synchronized(stream){
            stream.writeObject(pack);
        }

    }
}
