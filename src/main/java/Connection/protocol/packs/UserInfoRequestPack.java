package Connection.protocol.packs;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.ServerClient;

import java.io.Serializable;

public class UserInfoRequestPack implements Packable, Serializable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender){
        return v.handleUserInfoRequestPack(this, sender);
    }
}
