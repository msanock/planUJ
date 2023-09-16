package edu.planuj.serverConnection.abstraction;

import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.Server.sql.DatabaseException;

public interface NotificationPackageVisitor {

    void handleReply(Packable packable);

    void handleTeamNotification(NewTeamNotificationPackage newTeamNotificationPackage, ServerClient recipient) throws DatabaseException;

    void handleTaskNotification(NewTaskNotificationPackage newTaskNotificationPackage, ServerClient recipient) throws DatabaseException;

}
