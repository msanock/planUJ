package edu.planuj.client;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.*;
import edu.planuj.Utils.OperationResults.*;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class AppHandler {
    MainScreenController mainScreen;

    private TeamInfo currentTeam;
    static private AppHandler instance;
    private ClientInformation client;

    public static AppHandler getInstance() {
        if (instance == null)
            instance = new AppHandler();

        return instance;
    }

    AppHandler() {
        mainScreen = MainScreenController.getInstance();
    }

    static void setMainScreen(MainScreenController screen) {
        instance.mainScreen = screen;
    }

    public void forceLogInView() {
        RealApplication.setScene("login-view.fxml");
    }

    public boolean tryNewLogIn(String login) {
        // TODO shouldn't this ClientInformation setting be in a different class
        if (!ClientInformation.isCorrectLogin(login))
            return false;

        client = ClientInformation.getInstance();
        client.setClientName(login);
        IdResult idResult;
        try {
            idResult = RealApplication.getDatabase().addUser(new UserInfo(client.getUsername(), 0));
        } catch (DatabaseException e) {
            return false;
        }
        client.logInWithId(idResult.getId());
        Logger.getAnonymousLogger().info("Client set: " + client.getUsername() + " " + client.getId());

        return true;
    }
    public void loggedUserUI() throws IOException{
        // now changes in UI, at this point login is considered successful
        RealApplication.setScene("base-view.fxml");
        mainScreen = MainScreenController.getInstance();
        mainScreen.setTasks(Collections.emptyList());
        mainScreen.setMembers(Collections.emptyList());
        mainScreen.setTeams(Collections.emptyList());
        mainScreen.setLogInViewExitable(true);
        mainScreen.closeLogInView();
        GetTeamsResult result = null;
        try {
            result = RealApplication.getDatabase().getUserTeams(client.getId());
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

        if (result != null)
            mainScreen.setTeams(result.getTeams()); // TODO is null possible?

        mainScreen.showTeams();
    }

    public void setTeam(TeamInfo team) {
        GetTasksResult tasksResult = null;

        currentTeam = team;
        mainScreen.setMembers(team.getUsers());

        try {
            tasksResult = RealApplication.getDatabase().getTeamTasks(team.getId());
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
        }
        if (tasksResult != null)
            mainScreen.setTasks(tasksResult.getTasks());
        mainScreen.closeTeams();
    }

    public boolean updateTask(TaskInfo taskInfo) {
        try {
            RealApplication.getDatabase().updateTask(taskInfo);
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            GetTasksResult tasksResult = null;
            try {
                TeamInfo team = currentTeam;
                tasksResult = RealApplication.getDatabase().getTeamTasks(team.getId());
            } catch (DatabaseException ex) {
                mainScreen.reportError(ex);
            }
            if (tasksResult != null)
                mainScreen.setTasks(tasksResult.getTasks());
            return false;
        }
        return true;
    }

    public boolean addNewTask(TaskInfo taskInfo) {
        IdResult idResult = null;
        try {
            idResult = RealApplication.getDatabase().addTask(taskInfo);
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return false;
        }
        if (idResult != null) {
            taskInfo.setId(idResult.getId());
            return true;
        }
        return false;
    }


    public TeamInfo getCurrentTeam() {
        return currentTeam;
    }

    public boolean addNewTeam(TeamInfo teamInfo) {
        IdResult idResult = null;
        try {
            idResult  = RealApplication.getDatabase().addTeam(teamInfo);
            teamInfo.setId(idResult.getId());
        } catch (DatabaseException e) {
            mainScreen.reportError(e);

            return false;
        }
        return idResult != null;
    }

    public boolean AddUserToTeam(TeamUser teamUser) {
        try {
            if(currentTeam!=null)
                RealApplication.getDatabase().addTeamUser(teamUser, currentTeam.getId());
            else
                return false;
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return false;
        }

        return true;
    }

    public void updateTeamUsers() {
        GetTeamUserResult teamUsers = null;
        try {
            teamUsers = RealApplication.getDatabase().getTeamUsers(currentTeam.getId());
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return;
        }

        if (teamUsers != null)
            mainScreen.membersView.setMembers(teamUsers.getTeamUsers());
    }

    public boolean AddUserToTask(UserInfo user, TaskInfo taskInfo) {
        try {
            RealApplication.getDatabase().addUserTask(user.getId(), taskInfo.getId());
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return false;
        }

        return true;
    }

    public boolean removeUserFromTask(UserInfo user, TaskInfo taskInfo) {
        try {
            RealApplication.getDatabase().removeUserFromTask(user.getId(), taskInfo.getId());
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return false;
        }

        return true;
    }

    public Collection<UserInfo> getUsers() {
        try {
            return RealApplication.getDatabase().getUsers().getUsers();
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }
    }
}