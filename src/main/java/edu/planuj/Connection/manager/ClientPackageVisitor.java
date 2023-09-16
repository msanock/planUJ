package edu.planuj.Connection.manager;

import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;

public interface ClientPackageVisitor {
    void handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPackage);

    void handleNewTaskNotificationPack(NewTaskNotificationPackage newTaskNotificationPackage);

    void handleNewTeamNotificationPack(NewTeamNotificationPackage newTeamNotificationPackage);
}
