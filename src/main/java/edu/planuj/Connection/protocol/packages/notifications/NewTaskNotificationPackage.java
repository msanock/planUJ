package edu.planuj.Connection.protocol.packages.notifications;


import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.clientConnection.NotificationObserverImplementation;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;

import java.util.logging.Logger;

public class NewTaskNotificationPackage implements ClientPackable, NotificationPackage {
    private TaskInfo taskInfo;

    public NewTaskNotificationPackage(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public void accept(ClientPackageVisitor v) {
        NotificationObserverImplementation.getInstance().notify(this);
    }


    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return null;
    }

    @Override
    public void accept(NotificationPackageVisitor v, ServerClient recipient) {
        try {
            v.handleTaskNotification(this, recipient);
        }catch (DatabaseException e){
            Logger.getAnonymousLogger().log(java.util.logging.Level.SEVERE, "Exception while marking as notified: ", e);
        }

    }
}
