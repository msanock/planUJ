package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.userOperations.GetUsersPackage;
import edu.planuj.Connection.protocol.packages.userOperations.LoginPackage;
import edu.planuj.serverConnection.abstraction.ServerClient;

public interface UserOperationsPackageVisitor {
    RespondInformation handleLoginPackage(LoginPackage loginPackage, ServerClient sender);

    RespondInformation handleGetUsersPackage(GetUsersPackage getUsersPackage, ServerClient sender);
}
