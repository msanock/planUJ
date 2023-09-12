package client;

import Server.sql.DatabaseException;
import Utils.OperationResults.GetTasksResult;
import Utils.OperationResults.GetTeamsResult;
import Utils.OperationResults.IdResult;
import Utils.TeamInfo;
import Utils.UserInfo;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

public class AppHandler {
    MainScreen mainScreen;

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
            mainScreen.setLogInViewExitable(false);
            mainScreen.showLogInView();
    }

    public void tryNewLogIn(String login) {
        // TODO shouldn't this ClientInformation setting be in a different class
        if (!ClientInformation.isCorrectLogin(login))
            return;

        ClientInformation client = ClientInformation.getInstance();
        client.setClientName(login);
        UserInfo newUser = new UserInfo(login, -1);
        IdResult idResult;
        try {
            idResult = RealApplication.getDatabase().addUser(newUser);
        } catch (DatabaseException e) {
            mainScreen.reportError(e);
            return;
        }
        client.setClientInfo(login, idResult.getId());
        Logger.getAnonymousLogger().info("Client set: " + client.getUsername() + " " + client.getId());


        // now changes in UI, at this point login succeeded
        mainScreen.setTasks(Collections.emptyList());
        mainScreen.setMembers(Collections.emptyList());
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

    public void openTasksView(TeamInfo team) {
        GetTasksResult tasksResult = null;
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

    public void closeTeamsView() {
        mainScreen.closeTeams();
    }
}
