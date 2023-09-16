package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

public interface NotificationPackage {
    void accept(NotificationPackageVisitor v, ServerClient recipient);
}
