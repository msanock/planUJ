package connection.protocol;

import connection.manager.PackageVisitor;

public interface Packable {
    public void accept(PackageVisitor v);
}
