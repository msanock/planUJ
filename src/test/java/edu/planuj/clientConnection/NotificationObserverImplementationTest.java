package edu.planuj.clientConnection;

import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.clientConnection.abstraction.NotificationSubscriber;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class NotificationObserverImplementationTest {

    @Test
    void getInstance() {
        //given
        NotificationObserverImplementation notificationObserverImplementation = NotificationObserverImplementation.getInstance();
        NotificationObserverImplementation notificationObserverImplementation1 = NotificationObserverImplementation.getInstance();

        //when&then
        assertEquals(notificationObserverImplementation, notificationObserverImplementation1);
    }

    @Test
    void subscribe() {
        //given
        NotificationObserverImplementation notificationObserverImplementation = NotificationObserverImplementation.getInstance();
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);

        //when
        notificationObserverImplementation.subscribe(notificationSubscriber);

        //then
        assertTrue(notificationObserverImplementation.subscribers.contains(notificationSubscriber));
    }

    @Test
    void unsubscribe() {
        //given
        NotificationObserverImplementation notificationObserverImplementation = NotificationObserverImplementation.getInstance();
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);

        //when
        notificationObserverImplementation.subscribe(notificationSubscriber);
        notificationObserverImplementation.unsubscribe(notificationSubscriber);

        //then
        assertFalse(notificationObserverImplementation.subscribers.contains(notificationSubscriber));
    }

    @Test
    void notifyWithTask() {
        //given
        NotificationObserverImplementation notificationObserverImplementation = NotificationObserverImplementation.getInstance();
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);
        NewTaskNotificationPackage newTaskNotificationPackage = Mockito.mock(NewTaskNotificationPackage.class);

        //when
        notificationObserverImplementation.subscribe(notificationSubscriber);
        notificationObserverImplementation.notify(newTaskNotificationPackage);

        //then
        Mockito.verify(notificationSubscriber, Mockito.times(1)).update(newTaskNotificationPackage.getTaskInfo());
    }

    @Test
    void notifyWithTeam() {
        //given
        NotificationObserverImplementation notificationObserverImplementation = NotificationObserverImplementation.getInstance();
        NotificationSubscriber notificationSubscriber = Mockito.mock(NotificationSubscriber.class);
        NewTaskNotificationPackage newTaskNotificationPackage = Mockito.mock(NewTaskNotificationPackage.class);

        //when
        notificationObserverImplementation.subscribe(notificationSubscriber);
        notificationObserverImplementation.notify(newTaskNotificationPackage);

        //then
        Mockito.verify(notificationSubscriber, Mockito.times(1)).update(newTaskNotificationPackage.getTaskInfo());
    }
}