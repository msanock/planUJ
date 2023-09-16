package edu.planuj.clientConnection.abstraction;

import edu.planuj.Connection.protocol.packages.notifications.NewTaskNotificationPackage;
import edu.planuj.Connection.protocol.packages.notifications.NewTeamNotificationPackage;

public interface NotificationObserver{
    public void subscribe(NotificationSubscriber subscriber);
    public void unsubscribe(NotificationSubscriber subscriber);

    public void notify(NewTaskNotificationPackage newTaskNotificationPackage);

    public void notify(NewTeamNotificationPackage newTeamNotificationPackage);
}
