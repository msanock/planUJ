package Connection.protocol;

import Connection.manager.PackageVisitor;
import serverConnection.abstraction.ServerClient;
import Connection.protocol.Notification;

public interface ClientPackable {
    Notification accept(PackageVisitor v);

}
