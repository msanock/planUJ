package edu.planuj.Connection.protocol.packages.taskOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.serverConnection.abstraction.ServerClient;

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
