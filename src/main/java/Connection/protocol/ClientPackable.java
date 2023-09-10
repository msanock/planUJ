package Connection.protocol;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;

public interface ClientPackable extends Packable {
    void accept(ClientPackageVisitor v);

}
