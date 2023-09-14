package edu.planuj.Connection.protocol.packages.teamOperations;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.Packable;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.serverConnection.abstraction.ServerClient;

public class AddTeamPackage extends UUIDHolder implements Packable {
    private TeamInfo teamInfo;

    public AddTeamPackage(TeamInfo teamInfo) {
        this.teamInfo = teamInfo;
    }

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddTeamPackage(this, sender);
    }

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }
}
