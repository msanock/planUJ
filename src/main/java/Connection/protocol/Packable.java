package Connection.protocol;

import Connection.manager.PackageVisitor;
import serverConnection.ServerClient;
import serverConnection.SocketSelector;

public interface Packable {
     RespondInformation accept(PackageVisitor v, ServerClient sender);

}
