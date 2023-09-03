package Connection.protocol.packages.userOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import Utils.UserInfo;
import serverConnection.ServerClient;

public class LoginPackage extends UUIDHolder implements Packable {
    UserInfo userInfo;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleLoginPackage(this, sender);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
