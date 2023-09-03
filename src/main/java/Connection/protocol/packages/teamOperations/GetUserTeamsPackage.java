package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.abstraction.ServerClient;


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
