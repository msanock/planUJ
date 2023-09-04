package serverConnection.abstraction;

import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;

import java.io.IOException;

public interface ServerSendHandler {
    void send(Packable pack, ServerClient client) throws IOException;
    void sendResponses(RespondInformation respondInformation, SocketSelector socketSelector);
}
