package edu.planuj.serverConnection;

import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.Server.sql.PsqlEngine;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.serverConnection.abstraction.ServerClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NotificationPackageVisitorImplementationTest {

    private PsqlEngine database;

    void prepare(){
        database = Mockito.mock(PsqlEngine.class);
    }
    @Test
    void handleReply() {
        //given
        prepare();
        NotificationPackageVisitorImplementation notificationPackageVisitorImplementation = new NotificationPackageVisitorImplementation(database);

        //when
        notificationPackageVisitorImplementation.handleReply(null);

        //then
    }

    @Test
    void handleTeamNotification() {
        //given
        prepare();
        NotificationPackageVisitorImplementation notificationPackageVisitorImplementation = new NotificationPackageVisitorImplementation(database);
        NewTeamNotificationPackage newTeamNotificationPackage = Mockito.mock(NewTeamNotificationPackage.class);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        Mockito.when(newTeamNotificationPackage.getTeamInfo()).thenReturn(teamInfo);
        Mockito.when(teamInfo.getId()).thenReturn(1);
        ServerClient recipient = Mockito.mock(ServerClient.class);
        Mockito.when(recipient.getClientID()).thenReturn(1L);

        //when
        assertDoesNotThrow(()->notificationPackageVisitorImplementation.handleTeamNotification( newTeamNotificationPackage, recipient));

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).markUserTeamAsNotified(1, 1));
    }

    @Test
    void handleTaskNotification() {
        //given
        prepare();
        NotificationPackageVisitorImplementation notificationPackageVisitorImplementation = new NotificationPackageVisitorImplementation(database);
        NewTaskNotificationPackage newTaskNotificationPackage = Mockito.mock(NewTaskNotificationPackage.class);
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        Mockito.when(newTaskNotificationPackage.getTaskInfo()).thenReturn(taskInfo);
        Mockito.when(taskInfo.getId()).thenReturn(1);
        ServerClient recipient = Mockito.mock(ServerClient.class);
        Mockito.when(recipient.getClientID()).thenReturn(1L);

        //when
        assertDoesNotThrow(()->notificationPackageVisitorImplementation.handleTaskNotification( newTaskNotificationPackage, recipient));

        //then
        assertDoesNotThrow(()->Mockito.verify(database, Mockito.times(1)).markUserTaskAsNotified(1, 1));
    }
}