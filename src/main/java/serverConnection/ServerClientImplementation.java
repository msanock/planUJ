package serverConnection;

import Connection.connector.download.ServerSocketStreamReader;
import serverConnection.abstraction.ServerClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ServerClientImplementation implements ServerClient {
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ServerSocketStreamReader socketStreamReader;
    private Long ClientID;

    ServerClientImplementation(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void setSocketStreamReader(ServerSocketStreamReader socketStreamReader, ObjectOutputStream objectOutputStream) throws IOException {
        this.socketStreamReader = socketStreamReader;
        this.objectOutputStream = objectOutputStream;
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
    public void startSocketStreamReader() throws NoStreamReaderException {
        if(socketStreamReader == null)
            throw new NoStreamReaderException("No socket stream reader set");
        socketStreamReader.start();
    }

    @Override
    public Long getClientID() {
        return ClientID;
    }

    @Override
    public ObjectOutput getObjectOutput(){
        return objectOutputStream;
    }
}
