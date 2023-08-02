package connection.manager;

import connection.protocol.EmptyPack;


public interface PackageVisitor {

    void handleEmptyPack(EmptyPack emptyPack);

    /// ...
}
