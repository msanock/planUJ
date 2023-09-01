package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.packs.EmptyPack;
import Connection.protocol.Packable;
import Connection.protocol.packs.UserInfoRequestPack;

public class ServerPackageVisitor implements PackageVisitor {

    @Override
    public Packable handleEmptyPack(EmptyPack emptyPack) {
        return null;
    }

    @Override
    public Packable handleUserInfoRequestPack(UserInfoRequestPack userInfoRequestPack) {
        return null;
    }

}
