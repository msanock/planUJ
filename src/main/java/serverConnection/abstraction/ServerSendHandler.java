package serverConnection.abstraction;

import Connection.protocol.Packable;

import java.io.IOException;

public interface ServerSendHandler {
    void send(Packable pack, ServerClient client) throws IOException;
}
