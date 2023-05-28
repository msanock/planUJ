package connection.connectorSoundsStupid.upload;

import connection.protocol.AbstractPackage;

import java.net.Socket;

public interface Sender {
    void send(Socket receiver, AbstractPackage message);
}
