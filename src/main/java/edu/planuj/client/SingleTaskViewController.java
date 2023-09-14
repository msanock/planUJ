package edu.planuj.client;

import edu.planuj.Utils.TaskInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

public class SingleTaskViewController implements Initializable {

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



    public void setTask(TaskInfo task) {
        this.taskInfo = task;


    }

    private String format(LocalDateTime deadline) {
        if (deadline.isAfter(LocalDateTime.now())) {
            if(deadline.isBefore(LocalDateTime.now().plusDays(7))) {
                Duration duration = Duration.between(deadline, LocalDateTime.now());
                long days = duration.toDaysPart();
                duration = duration.minusDays(days);
                long hours = duration.toHours();
                duration = duration.minusHours(hours);
                long minutes = duration.toMinutes();

                StringBuilder stringBuilder = new StringBuilder();
                if (days > 0) stringBuilder.append(days).append((days == 1) ? " day" : " days");
                if (hours > 0) stringBuilder.append(hours).append((days == 1) ? " hour" : " hours");
                if (minutes > 0) stringBuilder.append(minutes).append((minutes == 1) ? " minute" : "minutes");
                return stringBuilder.toString();
            }
        }
        return deadline.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(taskInfo.getInfo());
        status.setText("STATUS: \n" + taskInfo.getStatus().name());
        priority.setText("PRIORITY: " + taskInfo.getPriority());
        deadline.setText("DEADLINE: \n" + format(taskInfo.getDeadline()));
        for (var user : taskInfo.getAssignedUsers()){
            users.getChildren().add(new Button(user.getUsername()));
        }
    }

    public void handleEditButton(ActionEvent e) {
        AppHandler.getInstance().openEditableTask(taskInfo);

    }
}
