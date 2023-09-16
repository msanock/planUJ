package edu.planuj.clientConnection;

import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;
import edu.planuj.clientConnection.abstraction.NotificationObserver;
import edu.planuj.clientConnection.abstraction.NotificationSubscriber;

import java.util.ArrayList;
import java.util.List;

public class NotificationObserverImplementation implements NotificationObserver {
    List<NotificationSubscriber> subscribers;

    private static class Holder {
        static NotificationObserverImplementation instance = new NotificationObserverImplementation();
    }

    private NotificationObserverImplementation() {
        subscribers = new ArrayList<>();
    }

    static public NotificationObserverImplementation getInstance() {
        return Holder.instance;
    }

    @Override
    public void subscribe(NotificationSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(NotificationSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void notify(NewTaskNotificationPackage newTaskNotificationPackage) {
        for (NotificationSubscriber subscriber : subscribers) {
            subscriber.update(newTaskNotificationPackage.getTaskInfo());
        }
    }

    @Override
    public void notify(NewTeamNotificationPackage newTeamNotificationPackage) {
        for (NotificationSubscriber subscriber : subscribers) {
            subscriber.update(newTeamNotificationPackage.getTeamInfo());
        }
    }

}
