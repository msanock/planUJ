package clientConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.packages.ResponsePackage;


import java.net.Socket;


public class ClientReceiveHandler /*extends ReceiveHandler*/ {
    private Socket serversSocket;
    private PackageVisitor packageVisitor;
    private ConnectionReceiver receiver;
    public void setReceiver(ConnectionReceiver receiver) {
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
        else
            pack.accept(packageVisitor);

        // I do not like this implementation
    }


   // @Override
    public void onLostConnection() {

    }


}
