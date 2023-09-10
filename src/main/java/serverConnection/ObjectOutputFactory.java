package serverConnection;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectOutputFactory {
    public ObjectOutputStream createObjectOutput(Socket clientSocket) throws IOException {
        return new ObjectOutputStream(clientSocket.getOutputStream());
    }
}
