package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import edu.planuj.Utils.UserInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SingleNewTaskViewController implements Initializable{

    private TaskInfo taskInfo;
    @FXML
    public GridPane taskBox;
    @FXML
    public TextField name;
    @FXML
    public ChoiceBox<TaskInfo.Status> status;
    @FXML
    public Spinner<Integer> priority;
    @FXML
    public DatePicker deadline;
    @FXML
    public TilePane users;
    @FXML
    public Button addButton;

    HashMap<UserInfo, Button> assignedUsers;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.getItems().addAll(TaskInfo.Status.values());
        status.setValue(TaskInfo.Status.TODO);
        priority.setEditable(false);
        priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));
        deadline.setValue(LocalDate.now());

        assignedUsers = new HashMap<>();
        for (UserInfo user : taskInfo.getAssignedUsers()) {
            Button newButton = new UserButton(user);
            assignedUsers.put(user, newButton);
            users.getChildren().add(newButton);
        }
    }

    public void setTask(TaskInfo task) {
        this.taskInfo = task;
    }
    public void handleAddButton(ActionEvent e) {
        //some checks TODO add more and to Editable
        if (name.getText().isBlank()) {
            MainScreenController.getInstance().reportError(new Exception("new task with empty name"));
            return;
        }

        taskInfo.setInfo(name.getText());
        taskInfo.setDeadline(deadline.getValue().atStartOfDay());
        taskInfo.setPriority(priority.getValue());
        taskInfo.setStatus(status.getValue());
        taskInfo.setAssignedUsers(assignedUsers.keySet().stream().toList());

        if (AppHandler.getInstance().addNewTask(taskInfo))
            MainScreenController.getInstance().acceptNewTask(taskInfo);

    }
}
