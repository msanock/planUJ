package Connection.protocol.packages.userOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.ServerClient;

public class GetUsersPackage implements Packable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetUsersPackage(this, sender);
    }
}
