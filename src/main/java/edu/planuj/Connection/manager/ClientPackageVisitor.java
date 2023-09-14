package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;

public interface ClientPackageVisitor {
    void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage);
}
