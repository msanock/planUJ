package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.clientConnection.abstraction.NotificationSubscriber;

import java.util.logging.Logger;

public class LoggingNotificationSubscriber implements NotificationSubscriber {
    @Override
    public void update(TaskInfo taskInfo) {
        Logger.getAnonymousLogger().info("New task: " + taskInfo.getInfo() + " " + taskInfo.getDeadline() + " for team " + taskInfo.getTeamID() + " for task " + taskInfo.getId());
    }

    @Override
    public void update(TeamInfo teamInfo) {
        Logger.getAnonymousLogger().info("New team: " + teamInfo.getName() + " " + teamInfo.getId() + " with users " + teamInfo.getUsers());
    }
}
