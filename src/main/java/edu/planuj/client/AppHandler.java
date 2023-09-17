package edu.planuj.client;

import edu.planuj.Server.sql.DatabaseException;
import edu.planuj.Utils.OperationResults.GetTasksResult;
import edu.planuj.Utils.OperationResults.GetTeamsResult;
import edu.planuj.Utils.OperationResults.IdResult;
import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.UserInfo;

import java.io.IOException;
import java.util.Collections;
import java.util.logging.Logger;

public class AppHandler {
    MainScreen mainScreen;

    private TeamInfo currentTeam;
    static private AppHandler instance;

    public static AppHandler getInstance() {
        if (instance == null)
            instance = new AppHandler();

        return instance;
    }

    AppHandler() { }

    static void setMainScreen(MainScreen screen) {
        instance.mainScreen = screen;
    }

    public void forceLogInView() {
        try {
            RealApplication.setScene("login-view.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean tryNewLogIn(String login) throws IOException {
        // TODO shouldn't this ClientInformation setting be in a different class
        if (!ClientInformation.isCorrectLogin(login))
            return false;

        ClientInformation client = ClientInformation.getInstance();
        client.setClientName(login);
        IdResult idResult;
        try {
            idResult = RealApplication.getDatabase().addUser(new UserInfo(client.getUsername(), 0));
        } catch (DatabaseException e) {
            return false;
        }
        client.logInWithId(idResult.getId());
        Logger.getAnonymousLogger().info("Client set: " + client.getUsername() + " " + client.getId());


        // now changes in UI, at this point login is considered successful

        RealApplication.setScene("base-view.fxml");
        mainScreen = MainScreen.getInstance();


        mainScreen.setTasks(Collections.emptyList());
        mainScreen.setMembers(Collections.emptyList());
        mainScreen.setTeams(Collections.emptyList());
        GetTeamsResult result = null;
        try {
            result = RealApplication.getDatabase().getTeams();
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
        }

        if (result != null)
            mainScreen.setTeams(result.getTeams()); // TODO is nullpointer possible?

        mainScreen.showTeams();

        return true;
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
}
