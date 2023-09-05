package serverConnection;

import Connection.connector.download.ServerSocketStreamReader;
import serverConnection.abstraction.ServerClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ServerClientImplementation implements ServerClient {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ServerSocketStreamReader socketStreamReader;
    private Long ClientID;

    public ServerClientImplementation(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void setSocketStreamReader(ServerSocketStreamReader socketStreamReader) throws IOException {
        this.socketStreamReader = socketStreamReader;
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return socket.getInputStream();
    }

    @Override
    public void setClientID(Long clientID) {
        ClientID = clientID;
    }

    @Override
    public void startSocketStreamReader() {
        socketStreamReader.start();
    }

    @Override
    public Long getClientID() {
        return ClientID;
    }

    @Override
    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }
}
