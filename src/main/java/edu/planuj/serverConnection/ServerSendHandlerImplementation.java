package edu.planuj.serverConnection;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSendHandlerImplementation implements ServerSendHandler {

    public ServerSendHandlerImplementation() {
    }


    public void send(Packable pack, ServerClient client) throws IOException {
        ObjectOutput outputStream = client.getObjectOutput();
        if(outputStream == null)
            throw new IOException("Output stream is null");
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
