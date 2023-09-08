package clientConnection.abstraction;

import Connection.protocol.packages.ResponsePackage;

public interface ConnectionReceiver {

    void update(ResponsePackage pack);
}
