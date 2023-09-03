package Connection.protocol.packages;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.abstraction.ServerClient;

import java.io.Serializable;

public class UserInfoRequestPackage implements Packable, Serializable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender){
        return v.handleUserInfoRequestPack(this, sender);
    }
}
