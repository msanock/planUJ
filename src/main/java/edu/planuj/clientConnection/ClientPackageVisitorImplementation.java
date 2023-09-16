package edu.planuj.clientConnection;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.client.AppHandler;
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

    @Override
    public void handleNewTaskNotificationPack(NewTaskNotificationPackage newTaskNotificationPackage) {
        NotificationObserverImplementation.getInstance().notify(newTaskNotificationPackage);
    }

    @Override
    public void handleNewTeamNotificationPack(NewTeamNotificationPackage newTeamNotificationPackage) {
        NotificationObserverImplementation.getInstance().notify(newTeamNotificationPackage);
    }
}
