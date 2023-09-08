package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.UUIDHolder;
import Utils.TeamInfo;
import serverConnection.abstraction.ServerClient;

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
