package Connection;

import java.io.IOException;
import java.net.Socket;

public class SocketFactory {
    public Socket createSocket(String host, int port) throws IOException {
        return new Socket(host, port);
    }
}
