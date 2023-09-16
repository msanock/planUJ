package edu.planuj.clientConnection.abstraction;


import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;

public interface NotificationSubscriber {
    public void update(TaskInfo taskInfo);
    public void update(TeamInfo teamInfo);
}
