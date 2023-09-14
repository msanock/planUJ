package edu.planuj.Connection.protocol.packages.teamOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.serverConnection.abstraction.ServerClient;


public class GetUserTeamsPackage extends UUIDHolder implements Packable {
    int userID;

    public GetUserTeamsPackage(int userID) {
        this.userID = userID;
    }
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetUserTeamsPackage(this, sender);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
