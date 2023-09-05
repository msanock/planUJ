package clientConnection.abstraction;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;

import java.io.IOException;

public interface ClientRequestHandler extends ConnectionReceiver {
    void sendUnrespondablePackage(Packable pack) throws IOException;
    ResponsePackage sendAndGetResponse(Packable pack) throws IOException;
    boolean isRequestingPossible();

}
