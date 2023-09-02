package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Utils.TaskInfo;
import serverConnection.ServerClient;

public class UpdateTaskPackage implements Packable {
    private TaskInfo taskInfo;
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
