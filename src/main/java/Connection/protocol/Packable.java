package Connection.protocol;

import Connection.manager.PackageVisitor;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.SocketSelector;

public interface Packable {
     RespondInformation accept(PackageVisitor v, ServerClient sender);

}
