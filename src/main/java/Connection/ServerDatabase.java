package Connection;

import Server.database.Database;
import Utils.OperationResults.*;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;

public class ServerDatabase implements Database {
    @Override
    public IdResult addTask(TaskInfo taskInfo) {
        return null;
    }

    @Override
    public void addUserTask(int user_id, int task_id) {

    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) {
        return null;
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) {
        return null;
    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) {
        return null;
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) {
    }

    @Override
    public GetTeamsResult getTeams() {
        return null;
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) {
        return null;
    }

    @Override
    public IdResult addUser(UserInfo userInfo) {
        return null;
    }

    @Override
    public GetUsersResult getUsers() {
        return null;
    }
    //placeholder
}
