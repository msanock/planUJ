package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import serverConnection.ServerClient;

public class GetTeamsPackage implements Packable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetTeamsPackage(this, sender);
    }
}
