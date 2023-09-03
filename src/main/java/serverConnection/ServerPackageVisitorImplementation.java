package serverConnection;

import Connection.manager.PackageVisitor;
import Connection.protocol.RespondInformation;
import Connection.protocol.packages.ResponsePackage;
import Connection.protocol.packages.taskOperations.*;
import Connection.protocol.packages.teamOperations.*;
import Connection.protocol.packages.userOperations.GetUsersPackage;
import Connection.protocol.packages.userOperations.LoginPackage;
import Connection.protocol.packages.EmptyPack;
import Connection.protocol.packages.UserInfoRequestPackage;
import Server.database.Database;
import Server.sql.DatabaseException;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import serverConnection.abstraction.ServerClient;
import serverConnection.abstraction.SocketSelector;

public class ServerPackageVisitorImplementation implements PackageVisitor {
    Database database;
    SocketSelector socketSelector;
    ServerClient client;
    public ServerPackageVisitorImplementation(Database database, SocketSelector socketSelector, ServerClient client) {
        this.database = database;
        this.socketSelector = socketSelector;
        this.client = client;
    }

    @Override
    public RespondInformation handleEmptyPack(EmptyPack emptyPack, ServerClient sender){
        return null;
    }

    @Override
    public RespondInformation handleUserInfoRequestPack(UserInfoRequestPackage userInfoRequestPack, ServerClient sender){
        return null;
    }


    @Override
    public RespondInformation handleLoginPackage(LoginPackage loginPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addUser(loginPackage.getUserInfo());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        loginPackage.getUserInfo().setId(result.getId());
        sender.setClientID((long) result.getId());
        socketSelector.setClientID((long) result.getId(), sender);
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleGetUsersPackage(GetUsersPackage getUsersPackage, ServerClient sender) {
        GetUsersResult result = null;
        try {
            result = database.getUsers();
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }


    @Override
    public RespondInformation handleAddTeamPackage(AddTeamPackage addTeamPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addTeam(addTeamPackage.getTeamInfo());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        addTeamPackage.getTeamInfo().setId(result.getId());
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleAddTeamUserPackage(AddTeamUserPackage addTeamUserPackage, ServerClient sender) {
        try {
            database.addTeamUser(addTeamUserPackage.getTeamUser(), addTeamUserPackage.getTeamID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new EmptyPack()).build();
    }

    @Override
    public RespondInformation handleGetTeamUsersPackage(GetTeamUsersPackage getTeamUsersPackage, ServerClient sender) {
        GetUsersResult getUsersResult = null;
        try {
            getUsersResult = database.getTeamUsers(getTeamUsersPackage.getTeamID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), getUsersResult.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleGetTeamsPackage(GetTeamsPackage getTeamsPackage, ServerClient sender) {
        GetTeamsResult getTeamsResult = null;
        try {
            getTeamsResult = database.getTeams();
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), getTeamsResult.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleGetUserTeamsPackage(GetUserTeamsPackage getUserTeamsPackage, ServerClient sender) {
        GetTeamsResult getTeamsResult = null;
        try {
            getTeamsResult = database.getUserTeams(getUserTeamsPackage.getUserID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), getTeamsResult.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleAddTaskPackage(AddTaskPackage addTaskPackage, ServerClient sender) {
        IdResult result = null;
        try {
            result = database.addTask(addTaskPackage.getTaskInfo());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        addTaskPackage.getTaskInfo().setId(result.getId());
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleGetTeamTasksPackage(GetTeamTasksPackage getTasksPackage, ServerClient sender) {
        GetTasksResult result = null;
        try {
            result = database.getTeamTasks(getTasksPackage.getTeamID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleGetUserTasksPackage(GetUserTasksPackage getUserTasksPackage, ServerClient sender) {
        GetTasksResult result = null;
        try {
            result = database.getUserTasks(getUserTasksPackage.getUserID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), result.toResponsePackage()).build();
    }

    @Override
    public RespondInformation handleAddUserTaskPackage(AddUserTaskPackage updateTaskPackage, ServerClient sender) {
        try {
            database.addUserTask(updateTaskPackage.getUserID(), updateTaskPackage.getTaskID());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder().addData(ResponsePackage.Dictionary.ERROR, e.getMessage()).setSuccess(false).build()
            ).build();
        }
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new EmptyPack()).build();
    }

    @Override
    public RespondInformation handleUpdateTaskPackage(UpdateTaskPackage updateTaskPackage, ServerClient sender) {
        try {
            database.updateTask(updateTaskPackage.getTaskInfo());
        } catch (DatabaseException e) {
            return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(),
                    new ResponsePackage.Builder()
                            .addData(ResponsePackage.Dictionary.ERROR, e.getMessage())
                            .setSuccess(false)
                            .build()
            ).build();
        }
        //TODO: should it really be an empty pack?
        return (new RespondInformation.RespondInformationBuilder()).addRespond(sender.getClientID(), new EmptyPack()).build();
    }
}
