package Connection.manager;

import Connection.protocol.RespondInformation;
import Connection.protocol.packages.userOperations.GetUsersPackage;
import Connection.protocol.packages.userOperations.LoginPackage;
import serverConnection.abstraction.ServerClient;

public interface UserOperationsPackageVisitor {
    RespondInformation handleLoginPackage(LoginPackage loginPackage, ServerClient sender);

    RespondInformation handleGetUsersPackage(GetUsersPackage getUsersPackage, ServerClient sender);
}
