package edu.planuj.Connection.protocol.packages.taskOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class GetUserTasksPackage extends UUIDHolder implements Packable {
    private int userID;

    public GetUserTasksPackage(int userID) {
        this.userID = userID;
    }

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
