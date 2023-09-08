package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.abstraction.ServerClient;

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
