package clientConnection;

import Connection.connector.upload.SendHandler;
import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;

import java.io.IOException;

public class ClientRequestHandlerImplementation implements ClientRequestHandler {
    ClientSendHandler sendHandler;

    @Override
    public void sendUnrespondablePackage(Packable pack) throws IOException {
        sendHandler.send(pack);
    }

    @Override
    public ResponsePackage sendAndGetResponse(Packable pack) {
        return null;
    }

    @Override
    public boolean isRequestingPossible() {
        return false;
    }
}
