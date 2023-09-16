package edu.planuj.serverConnection;

import edu.planuj.Connection.manager.PackageVisitor;
import edu.planuj.Connection.protocol.RespondInformation;
import edu.planuj.Connection.protocol.packages.ResponsePackage;
import edu.planuj.Connection.protocol.packages.UUIDHolder;
import edu.planuj.Connection.protocol.packages.taskOperations.*;
import edu.planuj.Connection.protocol.packages.teamOperations.*;
import edu.planuj.Connection.protocol.packages.userOperations.GetUsersPackage;
import edu.planuj.Connection.protocol.packages.userOperations.LoginPackage;
import edu.planuj.Connection.protocol.packages.EmptyPack;
import edu.planuj.Connection.protocol.packages.UserInfoRequestPackage;
import edu.planuj.Server.database.Database;
import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.*;
import edu.planuj.serverConnection.abstraction.ServerClient;
import edu.planuj.serverConnection.abstraction.SocketSelector;

public class ServerPackageVisitorImplementation implements PackageVisitor {
    private final Database database;
    private final SocketSelector socketSelector;

    public ServerPackageVisitorImplementation(Database database, SocketSelector socketSelector) {
        this.database = database;
        this.socketSelector = socketSelector;
    }

    @Override
    public RespondInformation handleEmptyPack(EmptyPack emptyPack, ServerClient sender){
        //TODO?
        return null;
    }

    @Override
    public RespondInformation handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPack, ServerClient sender){
        //TODO?
        return null;
    }

    @Override
    public RespondInformation handleResponseInformation(ResponsePackage responsePackage, ServerClient sender) {
        //TODO?
        return null;
    }

    private RespondInformation prepareBasicResponse(ServerClient sender, OperationResult result, UUIDHolder UUIDpackage){
        return (new RespondInformation.RespondInformationBuilder())
                .addRespond(sender.getClientID(), result.toResponsePackage(UUIDpackage.getUUID()))
                .build();
    }


    RespondInformation prepareBasicErrorResponse(ServerClient sender, Exception e){
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                new ResponsePackage.Builder()
                        .addData(ResponsePackage.Dictionary.ERROR, e)
                        .setSuccess(false)
                        .build()
        ).build();
    }


    @Override
    public RespondInformation handleLoginPackage(LoginPackage loginPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addUser(loginPackage.getUserInfo());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        loginPackage.getUserInfo().setId(result.getId());
        sender.setClientID((long) result.getId());
        socketSelector.setClientID((long) result.getId(), sender);
        return prepareBasicResponse(sender, result, loginPackage);
    }

    @Override
    public RespondInformation handleGetUsersPackage(GetUsersPackage getUsersPackage, ServerClient sender) {
        GetUsersResult result = null;
        try {
            result = database.getUsers();
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, result, getUsersPackage);
    }


    @Override
    public RespondInformation handleAddTeamPackage(AddTeamPackage addTeamPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addTeam(addTeamPackage.getTeamInfo());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        addTeamPackage.getTeamInfo().setId(result.getId());
        return prepareBasicResponse(sender, result, addTeamPackage);
    }

    @Override
    public RespondInformation handleAddTeamUserPackage(AddTeamUserPackage addTeamUserPackage, ServerClient sender) {
        try {
            database.addTeamUser(addTeamUserPackage.getTeamUser(), addTeamUserPackage.getTeamID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new ResponsePackage.Builder().setSuccess(true).build()).build();
    }

    @Override
    public RespondInformation handleGetTeamUsersPackage(GetTeamUsersPackage getTeamUsersPackage, ServerClient sender) {
        GetUsersResult getUsersResult = null;
        try {
            getUsersResult = database.getTeamUsers(getTeamUsersPackage.getTeamID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, getUsersResult, getTeamUsersPackage);
    }

    @Override
    public RespondInformation handleGetTeamsPackage(GetTeamsPackage getTeamsPackage, ServerClient sender) {
        GetTeamsResult getTeamsResult = null;
        try {
            getTeamsResult = database.getTeams();
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, getTeamsResult, getTeamsPackage);
    }

    @Override
    public RespondInformation handleGetUserTeamsPackage(GetUserTeamsPackage getUserTeamsPackage, ServerClient sender) {
        GetTeamsResult getTeamsResult = null;
        try {
            getTeamsResult = database.getUserTeams(getUserTeamsPackage.getUserID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, getTeamsResult, getUserTeamsPackage);
    }

    @Override
    public RespondInformation handleAddTaskPackage(AddTaskPackage addTaskPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addTask(addTaskPackage.getTaskInfo());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        addTaskPackage.getTaskInfo().setId(result.getId());
        return prepareBasicResponse(sender, result, addTaskPackage);
    }

    @Override
    public RespondInformation handleGetTeamTasksPackage(GetTeamTasksPackage getTasksPackage, ServerClient sender) {
        GetTasksResult result = null;
        try {
            result = database.getTeamTasks(getTasksPackage.getTeamID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, result, getTasksPackage);
    }

    @Override
    public RespondInformation handleGetUserTasksPackage(GetUserTasksPackage getUserTasksPackage, ServerClient sender) {
        GetTasksResult result = null;
        try {
            result = database.getUserTasks(getUserTasksPackage.getUserID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return prepareBasicResponse(sender, result, getUserTasksPackage);
    }

    @Override
    public RespondInformation handleAddUserTaskPackage(AddUserTaskPackage updateTaskPackage, ServerClient sender) {
        try {
            database.addUserTask(updateTaskPackage.getUserID(), updateTaskPackage.getTaskID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new ResponsePackage.Builder().setSuccess(true).build()).build();
    }

    @Override
    public RespondInformation handleUpdateTaskPackage(UpdateTaskPackage updateTaskPackage, ServerClient sender) {
        try {
            database.updateTask(updateTaskPackage.getTaskInfo());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new ResponsePackage.Builder().setSuccess(true).build()).build();
    }

    @Override
    public RespondInformation handleRemoveUserFromTaskPackage(RemoveUserFromTaskPackage removeUserFromTaskPackage, ServerClient sender) {
        try {
            database.removeUserFromTask(removeUserFromTaskPackage.getUserID(), removeUserFromTaskPackage.getTaskID());
        } catch (DatabaseException e) {
            return prepareBasicErrorResponse(sender, e);
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new ResponsePackage.Builder().setSuccess(true).build()).build();
    }
}
