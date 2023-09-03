package Connection;

import Connection.protocol.Packable;
import Connection.protocol.packages.taskOperations.AddTaskPackage;
import Server.database.Database;
import Server.sql.DatabaseException;
import Utils.OperationResults.*;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;

import java.util.concurrent.Future;

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
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {

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
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
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
