package Connection.protocol.packages.teamOperations;

import Connection.manager.PackageVisitor;
import Connection.protocol.Packable;
import Connection.protocol.RespondInformation;
import Utils.TeamInfo;
import serverConnection.ServerClient;

public class AddTeamPackage implements Packable {
    private TeamInfo teamInfo;

    @Override
    public RespondInformation accept(PackageVisitor v, ServerClient sender) {
        return v.handleAddTeamPackage(this, sender);
    }

    public TeamInfo getTeamInfo() {
        return teamInfo;
    }
}
