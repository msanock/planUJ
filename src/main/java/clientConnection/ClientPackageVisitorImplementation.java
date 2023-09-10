package clientConnection;

import Connection.manager.ClientPackageVisitor;
import Connection.protocol.packages.UserInfoRequestPackage;

public class ClientPackageVisitorImplementation implements ClientPackageVisitor {

    public ClientPackageVisitorImplementation() {

    }

    @Override
    public void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage) {
        //Not being used right now
    }
}
