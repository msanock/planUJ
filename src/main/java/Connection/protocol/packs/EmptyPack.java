package Connection.protocol.packs;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;

import java.io.Serializable;


/// VISITOR DESIGN PATTERN
public class EmptyPack implements Serializable, Packable {
    public Packable accept(PackageVisitor v) {
        return v.handleEmptyPack(this);
    }
}
