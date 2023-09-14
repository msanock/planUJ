package edu.planuj.Connection.protocol.packages.userOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Utils.UserInfo;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class LoginPackage extends UUIDHolder implements Packable {
    UserInfo userInfo;

    public LoginPackage(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleLoginPackage(this, sender);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
