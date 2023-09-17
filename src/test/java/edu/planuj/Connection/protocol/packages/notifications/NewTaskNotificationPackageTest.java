package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.clientConnection.NotificationObserverImplementation;
import edu.planuj.clientConnection.abstraction.NotificationSubscriber;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NewTaskNotificationPackageTest {

    @Test
    void acceptInteg() {
        //given
        ClientPackageVisitor v = Mockito.mock(ClientPackageVisitor.class);
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        NewTaskNotificationPackage newTeamNotificationPackage = new NewTaskNotificationPackage(taskInfo);
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);
        NotificationObserverImplementation.getInstance().subscribe(notificationSubscriber);
        //when
        newTeamNotificationPackage.accept(v);

        //then
        Mockito.verify(notificationSubscriber, Mockito.times(1)).update(taskInfo);
    }

    @Test
    void getSetTaskInfo() {
        //given
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        NewTaskNotificationPackage newTeamNotificationPackage = new NewTaskNotificationPackage(taskInfo);

        //when
        newTeamNotificationPackage.setTaskInfo(taskInfo);
        TaskInfo taskInfo1 = newTeamNotificationPackage.getTaskInfo();

        //then
        assertEquals(taskInfo, taskInfo1);

    }

    @Test
    void acceptServer() {
        //given
        PackageVisitor v = Mockito.mock(PackageVisitor.class);
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        NewTaskNotificationPackage newTeamNotificationPackage = new NewTaskNotificationPackage(taskInfo);

        //when
        newTeamNotificationPackage.accept(v, serverClient);
    }

    @Test
    void acceptNotification() throws DatabaseException {
        //given
        NotificationPackageVisitor v = Mockito.mock(NotificationPackageVisitor.class);
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        NewTaskNotificationPackage newTaskNotificationPackage = new NewTaskNotificationPackage(taskInfo);

        //when
        newTaskNotificationPackage.accept(v, serverClient);

        //then
        Mockito.verify(v, Mockito.times(1)).handleTaskNotification(newTaskNotificationPackage, serverClient);

    }

    @Test
    void acceptNotificationWithException() throws DatabaseException {
        //given
        NotificationPackageVisitor v = Mockito.mock(NotificationPackageVisitor.class);
        TaskInfo taskInfo = Mockito.mock(TaskInfo.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        NewTaskNotificationPackage newTaskNotificationPackage = new NewTaskNotificationPackage(taskInfo);
        Mockito.doThrow(DatabaseException.class).when(v).handleTaskNotification(newTaskNotificationPackage, serverClient);

        //when
        newTaskNotificationPackage.accept(v, serverClient);

        //then
        Mockito.verify(v, Mockito.times(1)).handleTaskNotification(newTaskNotificationPackage, serverClient);
    }
}