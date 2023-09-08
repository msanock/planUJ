package Connection.manager;

import Connection.protocol.packages.UserInfoRequestPackage;

public interface ClientPackageVisitor {
    void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage);
}
