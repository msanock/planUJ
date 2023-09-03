package clientConnection;

import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSendHandler {
    ObjectOutputStream outputStream;


    public void send(Packable pack) throws IOException {
        outputStream.writeObject(pack);
    }

    public boolean trySetOutputStream(OutputStream outputStream) {
        try {
            this.outputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
