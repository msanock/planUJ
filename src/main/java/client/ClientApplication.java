package client;

import Connection.protocol.packages.userOperations.GetUsersPackage;
import Presentation.database.DatabaseFactory;
import Server.database.Database;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.GetUsersResult;
import Utils.OperationResults.IdResult;
import Utils.TaskInfo;
import Utils.TeamInfo;
import Utils.TeamUser;
import Utils.UserInfo;
import clientConnection.ClientConnectionFactory;
import clientConnection.ClientConnectionManager;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApplication{
    static Database database;
    //TODO: add javafx, but this shit doesnt work
    public static void main(String[] args) {
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void start() throws Exception {
        ClientConnectionManager connectionManager = ClientConnectionFactory.getInstance().getClientConnection();
        ClientInformation.getInstance().setClientName("admin");
        try {
            connectionManager.startService();
        } catch (ConnectException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Exception while connecting to server: ", e);
            throw new RuntimeException(e);
        }

        database = DatabaseFactory.getInstance().getServerDatabase(connectionManager.getRequestHandler());

        try {
            IdResult res = database.addUser(ClientInformation.getInstance());
            ClientInformation.getInstance().setId(res.getId());
        }catch (Exception ignore){}

        /*System.out.println(ClientInformation.getInstance().getId() + " returing id");

        TeamInfo team1 = new TeamInfo("team1", 0, List.of());

        IdResult idResult = database.addTeam(team1);
        team1.setId(idResult.getId());

        System.out.println(idResult.getId() + " team id ");


        TeamUser teamUser = new TeamUser(ClientInformation.getInstance(), TeamUser.Role.ADMIN, "developer");

        try {
            database.addTeamUser(teamUser, team1.getId());
        }catch (Exception ignore){}

        TaskInfo taskInfo = new TaskInfo(0,1, "task1", TaskInfo.Status.TODO, 10, LocalDateTime.now(), null);

        try{
        idResult = database.addTask(taskInfo);
        }catch (Exception ignore){}

        System.out.println(idResult.getId() + " task id ");

        try{
        database.addUserTask(ClientInformation.getInstance().getId(), idResult.getId());
        }catch (Exception ignore){}

        try{
        database.updateTask(taskInfo);
        }catch (Exception a){
            a.printStackTrace();
        }

        try{
            GetTasksResult result= database.getTeamTasks(1);
            System.out.println(result.getTasks().size() + " team 1 tasks");
            for(TaskInfo info : result.getTasks()){
                System.out.println(info.getInfo() + " " + info.getId() + " " + info.getStatus() + " " + info.getPriority() + " " + info.getDeadline());
                for (UserInfo userInfo : info.getAssignedUsers()){
                    System.out.println(userInfo.getUsername() + " " + userInfo.getId());
                }
            }
        }catch (Exception a){
            a.printStackTrace();
        }

        try{
            GetUsersResult result= database.getTeamUsers(1);
            System.out.println(result.getUsers().size() + " team 1 users");
            for (UserInfo info : result.getUsers()){
                System.out.println(info.getUsername() + " " + info.getId());
            }
        }catch (Exception a){
            a.printStackTrace();
        }

        try {
            GetUsersResult res = database.getUsers();
            System.out.println(res.getUsers().size() + " users");
            for(UserInfo info : res.getUsers()){
                System.out.println(info.getUsername() + " " + info.getId());
            }
        }catch (Exception a){
            a.printStackTrace();
        }

        try {
            GetTasksResult res = database.getUserTasks(ClientInformation.getInstance().getId());
            for(TaskInfo info : res.getTasks()){
                System.out.println(info.getInfo() + " " + info.getId() + " " + info.getStatus() + " " + info.getPriority() + " " + info.getDeadline());
            }
        }catch (Exception a){
            a.printStackTrace();
        }

        try {
            GetTeamsResult res = database.getTeams();
            for(TeamInfo info : res.getTeams()){
                System.out.println(info.getName() + " " + info.getId());
            }
        }catch (Exception a){
            a.printStackTrace();
        }*/
    }




}
