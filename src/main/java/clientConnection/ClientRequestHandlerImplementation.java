package clientConnection;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import clientConnection.abstraction.ClientRequestHandler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ClientRequestHandlerImplementation implements ClientRequestHandler {
    ClientSendHandler sendHandler;
    ClientReceiveHandler receiveHandler;

    AtomicReference<ResponsePackage> response;

    public ClientRequestHandlerImplementation(ClientReceiveHandler clientReceiveHandler){
        this.sendHandler = clientReceiveHandler.getSendHandler();
        receiveHandler = clientReceiveHandler;
        response = new AtomicReference<>(null);
    }

    @Override
    public void sendUnrespondablePackage(Packable pack) throws IOException {
        sendHandler.send(pack);
    }

    @Override
    public ResponsePackage sendAndGetResponse(Packable pack) throws IOException {
        receiveHandler.setReceiver(this);
        sendHandler.send(pack);
        response = new AtomicReference<>(null);
        synchronized (this){
            long timeTillTimeout = ConnectionSettings.REQUEST_TIMEOUT;
            while (response.get() == null) {
                try {
                    this.wait(timeTillTimeout);
                    // TODO update timeTillTimeout!
                } catch (InterruptedException ignore) {
                    //hope that it isn't anything serious
                }
            }
        }
        receiveHandler.deleteReceiver(this);
        return response.get();
    }

    @Override
    public boolean isRequestingPossible() {
        return false;
    }

    @Override
    public void update(ResponsePackage pack) {
        synchronized (this) {
            response.set(pack);
            this.notify();
        }
    }
}
