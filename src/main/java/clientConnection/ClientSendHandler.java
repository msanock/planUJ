package clientConnection;

import Connection.protocol.Packable;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ClientSendHandler {
    private ObjectOutput outputStream;
    private AtomicBoolean isOnline;

    public ClientSendHandler() {
        isOnline = new AtomicBoolean(false);
    }

    public boolean isOnline() {
        return isOnline.get();
    }

    public void send(Packable pack) throws IOException {
        if (!isOnline.get()) {
            throw new IOException("No output stream");
        }
        outputStream.writeObject(pack);
    }

    public void trySetOutputStream(ObjectOutput outputStream) {
        this.outputStream = outputStream;
        isOnline.set(true);
    }
}
