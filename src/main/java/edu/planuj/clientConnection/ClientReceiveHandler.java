package edu.planuj.clientConnection;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.clientConnection.abstraction.ConnectionReceiver;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;


public class ClientReceiveHandler {
    private AtomicReference<ConnectionReceiver> receiver;
    private final ClientSendHandler sendHandler;
    private final ExecutorService executorService;

    public ClientReceiveHandler(ClientSendHandler clientSendHandler, ExecutorService executorService){
        this.sendHandler = clientSendHandler;
        this.executorService = executorService;
        receiver = new AtomicReference<>(null);
    }

    public void setReceiver(ConnectionReceiver receiver) {
        // TODO receiver should probably also be able to take different packs,
        // Is this synchronization even useful ???
        //this is a question to debug team
        synchronized (this) {
            while (this.receiver.get() != null) {
                try {
                    this.wait();
                } catch (InterruptedException ignore) {}
            }
            this.receiver.set(receiver);
        }
    }
    public void deleteReceiver(ConnectionReceiver receiver) {
        synchronized (this) {
            this.receiver.compareAndSet(receiver, null);
        }
    }

    public void onNewPackage(ClientPackable pack, ClientPackageVisitor packageVisitor){
        Logger.getAnonymousLogger().info("New package received");
        if (pack instanceof ResponsePackage) {
            if(receiver.get() == null){
                Logger.getAnonymousLogger().severe("Received package without receiver, package " + pack.toString() + " will be ignored");
                return;
            }
            receiver.get().update((ResponsePackage) pack);
        }else {
            executorService.submit(() -> {
                pack.accept(packageVisitor);
            });
        }
    }


    public void onLostConnection() {
        Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Lost connection to server");
    }


    public ClientSendHandler getSendHandler() {
        return sendHandler;
    }

    ConnectionReceiver getReceiver() {
        return receiver.get();
    }

}
