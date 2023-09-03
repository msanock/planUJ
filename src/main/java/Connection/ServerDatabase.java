package Connection;

import Connection.protocol.Packable;
import Connection.protocol.packages.taskOperations.*;
import Connection.protocol.packages.teamOperations.*;
import Connection.protocol.packages.userOperations.GetUsersPackage;
import Connection.protocol.packages.userOperations.LoginPackage;
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
        AddTaskPackage addTaskPackage = new AddTaskPackage(taskInfo);


        return null;
    }

    @Override
    public void addUserTask(int user_id, int task_id) {
        AddUserTaskPackage addUserTaskPackage = new AddUserTaskPackage(user_id, task_id);

    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) {
        GetTeamTasksPackage getTeamTasksPackage = new GetTeamTasksPackage(team_id);

        return null;
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) {
        GetUserTasksPackage getUserTasksPackage = new GetUserTasksPackage(user_id);


        return null;
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        UpdateTaskPackage updateTaskPackage = new UpdateTaskPackage(taskInfo);

    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) {
        AddTeamPackage addTeamPackage = new AddTeamPackage(teamInfo);

        return null;
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) {
        AddTeamUserPackage addTeamUserPackage = new AddTeamUserPackage(teamUser, team_id);

    }

    @Override
    public GetTeamsResult getTeams() {
        GetTeamsPackage getTeamsPackage = new GetTeamsPackage();

        return null;
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) {
        GetTeamUsersPackage getTeamUsersPackage = new GetTeamUsersPackage(team_id);

        return null;
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        GetUserTeamsPackage getUserTeamsPackage = new GetUserTeamsPackage(user_id);


        return null;
    }

    @Override
    public IdResult addUser(UserInfo userInfo) {
        LoginPackage loginPackage = new LoginPackage(userInfo);

        return null;
    }

    @Override
    public GetUsersResult getUsers() {
        GetUsersPackage getUsersPackage = new GetUsersPackage();

        return null;
    }
}
