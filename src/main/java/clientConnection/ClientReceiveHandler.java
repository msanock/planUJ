package clientConnection;

import Connection.connector.upload.SendHandler;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import clientConnection.abstraction.ConnectionReceiver;


import java.io.IOException;
import java.net.Socket;


public class ClientReceiveHandler /*extends ReceiveHandler*/ {
    private Socket serversSocket;
    private PackageVisitor packageVisitor;
    private ConnectionReceiver receiver;
    private ClientSendHandler sendHandler;

    public void setReceiver(ConnectionReceiver receiver) {
        // TODO receiver should probably also be able to take different packs,
        // Is this synchronization even useful ???
        //this is a question to debug team
        synchronized (this) {
            while (this.receiver != null) {
                try {
                    this.wait(); // add timeout
                } catch (InterruptedException ignore) {

                }
            }
            this.receiver = receiver;
        }
    }
    public void deleteReceiver(ConnectionReceiver receiver) {
        synchronized (this) {
            if (this.receiver == receiver)
                this.receiver = null;
        }
    }

    //@Override
    public void onNewPackage(ClientPackable pack) {
        if (pack instanceof ResponsePackage)
            receiver.update((ResponsePackage) pack);
        else {
            Packable response = pack.accept(packageVisitor);
            if (response != null) {
                try {
                    sendHandler.send(response);
                } catch (IOException e) {
                    onLostConnection(); // maybe with handling this unsent package
                }
            }
        }
        // I do not like this implementation
    }


   // @Override
    public void onLostConnection() {

    }


}
