package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.ServerClient;

public class GetUserTasksPackage extends UUIDHolder implements Packable {
    private int userID;

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetUserTasksPackage(this, sender);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
