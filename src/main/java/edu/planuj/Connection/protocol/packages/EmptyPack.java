package edu.planuj.Connection.protocol.packages;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.serverConnection.abstraction.ServerClient;


public class EmptyPack implements Packable {
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleEmptyPack(this, sender);
    }
}
