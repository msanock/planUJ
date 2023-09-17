package edu.planuj.Connection.protocol.packages.notifications;

import edu.planuj.Connection.manager.ClientPackageVisitor;
import edu.planuj.Connection.protocol.ClientPackable;
import edu.planuj.serverConnection.abstraction.NotificationPackageVisitor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class BatchNotificationPackageTest {

    @Test
    void addGetNotification() {
        //given
        BatchNotificationPackage batchNotificationPackage = new BatchNotificationPackage();
        NotificationPackage notificationPackage = Mockito.mock(NotificationPackage.class);
        //when
        batchNotificationPackage.addNotification(notificationPackage);
        //then
        assertEquals(notificationPackage, batchNotificationPackage.getNotifications().get(0));
    }


    @Test
    void acceptClient() {
        //given
        ClientPackageVisitor clientPackageVisitor = Mockito.mock(ClientPackageVisitor.class);
        BatchNotificationPackage batchNotificationPackage = new BatchNotificationPackage();
        NotificationPackage notificationPackage = Mockito.mock(NotificationPackage.class);
        batchNotificationPackage.addNotification(notificationPackage);

        //when
        batchNotificationPackage.accept(clientPackageVisitor);

        //then
        Mockito.verify((notificationPackage)).accept(clientPackageVisitor);
    }

    @Test
    void acceptNotification() {
        //given
        NotificationPackageVisitor notificationPackageVisitor = Mockito.mock(NotificationPackageVisitor.class);
        BatchNotificationPackage batchNotificationPackage = new BatchNotificationPackage();
        NotificationPackage notificationPackage = Mockito.mock(NotificationPackage.class);
        batchNotificationPackage.addNotification(notificationPackage);

        //when
        batchNotificationPackage.accept(notificationPackageVisitor, null);

        //then
        Mockito.verify((notificationPackage)).accept(notificationPackageVisitor, null);
    }

}