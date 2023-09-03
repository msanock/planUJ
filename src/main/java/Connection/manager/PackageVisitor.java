package Connection.manager;

import Connection.protocol.RespondInformation;
import Connection.protocol.packages.EmptyPack;
import Connection.protocol.packages.UserInfoRequestPackage;
import serverConnection.abstraction.ServerClient;


public interface PackageVisitor extends UserOperationsPackageVisitor, TeamOperationsPackageVisitor, TaskOperationsPackageVisitor{

    RespondInformation handleEmptyPack(EmptyPack emptyPack, ServerClient sender);

    RespondInformation handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPack, ServerClient sender);


    /// ...
}
