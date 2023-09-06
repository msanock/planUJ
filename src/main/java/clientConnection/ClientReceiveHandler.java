package clientConnection;

import Connection.connector.upload.SendHandler;
import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import clientConnection.abstraction.ConnectionReceiver;
import oracle.ons.Cli;


import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;


public class ClientReceiveHandler /*extends ReceiveHandler*/ {
    private AtomicReference<ConnectionReceiver> receiver;
    private final ClientSendHandler sendHandler;
    private final ExecutorService executorService;

    public ClientReceiveHandler(ClientSendHandler clientSendHandler){
        this.sendHandler = clientSendHandler;
        executorService = Executors.newCachedThreadPool();
        receiver = new AtomicReference<>(null);
    }

    public void setReceiver(ConnectionReceiver receiver) {
        // TODO receiver should probably also be able to take different packs,
        // Is this synchronization even useful ???
        //this is a question to debug team
        synchronized (this) {
            while (this.receiver.get() != null) {
                try {
                    this.wait(); // add timeout
                } catch (InterruptedException ignore) {

                }
            }
            this.receiver.set(receiver);
        }
    }
    public void deleteReceiver(ConnectionReceiver receiver) {
        synchronized (this) {
            this.receiver.compareAndSet(receiver, null);
        }
    }

    //@Override
    public void onNewPackage(ClientPackable pack, ClientPackageVisitor packageVisitor){
        Logger.getAnonymousLogger().info("New package received");
        if (pack instanceof ResponsePackage)
            receiver.get().update((ResponsePackage) pack);
        else {
            executorService.submit(() -> {
                pack.accept(packageVisitor);
            });
        }
    }


   // @Override
    public void onLostConnection() {
        Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Lost connection to server");
    }


    public ClientSendHandler getSendHandler() {
        return sendHandler;
    }
}
