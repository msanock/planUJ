package Connection.protocol.packages;

import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.userOperations.LoginPackage;
import clientConnection.ClientConnectionManager;
import serverConnection.abstraction.ServerClient;

import java.io.Serializable;

public class UserInfoRequestPackage implements Packable, Serializable, ClientPackable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender){
        return v.handleUserInfoRequestPack(this, sender);
    }

    @Override
    public Packable accept(PackageVisitor v) {
        return new LoginPackage(ClientConnectionManager.getUserInfo());
    }
}
