package edu.planuj.serverConnection;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.notifications.NotificationPackage;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.ServerSendHandler;
import edu.planuj.serverConnection.abstraction.SocketSelector;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSendHandlerImplementation implements ServerSendHandler {
    private final NotificationPackageVisitor notificationPackageVisitor;
    public ServerSendHandlerImplementation(NotificationPackageVisitor notificationPackageVisitor) {
        this.notificationPackageVisitor = notificationPackageVisitor;
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
        Map<Long, List<Packable>> responses = respondInformation.getResponses();

        socketSelector.getExistingClientsFromId(responses.keySet().stream().toList())
                .forEach(pair -> {
                    responses.get(pair.getKey()).forEach(response-> {
                        try{
                            if (response instanceof NotificationPackage) {
                                ((NotificationPackage) response).accept(notificationPackageVisitor, pair.getValue());
                            }
                            Logger.getAnonymousLogger().log(Level.INFO, "Sending " + response + " response to client: " + pair.getValue().getClientID());
                            send(response, pair.getValue());
                        } catch (IOException e) {
                            Logger.getAnonymousLogger().log(Level.SEVERE, "Error sending response to client", e);
                        }
                    });
                });
    }



}
