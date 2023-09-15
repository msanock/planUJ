package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.util.ResourceBundle;

public class SingleEditableTaskViewController implements Initializable {

    TaskInfo taskInfo;
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
    public Button setButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(taskInfo.getInfo());
        status.getItems().addAll(TaskInfo.Status.values());
        status.setValue(taskInfo.getStatus());
        priority.setEditable(false);
        priority.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, taskInfo.getPriority()));
        deadline.setValue(taskInfo.getDeadline().toLocalDate());
    }

    public void setTask(TaskInfo task) {
        this.taskInfo = task;
    }

    public void handleSetButton(ActionEvent e) {
        taskInfo.setInfo(name.getText());
        taskInfo.setDeadline(deadline.getValue().atStartOfDay());
        taskInfo.setPriority(priority.getValue());
        taskInfo.setStatus(status.getValue());

        if (AppHandler.getInstance().updateTask(taskInfo))
            MainScreenController.getInstance().changeToNormalTask(taskInfo);
    }
}
