package edu.planuj.Connection.protocol.packages.taskOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class UpdateTaskPackage extends UUIDHolder implements Packable {
    private TaskInfo taskInfo;

    public UpdateTaskPackage(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleUpdateTaskPackage(this, sender);
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
