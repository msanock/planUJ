package Connection.protocol;

import Connection.manager.PackageVisitor;

import java.io.Serializable;


/// VISITOR DESIGN PATTERN
public record EmptyPack() implements Serializable, Packable {
    public void accept(PackageVisitor v) {
        v.handleEmptyPack(this);
    }
}
