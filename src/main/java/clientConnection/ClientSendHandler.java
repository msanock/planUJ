package clientConnection;

import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientSendHandler {
    private ObjectOutputStream outputStream;
    private AtomicBoolean isOnline;

    public ClientSendHandler() {
        isOnline = new AtomicBoolean(false);
    }

    public boolean isOnline() {
        return isOnline.get();
    }

    public void send(Packable pack) throws IOException {
        outputStream.writeObject(pack);
    }

    public boolean trySetOutputStream(OutputStream outputStream) {
        try {
            this.outputStream = new ObjectOutputStream(outputStream);
            isOnline.set(true);
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
