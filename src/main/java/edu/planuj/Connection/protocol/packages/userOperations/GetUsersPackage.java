package edu.planuj.Connection.protocol.packages.userOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class GetUsersPackage extends UUIDHolder implements Packable {
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetUsersPackage(this, sender);
    }
}
