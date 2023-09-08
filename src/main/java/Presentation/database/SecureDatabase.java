package Presentation.database;

import Server.database.Database;
import Server.sql.DatabaseException;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;

public class SecureDatabase implements Database {

    Database delegate;

    public SecureDatabase(Database delegate) {
        this.delegate = delegate;
    }

    @Override
    public IdResult addTask(TaskInfo taskInfo) throws DatabaseException{
        return delegate.addTask(taskInfo);
    }

    @Override
    public void addUserTask(int user_id, int task_id) throws DatabaseException {
        delegate.addUserTask(user_id, task_id);
    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) throws DatabaseException {
        return delegate.getTeamTasks(team_id);
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) throws DatabaseException {
        return delegate.getUserTasks(user_id);
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        delegate.updateTask(taskInfo);
    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) throws DatabaseException {
        return delegate.addTeam(teamInfo);
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException {
        delegate.addTeamUser(teamUser, team_id);
    }

    @Override
    public GetTeamsResult getTeams() throws DatabaseException {
        return delegate.getTeams();
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) throws DatabaseException {
        return delegate.getTeamUsers(team_id);
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        return delegate.getUserTeams(user_id);
    }

    @Override
    public IdResult addUser(UserInfo userInfo) throws DatabaseException {
        return delegate.addUser(userInfo);
    }

    @Override
    public GetUsersResult getUsers() throws DatabaseException {
        return delegate.getUsers();
    }

    @Override
    public void removeUserFromTask(int user_id, int task_id) throws DatabaseException {
        delegate.removeUserFromTask(user_id, task_id);
    }
}
