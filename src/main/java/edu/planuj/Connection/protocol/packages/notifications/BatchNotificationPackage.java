package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.clientConnection.NotificationObserverImplementation;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

import java.util.ArrayList;
import java.util.List;

public class BatchNotificationPackage implements NotificationPackage, ClientPackable {
    private final ArrayList<NotificationPackage> notifications;

    public BatchNotificationPackage() {
        notifications = new ArrayList<>();
    }

    public BatchNotificationPackage addNotification(NotificationPackage notification) {
        notifications.add(notification);
        return this;
    }

    public List<NotificationPackage> getNotifications() {
        return notifications;
    }

    @Override
    public void accept(NotificationPackageVisitor v, ServerClient recipient) {
        for (NotificationPackage notification : notifications) {
            notification.accept(v, recipient);
        }
    }

    @Override
    public void accept(ClientPackageVisitor v) {
        for (NotificationPackage notification : notifications) {
            if (notification instanceof ClientPackable)
                ((ClientPackable) notification).accept(v);
        }
    }

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return null;
    }
}
