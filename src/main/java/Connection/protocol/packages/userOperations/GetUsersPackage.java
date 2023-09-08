package Connection.protocol.packages.userOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.abstraction.ServerClient;

public class GetUsersPackage extends UUIDHolder implements Packable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetUsersPackage(this, sender);
    }
}
