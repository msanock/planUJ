package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.clientConnection.NotificationObserverImplementation;
import edu.planuj.clientConnection.abstraction.NotificationSubscriber;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import edu.planuj.serverConnection.abstraction.ServerClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NewTeamNotificationPackageTest {

    @Test
    void acceptInteg() {
        //given
        ClientPackageVisitor v = Mockito.mock(ClientPackageVisitor.class);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        NewTeamNotificationPackage newTeamNotificationPackage = new NewTeamNotificationPackage(teamInfo);
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);
        NotificationObserverImplementation.getInstance().subscribe(notificationSubscriber);
        //when
        newTeamNotificationPackage.accept(v);

        //then
        Mockito.verify(notificationSubscriber, Mockito.times(1)).update(teamInfo);
    }

    @Test
    void getSetTeamInfo() {
        //given
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        NewTeamNotificationPackage newTeamNotificationPackage = new NewTeamNotificationPackage(teamInfo);

        //when
        newTeamNotificationPackage.setTeamInfo(teamInfo);
        TeamInfo teamInfo1 = newTeamNotificationPackage.getTeamInfo();

        //then
        assertEquals(teamInfo, teamInfo1);
    }

    @Test
    void acceptServer() {
        //given
        PackageVisitor v = Mockito.mock(PackageVisitor.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        NewTeamNotificationPackage newTeamNotificationPackage = new NewTeamNotificationPackage(teamInfo);

        //when
        newTeamNotificationPackage.accept(v, serverClient);
    }

    @Test
    void acceptNotification() throws DatabaseException {
        //given
        NotificationPackageVisitor v = Mockito.mock(NotificationPackageVisitor.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        NewTeamNotificationPackage newTeamNotificationPackage = new NewTeamNotificationPackage(teamInfo);

        //when
        newTeamNotificationPackage.accept(v, serverClient);

        //then
        Mockito.verify(v, Mockito.times(1)).handleTeamNotification(newTeamNotificationPackage, serverClient);
    }

    @Test
    void acceptNotificationWithException() throws DatabaseException {
        //given
        NotificationPackageVisitor v = Mockito.mock(NotificationPackageVisitor.class);
        ServerClient serverClient = Mockito.mock(ServerClient.class);
        TeamInfo teamInfo = Mockito.mock(TeamInfo.class);
        NewTeamNotificationPackage newTeamNotificationPackage = new NewTeamNotificationPackage(teamInfo);
        Mockito.doThrow(DatabaseException.class).when(v).handleTeamNotification(newTeamNotificationPackage, serverClient);

        //when
        newTeamNotificationPackage.accept(v, serverClient);

        //then
        Mockito.verify(v, Mockito.times(1)).handleTeamNotification(newTeamNotificationPackage, serverClient);
    }
}