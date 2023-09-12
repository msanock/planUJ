package clientConnection;

import Connection.manager.ClientPackageVisitor;
import Connection.protocol.packages.UserInfoRequestPackage;
import client.AppHandler;
import javafx.application.Platform;

public class ClientPackageVisitorImplementation implements ClientPackageVisitor {
    AppHandler appHandler; // NOT SET, EVEN worse than that

    public ClientPackageVisitorImplementation() {

        appHandler = AppHandler.getInstance();
    }

    @Override
    public void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage) {
        Platform.runLater(() -> appHandler.forceLogInView());


        /*try {
            IdResult result = database.addUser(ClientInformation.getInstance());
            ClientInformation.getInstance().LogInWithId(result.getId());
        } catch (DatabaseException e) {
            Logger.getAnonymousLogger().severe("Exception while adding user to database: " + e.getMessage());
        }*/
    }
}
