package client;

import Connection.protocol.packages.ResponsePackage;
import Connection.protocol.packages.taskOperations.*;
import Connection.protocol.packages.teamOperations.*;
import Connection.protocol.packages.userOperations.GetUsersPackage;
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
import clientConnection.ClientRequestHandler;

import java.io.IOException;

// what a stunning name, truly delightful
public class OperationsOnServerProxy implements Database {
    final ClientRequestHandler requestHandler;


    public OperationsOnServerProxy(ClientRequestHandler requestHandler) {
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

        //
        return null; // TODO return what it has to return
    }

    @Override
    public void addUserTask(int user_id, int task_id) throws DatabaseException {
        try {
            requestHandler.sendUnrespondablePackage(new AddUserTaskPackage(user_id, task_id));
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
        return null; // TODO return what it has to return
    }

    @Override
    public GetTasksResult getUserTasks(int user_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetUserTasksPackage(user_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return null; // TODO return what it has to return
    }

    @Override
    public void updateTask(TaskInfo taskInfo) throws DatabaseException {
        try {
            requestHandler.sendUnrespondablePackage(new UpdateTaskPackage(taskInfo));
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

        return null; // TODO return what it has to return
    }

    @Override
    public void addTeamUser(TeamUser teamUser, int team_id) throws DatabaseException {
        try {
            requestHandler.sendUnrespondablePackage(new AddTeamUserPackage(teamUser, team_id));
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

        return null; // TODO return what it has to return
    }

    @Override
    public GetUsersResult getTeamUsers(int team_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetTeamUsersPackage(team_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return null; // TODO return what it has to return
    }

    @Override
    public GetTeamsResult getUserTeams(int user_id) throws DatabaseException {
        ResponsePackage response;
        try {
            response = requestHandler.sendAndGetResponse(new GetUserTeamsPackage(user_id));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return null; // TODO return what it has to return
    }

    @Override
    public IdResult addUser(UserInfo userInfo) throws DatabaseException {
        // ResponsePackage response = requestHandler.sendAndGetResponse(new );
        // Does AddUserPackage even exist ??

        return null; // TODO return what it has to return
    }

    @Override
    public GetUsersResult getUsers() throws DatabaseException {
        ResponsePackage response;
        try {
           response = requestHandler.sendAndGetResponse(new GetUsersPackage());
        } catch (IOException e) {
            throw new DatabaseException(e);
        }

        return null; // TODO return what it has to return
    }
}
