package Connection.manager;

import Connection.protocol.EmptyPack;


public interface PackageVisitor {

    void handleEmptyPack(EmptyPack emptyPack);

    /// ...
}
