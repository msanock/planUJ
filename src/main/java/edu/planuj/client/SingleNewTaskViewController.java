package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.ResourceBundle;

public class SingleNewTaskViewController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        status.getItems().addAll(TaskInfo.Status.values());
        status.setValue(TaskInfo.Status.TODO);
        priority.setEditable(false);
        priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5));
        deadline.setValue(LocalDate.now());
    }

    public void setTask(TaskInfo task) {
        this.taskInfo = task;
    }
    public void handleAddButton(ActionEvent e) {
        taskInfo.setInfo(name.getText());
        taskInfo.setDeadline(deadline.getValue().atStartOfDay());
        taskInfo.setPriority(priority.getValue());
        taskInfo.setStatus(status.getValue());

        //some checks TODO add more and to Editable
        if (name.getText().isBlank()) {
            MainScreenController.getInstance().reportError(new Exception("new task with empty name"));
            return;
        }

        if (AppHandler.getInstance().addNewTask(taskInfo))
            MainScreenController.getInstance().acceptNewTask(taskInfo);
    }
}