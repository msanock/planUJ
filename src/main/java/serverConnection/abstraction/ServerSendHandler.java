package serverConnection.abstraction;

import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;

import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;

public interface ServerSendHandler {
    void send(Packable pack, ServerClient client) throws IOException;
    void sendResponses(RespondInformation respondInformation, SocketSelector socketSelector);
}
