package Connection.protocol.packages;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;
import Connection.protocol.ClientPackable;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.userOperations.LoginPackage;
import client.ClientInformation;
import clientConnection.ClientConnectionManager;
import serverConnection.abstraction.ServerClient;

import java.io.Serializable;

public class UserInfoRequestPackage implements Serializable, ClientPackable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender){
        return v.handleUserInfoRequestPack(this, sender);
    }

    @Override
    public void accept(ClientPackageVisitor v) {
        v.handleUserInfoRequestPack(this);
    }
}
