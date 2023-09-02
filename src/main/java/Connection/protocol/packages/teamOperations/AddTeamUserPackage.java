package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Utils.TeamUser;
import serverConnection.ServerClient;

public class AddTeamUserPackage implements Packable {
    private TeamUser teamUser;
    private int TeamID;

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddTeamUserPackage(this, sender);
    }

    public TeamUser getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(TeamUser teamUser) {
        this.teamUser = teamUser;
    }

    public int getTeamID() {
        return TeamID;
    }

    public void setTeamID(int teamID) {
        TeamID = teamID;
    }
}
