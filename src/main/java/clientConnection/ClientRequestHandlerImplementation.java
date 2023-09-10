package clientConnection;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;
import clientConnection.abstraction.ClientRequestHandler;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ClientRequestHandlerImplementation implements ClientRequestHandler {
    private final ClientSendHandler sendHandler;
    private final ClientReceiveHandler receiveHandler;
    private AtomicReference<ResponsePackage> response;

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
        response = new AtomicReference<>(null);
        receiveHandler.setReceiver(this);
        sendHandler.send(pack);
        int timeoutCount=0;
        synchronized (this){
            long timeTillTimeout = ConnectionSettings.REQUEST_TIMEOUT;
            while (getCurrentResponse() == null) {
                try {
                    this.wait(timeTillTimeout);
                    timeoutCount++;
                    if(timeoutCount>=ConnectionSettings.TIMEOUT_ITERATIONS){
                        receiveHandler.deleteReceiver(this);
                        throw new SendTimeoutException("Request timed out");
                    }
                } catch (InterruptedException ignore) {}
            }
        }
        receiveHandler.deleteReceiver(this);
        return getCurrentResponse();
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

    ResponsePackage getCurrentResponse(){
        return response.get();
    }
}
