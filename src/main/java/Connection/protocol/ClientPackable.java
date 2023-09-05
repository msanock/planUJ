package Connection.protocol;

import Connection.manager.PackageVisitor;

public interface ClientPackable {
    Packable accept(PackageVisitor v);

}
