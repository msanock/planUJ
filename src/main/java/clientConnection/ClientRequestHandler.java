package clientConnection;

import Connection.protocol.Packable;
import Connection.protocol.packages.ResponsePackage;

import java.io.IOException;

public interface ClientRequestHandler {
    void sendUnrespondablePackage(Packable pack) throws IOException;
    ResponsePackage sendAndGetResponse(Packable pack);
    boolean isRequestingPossible();

}
