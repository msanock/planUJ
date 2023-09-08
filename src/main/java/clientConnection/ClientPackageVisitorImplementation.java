package clientConnection;

import Connection.manager.ClientPackageVisitor;
import Connection.protocol.packages.UserInfoRequestPackage;
import Server.database.Database;
import Server.sql.DatabaseException;
import Utils.OperationResults.IdResult;
import client.ClientInformation;
import oracle.ons.Cli;

import javax.xml.crypto.Data;
import java.util.logging.Logger;

public class ClientPackageVisitorImplementation implements ClientPackageVisitor {

    public ClientPackageVisitorImplementation() {
    }

    @Override
    public void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage) {

        /*try {
            IdResult result = database.addUser(ClientInformation.getInstance());
            ClientInformation.getInstance().LogInWithId(result.getId());
        } catch (DatabaseException e) {
            Logger.getAnonymousLogger().severe("Exception while adding user to database: " + e.getMessage());
        }*/
    }
}
