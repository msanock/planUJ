package Connection.protocol.packages.userOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Utils.UserInfo;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import serverConnection.ServerClient;

public class LoginPackage implements Packable {
    UserInfo userInfo;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleLoginPackage(this, sender);
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
