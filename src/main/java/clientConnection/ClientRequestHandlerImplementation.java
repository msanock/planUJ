package clientConnection;

import Connection.connector.upload.SendHandler;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;

import java.io.IOException;
import java.util.Timer;

public class ClientRequestHandlerImplementation implements ClientRequestHandler {
    ClientSendHandler sendHandler;
    ClientReceiveHandler receiveHandler;

    ResponsePackage response;

    public ClientRequestHandlerImplementation(ClientReceiveHandler clientReceiveHandler, ClientSendHandler sendHandler){
        this.sendHandler = sendHandler;
        receiveHandler = clientReceiveHandler;
    }

    @Override
    public void sendUnrespondablePackage(Packable pack) throws IOException {
        sendHandler.send(pack);
    }

    @Override
    public ResponsePackage sendAndGetResponse(Packable pack) throws IOException {
        receiveHandler.setReceiver(this);
        sendHandler.send(pack);
        response = null;
        synchronized (this){
            long timeTillTimeout = ConnectionSettings.REQUEST_TIMEOUT;
            while (response == null) {
                try {
                    this.wait(timeTillTimeout);
                    // TODO update timeTillTimeout!
                } catch (InterruptedException ignore) {
                    //hope that it isn't anything serious
                }
            }
        }
        receiveHandler.deleteReceiver(this);
        return response;
    }

    @Override
    public boolean isRequestingPossible() {
        return false;
    }

    @Override
    public void update(ResponsePackage pack) {
        synchronized (this) {
            response = pack;
            this.notify();
        }
    }
}
