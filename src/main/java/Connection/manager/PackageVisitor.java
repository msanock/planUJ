package Connection.manager;

import Connection.protocol.packs.EmptyPack;
import Connection.protocol.Packable;
import Connection.protocol.packs.UserInfoRequestPack;


public interface PackageVisitor {

    Packable handleEmptyPack(EmptyPack emptyPack);

    Packable handleUserInfoRequestPack(UserInfoRequestPack userInfoRequestPack);



    /// ...
}
