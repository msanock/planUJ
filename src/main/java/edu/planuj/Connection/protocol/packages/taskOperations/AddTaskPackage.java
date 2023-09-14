package edu.planuj.Connection.protocol.packages.taskOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class AddTaskPackage extends UUIDHolder implements Packable{
    private TaskInfo taskInfo;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddTaskPackage(this, sender);
    }

    public AddTaskPackage(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
