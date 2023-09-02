package Connection.manager;

import Connection.protocol.RespondInformation;
import Connection.protocol.packs.EmptyPack;
import Connection.protocol.Packable;
import Connection.protocol.packs.UserInfoRequestPack;
import serverConnection.ServerClient;


public interface PackageVisitor extends UserOperationsPackageVisitor, TeamOperationsPackageVisitor, TaskOperationsPackageVisitor{

    RespondInformation handleEmptyPack(EmptyPack emptyPack, ServerClient sender);

    RespondInformation handleUserInfoRequestPack(UserInfoRequestPack userInfoRequestPack, ServerClient sender


    /// ...
}
