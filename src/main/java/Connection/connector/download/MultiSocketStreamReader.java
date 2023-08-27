package Connection.connector.download;



import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MultiSocketStreamReader {
    private HashMap<Socket, SocketStreamReader> socketsReaders;
    private final ReceiveHandler handler; // ??? no chyba nie

    public MultiSocketStreamReader(ReceiveHandler handler) {
        socketsReaders = new HashMap<>();
        this.handler = handler;

    }

    public void addNewReader(Socket socket) {
        SocketStreamReader socketStreamReader  = new SocketStreamReader(socket, handler);
        socketsReaders.put(socket, socketStreamReader);
        handler.onNewConnection();
        socketStreamReader.start();
    }

}
