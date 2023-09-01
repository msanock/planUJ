package serverConnection;

import Connection.connector.download.ServerSocketStreamReader;
import Connection.connector.download.ServerSocketStreamReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ServerSocketStreamReader socketStreamReader;
    private Long ClientID;

    public Client(Socket socket) {
        this.socket = socket;
    }

    public void setSocketStreamReader(ServerSocketStreamReader socketStreamReader) throws IOException {
        this.socketStreamReader = socketStreamReader;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
