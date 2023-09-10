package Connection.protocol.packages;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.abstraction.ServerClient;

import java.io.Serializable;


public class EmptyPack implements Packable {
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleEmptyPack(this, sender);
    }
}
