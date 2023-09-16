package edu.planuj.serverConnection;


import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.Presentation.database.DatabaseFactory;
import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Server.sql.PsqlEngine;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class NotificationPackageVisitorImplementation implements NotificationPackageVisitor {
    private final PsqlEngine database;
    public NotificationPackageVisitorImplementation(PsqlEngine database){
        this.database = database;
    }

    @Override
    public void handleReply(Packable packable) {
        return;
    }

    @Override
    public void handleTeamNotification(NewTeamNotificationPackage newTeamNotificationPackage, ServerClient recipient) throws DatabaseException {
        database.markUserTeamAsNotified(newTeamNotificationPackage.getTeamInfo().getId(), Math.toIntExact(recipient.getClientID()));
    }

    @Override
    public void handleTaskNotification(NewTaskNotificationPackage newTaskNotificationPackage, ServerClient recipient) throws DatabaseException {
        database.markUserTaskAsNotified(newTaskNotificationPackage.getTaskInfo().getId(), Math.toIntExact(recipient.getClientID()));
    }
}
