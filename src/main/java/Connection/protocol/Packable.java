package Connection.protocol;

import Connection.manager.PackageVisitor;

public interface Packable {
     Packable accept(PackageVisitor v);

}
