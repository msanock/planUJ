package connection.connectorSoundsStupid.upload;

import connection.protocol.Package;

import java.net.Socket;

public interface Sender {
    void send(Socket receiver, Package pack);
}
