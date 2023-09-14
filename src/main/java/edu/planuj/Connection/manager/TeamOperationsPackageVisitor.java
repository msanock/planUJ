package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.teamOperations.*;
import edu.planuj.serverConnection.abstraction.ServerClient;

public interface TeamOperationsPackageVisitor {

    RespondInformation handleAddTeamPackage(AddTeamPackage addTeamPackage, ServerClient sender);
    RespondInformation handleAddTeamUserPackage(AddTeamUserPackage addTeamUserPackage, ServerClient sender);
    RespondInformation handleGetTeamUsersPackage(GetTeamUsersPackage getTeamUsersPackage, ServerClient sender);
    RespondInformation handleGetTeamsPackage(GetTeamsPackage getTeamsPackage, ServerClient sender);

    RespondInformation handleGetUserTeamsPackage(GetUserTeamsPackage getUserTeamsPackage, ServerClient sender);
}
