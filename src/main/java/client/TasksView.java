package client;


import Utils.TaskInfo;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Collection;
import java.util.HashMap;


public class TasksView extends VBox {
    HashMap<TaskInfo, SingleTaskView> tasks;
    Integer numberOfTasks;

    public TasksView() {
        numberOfTasks = 0;
        tasks = new HashMap<>();
    }

    public void setTasks(Collection<TaskInfo> tasks) {
        this.tasks.clear();
        this.getChildren().clear();
        for (var task : tasks) {
            SingleTaskView newSingleTaskView = new SingleTaskView(task);
            this.tasks.put(task, newSingleTaskView);
            this.getChildren().add(newSingleTaskView);
        }
    }

    public void addTask(TaskInfo task) {
        SingleTaskView newSingleTaskView = new SingleTaskView(task);
        this.tasks.put(task, newSingleTaskView);
        this.getChildren().add(newSingleTaskView);
    }

    public void deleteTask(TaskInfo task) {
        SingleTaskView which = tasks.remove(task);
        this.getChildren().remove(which);
    }

}

class SingleTaskView extends VBox {
    // https://www.pixelduke.com/fxskins/
    TaskInfo task;
    TaskLabel label;
    TaskMembers taskMembers;

    public SingleTaskView(TaskInfo task){
        super();

        this.setBorder(new Border(new BorderStroke(Color.AZURE, BorderStrokeStyle.SOLID, new CornerRadii(5.), new BorderWidths(4))));
        this.setPadding(new Insets(50));
        setOpaqueInsets(new Insets(70));
        HBox.setHgrow(this, Priority.ALWAYS);

        this.task = task;

        label = new TaskLabel(task.getInfo());
        this.getChildren().add(label);

        taskMembers = new TaskMembers();
        this.getChildren().add(taskMembers);

    }

    public static void handleEntered(MouseEvent e){

    }

    public static void handleExited(MouseEvent e){

    }
}

class TaskLabel extends Label{
    TaskLabel(String label) {
        //this.setGraphic("jakaś grafika );
        this.setMinHeight(60);
        this.setMinWidth(20);
        this.autosize();
        this.setText(label);
        this.setFont(new Font(50)); // TODO set font family, and bold
        //this.setTextOverrun(OverrunStyle.CLIP);
        this.setWrapText(false);
        this.setPadding(new Insets(7));

        this.setTextFill(Color.AQUA);
    }
}

class TaskMembers extends TilePane {
    HashMap<String, Button> assignedMembers;
    TaskMembers() {
        this.setOrientation(Orientation.HORIZONTAL);
        this.setHgap(3);
        this.setVgap(4);

    }

    public void setAssigned(Collection<String> assignedMembers) {
        this.getChildren().clear();
        this.getChildren().addAll(assignedMembers.stream().map((assigned) -> new Button(assigned)).toList());
    }

    public void addAssigned(String name) {
        Button newButton = new Button(name);
        this.getChildren().add(newButton);
        this.assignedMembers.put(name, newButton);
    }

    public void deleteAssigned(String name) {
        this.getChildren().remove(assignedMembers.remove(name));
    }
}
