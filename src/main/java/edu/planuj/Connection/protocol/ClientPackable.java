package edu.planuj.Connection.protocol;

import edu.planuj.Connection.manager.ClientPackageVisitor;

public interface ClientPackable extends Packable {
    void accept(ClientPackageVisitor v);

}
