package clientConnection;

import Connection.protocol.packages.ResponsePackage;

public interface ConnectionReceiver {

    void update(ResponsePackage pack);
}
