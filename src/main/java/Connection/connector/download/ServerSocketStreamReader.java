package Connection.connector.download;

import Connection.protocol.Packable;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.ServerReceiveHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSocketStreamReader extends Thread {
    private final InputStream stream;
    private final ServerReceiveHandler handler;

    private final ServerClient client;
    private final ObjectInputFactory objectInputFactory;

    public ServerSocketStreamReader(
            ServerClient client,
            ServerReceiveHandler handler,
            ObjectInputFactory objectInputFactory
            ) throws IOException {
        this.client = client;
        this.stream = client.getInputStream();
        this.handler = handler;
        this.objectInputFactory = objectInputFactory;

        this.setDaemon(true);
    }


    @Override
    public void run() {
        try {
            ObjectInput objectStream = objectInputFactory.createObjectInput(stream);
            while(true) {
                Packable newPackage = (Packable) objectStream.readObject();
                if (newPackage == null)
                    break;

                Logger.getAnonymousLogger().info("Received package: " + newPackage.getClass().getSimpleName());
                handler.onNewPackage(newPackage, client); // onNewPackage, może coś bez socketa?
            }
        }catch (SocketException e){
            Logger.getAnonymousLogger().log(Level.INFO, "Client disconnected");
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ServerSocketStreamReader", e);
        } catch (ClassNotFoundException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception in ServerSocketStreamReader", e);
        } finally {
            handler.onLostConnection(client);
        }
    }


}
