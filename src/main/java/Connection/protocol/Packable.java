package Connection.protocol;

import Connection.manager.PackageVisitor;

public interface Packable {
    public void accept(PackageVisitor v);
}
