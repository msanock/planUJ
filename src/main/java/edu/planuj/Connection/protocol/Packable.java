package edu.planuj.Connection.protocol;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

import java.io.Serializable;

public interface Packable extends Serializable {
     RespondInformation accept(PackageVisitor v, ServerClient sender);

}
