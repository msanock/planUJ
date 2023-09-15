package edu.planuj.Connection.protocol.packages.teamOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Utils.TeamUser;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class AddTeamUserPackage extends UUIDHolder implements Packable {
    private TeamUser teamUser;
    private int TeamID;

    public AddTeamUserPackage(TeamUser teamUser, int TeamID) {
        this.teamUser = teamUser;
        this.TeamID = TeamID;
    }

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
