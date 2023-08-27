package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.EmptyPack;

public class ServerPackageVisitor implements PackageVisitor {

    @Override
    public void handleEmptyPack(EmptyPack emptyPack) {

    }
}
