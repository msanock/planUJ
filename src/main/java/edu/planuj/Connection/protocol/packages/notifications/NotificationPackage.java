package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

public interface NotificationPackage extends ClientPackable {
    void accept(NotificationPackageVisitor v, ServerClient recipient);
}
