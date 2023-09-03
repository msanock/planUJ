package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import Utils.TaskInfo;
import serverConnection.ServerClient;

public class AddTaskPackage extends UUIDHolder implements Packable{
    private TaskInfo taskInfo;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddTaskPackage(this, sender);
    }

    public TaskInfo getTaskInfo() {
        return taskInfo;
    }

    public void setTaskInfo(TaskInfo taskInfo) {
        this.taskInfo = taskInfo;
    }
}
