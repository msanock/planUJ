package edu.planuj.clientConnection.abstraction;

import edu.planuj.Connection.protocol.packages.ResponsePackage;

public interface ConnectionReceiver {

    void update(ResponsePackage pack);
}
