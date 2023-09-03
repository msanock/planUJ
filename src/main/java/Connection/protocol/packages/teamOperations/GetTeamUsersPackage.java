package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.abstraction.ServerClient;


public class GetTeamUsersPackage extends UUIDHolder implements Packable {
    int teamID;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetTeamUsersPackage(this, sender);
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }
}