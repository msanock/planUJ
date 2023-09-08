package serverConnection;

import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerSendHandler;
import serverConnection.abstraction.SocketSelector;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSendHandlerImplementation implements ServerSendHandler {

    public ServerSendHandlerImplementation() {
    }


    public void send(Packable pack, ServerClient client) throws IOException {
        ObjectOutputStream outputStream = client.getObjectOutputStream();
        synchronized(outputStream){
            outputStream.writeObject(pack);
        }
    }

    public void sendResponses(RespondInformation respondInformation, SocketSelector socketSelector){
        Map<Long, Packable> responses = respondInformation.getResponses();

        socketSelector.getExistingClientsFromId(responses.keySet().stream().toList())
                .forEach(pair -> {
                    try {
                        send(responses.get(pair.getKey()), pair.getValue());
                    } catch (IOException e) {
                        Logger.getAnonymousLogger().log(Level.SEVERE, "Error sending response to client", e);
                    }
                });
    }



}
