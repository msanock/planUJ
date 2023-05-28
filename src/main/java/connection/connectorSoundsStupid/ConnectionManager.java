package connection.connectorSoundsStupid;

import connection.connectorSoundsStupid.upload.Sender;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConnectionManager implements Sender {
    boolean isConnected;
    public abstract void start();
    public abstract void send(Socket receiver, Package pack);

    public abstract void flush();

}
