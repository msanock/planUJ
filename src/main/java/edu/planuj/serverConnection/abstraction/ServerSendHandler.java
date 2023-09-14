package edu.planuj.serverConnection.abstraction;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;

import java.io.IOException;

public interface ServerSendHandler {
    void send(Packable pack, ServerClient client) throws IOException;
    void sendResponses(RespondInformation respondInformation, SocketSelector socketSelector);
}
