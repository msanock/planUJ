package Connection.protocol;

import Connection.manager.ClientPackageVisitor;
import Connection.manager.PackageVisitor;

public interface ClientPackable {
    void accept(ClientPackageVisitor v);

}
