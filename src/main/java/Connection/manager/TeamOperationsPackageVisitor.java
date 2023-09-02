package Connection.manager;

import Connection.protocol.RespondInformation;
import Connection.protocol.packages.teamOperations.*;
import serverConnection.ServerClient;

public interface TeamOperationsPackageVisitor {

    RespondInformation handleAddTeamPackage(AddTeamPackage addTeamPackage, ServerClient sender);
    RespondInformation handleAddTeamUserPackage(AddTeamUserPackage addTeamUserPackage, ServerClient sender);
    RespondInformation handleGetTeamUsersPackage(GetTeamUsersPackage getTeamUsersPackage, ServerClient sender);
    RespondInformation handleGetTeamsPackage(GetTeamsPackage getTeamsPackage, ServerClient sender);

    RespondInformation handleGetUserTeamsPackage(GetUserTeamsPackage getUserTeamsPackage, ServerClient sender);
}
