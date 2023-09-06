package client;

import Connection.protocol.packages.ResponsePackage;
import Connection.protocol.packages.taskOperations.*;
import Connection.protocol.packages.teamOperations.*;
import Connection.protocol.packages.userOperations.GetUsersPackage;
import Connection.protocol.packages.userOperations.LoginPackage;
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
import clientConnection.abstraction.ClientRequestHandler;

import java.io.IOException;

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
        if(response.isSuccess()){
            return new IdResult((Integer) response.getData(ResponsePackage.Dictionary.ID));
        }else{
            throw new DatabaseException((Exception)response.getData(ResponsePackage.Dictionary.ERROR));
        }
    }

    @Override
    public void addUserTask(int user_id, int task_id) throws DatabaseException {
        try {
            requestHandler.sendAndGetResponse(new AddUserTaskPackage(user_id, task_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public GetTasksResult getTeamTasks(int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamTasksPackage(team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
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

        return new GetTasksResult(response);
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        try {
            requestHandler.sendAndGetResponse(new UpdateTaskPackage(taskInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public IdResult addTeam(TeamInfo teamInfo) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new AddTeamPackage(teamInfo));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
        return new IdResult((Integer) response.getData(ResponsePackage.Dictionary.ID));
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException {
        try {
            requestHandler.sendAndGetResponse(new AddTeamUserPackage(teamUser, team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public GetTeamsResult getTeams() throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamsPackage());
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return new GetTeamsResult(response);
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamUsersPackage(team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return new GetUsersResult(response);
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetUserTeamsPackage(user_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

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

        return new GetUsersResult(response);
    }
}
