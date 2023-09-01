package Connection.protocol.packs;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;

import java.io.Serializable;

public class UserInfoRequestPack implements Packable, Serializable {
    @Override
    public Packable accept(PackageVisitor v) {
        return v.handleUserInfoRequestPack(this);
    }
}
