package Connection.protocol.packs;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.ServerClient;

import java.io.Serializable;


/// VISITOR DESIGN PATTERN
public class EmptyPack implements Serializable, Packable {
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleEmptyPack(this, sender);
    }
}
