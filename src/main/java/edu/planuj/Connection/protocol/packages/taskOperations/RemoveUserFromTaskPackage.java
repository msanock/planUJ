package edu.planuj.Connection.protocol.packages.taskOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class RemoveUserFromTaskPackage extends UUIDHolder implements Packable {
    private final int userID;
    private final int taskID;

    public RemoveUserFromTaskPackage(int userID, int taskID) {
        this.userID = userID;
        this.taskID = taskID;
    }

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleRemoveUserFromTaskPackage(this, sender);
    }

    public int getUserID() {
        return userID;
    }

    public int getTaskID() {
        return taskID;
    }
}
