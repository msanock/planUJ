package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SingleTaskViewController implements Initializable, UserListController {

    TaskInfo taskInfo;
    @FXML
    public GridPane taskBox;
    @FXML
    public Label name;
    @FXML
    public Label status;
    @FXML
    public Label priority;
    @FXML
    public Label deadline;
    @FXML
    public TilePane users;
    @FXML
    public Button editButton;
    @FXML
    public Button editAssignedButton;

    boolean isEditAssignedButtonClicked;
    HashMap<UserInfo, SingleTaskViewController.UserButton> assignedUsers;



    public void setTask(TaskInfo task) {
        this.taskInfo = task;
    }

    @Override
    public void addUser(UserInfo user) {
        if (AppHandler.getInstance().AddUserToTask(user, taskInfo)) {
            SingleTaskViewController.UserButton newUser = new SingleTaskViewController.UserButton(user);
            users.getChildren().add(newUser);
            assignedUsers.put(user, newUser);
        }
    }

    @Override
    public void deleteUser(UserInfo user) {
        if (AppHandler.getInstance().removeUserFromTask(user, taskInfo)) {
            users.getChildren().remove(assignedUsers.remove(user));
        }
    }

    @Override
    public void cancel() {
        isEditAssignedButtonClicked = false;

    }

    class UserButton extends Button {
        UserInfo userInfo;
        UserButton(UserInfo userInfo) {
            super(userInfo.getUsername());
            this.getStyleClass().add("assigned-user-button");
            this.setGraphic(new FontIcon("bi-person:30"));
            this.userInfo = userInfo;
        }

        public UserInfo getUserInfo() {
            return userInfo;
        }
    }

    private String format(LocalDateTime deadline) {
//        if (deadline.isAfter(LocalDateTime.now())) {
//            if(deadline.isBefore(LocalDateTime.now().plusDays(7))) {
//                Duration duration = Duration.between(deadline, LocalDateTime.now());
//                long days = duration.toDaysPart();
//                duration = duration.minusDays(days);
//                long hours = duration.toHours();
//                duration = duration.minusHours(hours);
//                long minutes = duration.toMinutes();
//
//                StringBuilder stringBuilder = new StringBuilder();
//                if (days > 0) stringBuilder.append(days).append((days == 1) ? " day" : " days");
//                if (hours > 0) stringBuilder.append(hours).append((days == 1) ? " hour" : " hours");
//                if (minutes > 0) stringBuilder.append(minutes).append((minutes == 1) ? " minute" : "minutes");
//                return stringBuilder.toString();
//            }
//        }
        return deadline.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assignedUsers = new HashMap<>();
        name.setText(taskInfo.getInfo());
        status.setText(taskInfo.getStatus().name());
        priority.setText(String.valueOf(taskInfo.getPriority()));
        deadline.setText(format(taskInfo.getDeadline()));
        isEditAssignedButtonClicked = false;
        for (var user : taskInfo.getAssignedUsers()){
            UserButton button = new UserButton(user);
            users.getChildren().add(button);
            assignedUsers.put(user, button);
        }
    }

    public void handleEditButton(ActionEvent e) {
        MainScreenController.getInstance().openEditableTask(taskInfo);
    }

    public void handleEditAssignedButton(ActionEvent e) {
        isEditAssignedButtonClicked = ! isEditAssignedButtonClicked;
        MainScreenController.getInstance().membersView.action(taskInfo.getAssignedUsers(), this, isEditAssignedButtonClicked);
    }
}
