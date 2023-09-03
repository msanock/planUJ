package Connection.protocol.packages.taskOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import serverConnection.ServerClient;

public class GetTeamTasksPackage extends UUIDHolder implements Packable {
    private int teamID;
    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleGetTeamTasksPackage(this, sender);
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }
}
