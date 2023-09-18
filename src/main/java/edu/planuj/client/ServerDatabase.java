package edu.planuj.client;

import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Connection.protocol.packages.taskOperations.*;
import edu.planuj.Connection.protocol.packages.teamOperations.*;
import edu.planuj.Connection.protocol.packages.userOperations.GetUsersPackage;
import edu.planuj.Connection.protocol.packages.userOperations.LoginPackage;
import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.*;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import edu.planuj.Utils.UserInfo;
import edu.planuj.clientConnection.abstraction.ClientRequestHandler;

import java.io.IOException;
import java.sql.SQLException;

// what a dull name, truly hideous
public class ServerDatabase implements Database {
    final ClientRequestHandler requestHandler;


    public ServerDatabase(ClientRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public IdResult addTask(TaskInfo taskInfo) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new AddTaskPackage(taskInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new IdResult((Integer) response.getData(ResponsePackage.Dictionary.ID));
    }

    @Override
    public void addUserTask(int user_id, int task_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new AddUserTaskPackage(user_id, task_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamTasksPackage(team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetTasksResult(response);
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetUserTasksPackage(user_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetTasksResult(response);
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new UpdateTaskPackage(taskInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new AddTeamPackage(teamInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new IdResult((Integer) response.getData(ResponsePackage.Dictionary.ID));
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new AddTeamUserPackage(teamUser, team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
    }

    @Override
    public GetTeamsResult getTeams() throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamsPackage());
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetTeamsResult(response);
    }

    @Override
    public GetTeamUserResult getTeamUsers(int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamUsersPackage(team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetTeamUserResult(response);
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetUserTeamsPackage(user_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetTeamsResult(response);
    }

    @Override
    public IdResult addUser(UserInfo userInfo) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new LoginPackage(userInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new IdResult((Integer) response.getData(ResponsePackage.Dictionary.ID));
    }

    @Override
    public GetUsersResult getUsers() throws DatabaseException {
        ResponsePackage response;
        try {
           response = requestHandler.sendAndGetResponse(new GetUsersPackage());
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
        return new GetUsersResult(response);
    }

    @Override
    public void removeUserFromTask(int user_id, int task_id) throws DatabaseException {
        ResponsePackage response;
        try {
             response = requestHandler.sendAndGetResponse(new RemoveUserFromTaskPackage(user_id, task_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        throwExceptionIfNotSuccess(response);
    }

    private void throwExceptionIfNotSuccess(ResponsePackage response) throws DatabaseException {
        if(response == null){
            throw new DatabaseException(new DatabaseException(new SQLException("Response is null")));
        }else if(!response.isSuccess()){
            throw new DatabaseException((Exception)response.getData(ResponsePackage.Dictionary.ERROR));
        }
    }
}
