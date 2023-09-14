package edu.planuj.Connection.protocol.packages;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.serverConnection.abstraction.ServerClient;

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
