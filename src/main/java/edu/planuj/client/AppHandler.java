package edu.planuj.client;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.GetTasksResult;
import edu.planuj.Utils.OperationResults.GetTeamsResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;

import java.util.Collections;
import java.util.logging.Logger;

public class AppHandler {
    MainScreenController mainScreen;

    static private AppHandler instance;

    public static AppHandler getInstance() {
        if (instance == null)
            instance = new AppHandler();

        return instance;
    }

    AppHandler() { }

    static void setMainScreen(MainScreenController screen) {
        instance.mainScreen = screen;
    }
    public void forceLogInView() {
            mainScreen.setLogInViewExitable(false);
            mainScreen.showLogInView();
    }

    public void tryNewLogIn(String login) {
        // TODO shouldn't this ClientInformation setting be in a different class
        if (!ClientInformation.isCorrectLogin(login))
            return;

        ClientInformation client = ClientInformation.getInstance();
        client.setClientName(login);
        IdResult idResult;
        try {
            idResult = RealApplication.getDatabase().addUser(client);
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return;
        }
        client.logInWithId(idResult.getId());
        Logger.getAnonymousLogger().info("Client set: " + client.getUsername() + " " + client.getId());


        // now changes in UI, at this point login is considered successful
        mainScreen.setTasks(Collections.emptyList());
        mainScreen.setMembers(Collections.emptyList());
        mainScreen.setTeams(Collections.emptyList());
        mainScreen.setLogInViewExitable(true);
        mainScreen.closeLogInView();
        GetTeamsResult result = null;
        try {
            result = RealApplication.getDatabase().getTeams();
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
        }

        if (result != null)
            mainScreen.setTeams(result.getTeams()); // TODO is nullpointer possible?

        mainScreen.showTeams();


    }

    public void setTeam(TeamInfo team) {
        GetTasksResult tasksResult = null;
        mainScreen.setCurrentTeam(team);
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
                TeamInfo team = mainScreen.getCurrentTeam();
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
}
