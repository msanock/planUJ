package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.EmptyPack;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;
import edu.planuj.serverConnection.abstraction.ServerClient;


public interface PackageVisitor extends UserOperationsPackageVisitor, TeamOperationsPackageVisitor, TaskOperationsPackageVisitor{

    RespondInformation handleEmptyPack(EmptyPack emptyPack, ServerClient sender);

    RespondInformation handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPack, ServerClient sender);

    RespondInformation handleResponseInformation(ResponsePackage responsePackage, ServerClient sender); // TODO: move to more suitable place

}
