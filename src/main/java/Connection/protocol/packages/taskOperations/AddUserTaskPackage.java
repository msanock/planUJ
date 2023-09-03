package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.abstraction.ServerClient;

public class AddUserTaskPackage extends UUIDHolder implements Packable {
    private int userID;
    private int taskID;

    public AddUserTaskPackage(int userID, int taskID) {
        this.userID = userID;
        this.taskID = taskID;
    }
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddUserTaskPackage(this, sender);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}
