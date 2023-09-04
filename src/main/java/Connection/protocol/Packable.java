package Connection.protocol;

import Connection.manager.PackageVisitor;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.SocketSelector;

import java.io.Serializable;

public interface Packable extends Serializable {
     RespondInformation accept(PackageVisitor v, ServerClient sender);

}
