package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.TeamInfo;
import edu.planuj.Utils.TeamUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class MainScreen {
    @FXML
    public HBox pane;
    @FXML
    public StackPane main;
    @FXML
    public Button teamsButton;
    private boolean isTeamsButtonPressed;
    @FXML
    public TeamsView teamsView;
    @FXML
    public MembersView membersView;
    @FXML
    public TasksView tasksView;
    static MainScreen instance;


    public static MainScreen getInstance() {
        if(instance==null)
            instance = new MainScreen();
        return instance;
    }

    public MainScreen(){
        AppHandler.setMainScreen(this);

        instance = this;
        teamsView = new TeamsView();
        isTeamsButtonPressed = false;
    }

    public void setTasks(Collection <TaskInfo> tasks) {
        tasksView.setTasks(tasks);
    }

    public void setMembers(Collection<TeamUser> members) {
        membersView.setMembers(members);
    }

    public void setTeams(Collection<TeamInfo> teams) {
        teamsView.setTeams(teams);
    }

    public void addTask(TaskInfo task) {
        tasksView.addTask(task);
    }
    public void addUserToTask(TaskInfo task, String user) {

    }

    public void addMember(TeamUser name) {
        membersView.addMember(name);
    } // ?


    public void handleTeamsButton(ActionEvent actionEvent) {
        if (isTeamsButtonPressed) {
            // operations on button

            // operations on pane
            isTeamsButtonPressed = false;
            main.getChildren().remove(teamsView);
        } else {
            // operations on button

            // operations on pane

            isTeamsButtonPressed = true;
            main.getChildren().add(teamsView);
        }
    }
    public void reportError(Exception e) {
        Logger.getAnonymousLogger().info("Error: " + e.getMessage());
    }

    public void showTeams() {
        if (!main.getChildren().contains(teamsView)) {
            main.getChildren().add(teamsView);
            isTeamsButtonPressed = true;
        }
    }

    public void closeTeams() {
        if (main.getChildren().contains(teamsView)) {
            main.getChildren().remove(teamsView);
            isTeamsButtonPressed = false;
        }
    }

    public void openEditableTask(TaskInfo taskInfo) {
        tasksView.changeToEditableTask(taskInfo);
    }

    public void changeToNormalTask(TaskInfo taskInfo) {
        tasksView.changeToNormalTask(taskInfo);
        membersView.unmarkAll();
    }

    public void acceptNewTask(TaskInfo taskInfo) {
        tasksView.acceptNewTask(taskInfo);
        membersView.unmarkAll();
    }

    public void cancelTaskCreation(TaskInfo taskInfo) {
        tasksView.cancelTaskCreation(taskInfo);
        membersView.unmarkAll();
    }
}
