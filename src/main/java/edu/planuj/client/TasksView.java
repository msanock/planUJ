package edu.planuj.client;


import edu.planuj.Utils.TaskInfo;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public class TasksView extends VBox {
    HashMap<TaskInfo, SingleTask> tasks;
    Integer numberOfTasks;

    public TasksView() {
        numberOfTasks = 0; //
        tasks = new HashMap<>();
    }

    public void setTaskEditable(TaskInfo task) {
        SingleTask oldSingleTask = tasks.get(task);
        SingleTask editableSingleTask = null;
        try {
            editableSingleTask = SingleTaskViewFactory.getInstance().getEditableSingleTask(task);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.getChildren().remove(oldSingleTask.getIndex());
        this.getChildren().add(oldSingleTask.getIndex(), editableSingleTask.getPane());
    }

    public void setTasks(Collection<TaskInfo> tasks) {
        this.tasks.clear();
        this.getChildren().clear();
        for (var task : tasks) {
            SingleTask newSingleTask = null;
            try {
                newSingleTask = SingleTaskViewFactory.getInstance().getSingleTask(task);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.tasks.put(task, newSingleTask);
            this.getChildren().add(newSingleTask.getPane());
            newSingleTask.setIndex(this.getChildren().indexOf(newSingleTask.getPane()));
            VBox.setVgrow(newSingleTask.getPane(), Priority.ALWAYS);
        }
    }

    public void addTask(TaskInfo task) {
        SingleTask newSingleTaskView = null;
        try {
            newSingleTaskView = SingleTaskViewFactory.getInstance().getSingleTask(task);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.tasks.put(task, newSingleTaskView);
        this.getChildren().add(newSingleTaskView.getPane());
        newSingleTaskView.setIndex(this.getChildren().indexOf(newSingleTaskView.getPane()));
        VBox.setVgrow(newSingleTaskView.getPane(), Priority.ALWAYS);
    }

    public void deleteTask(TaskInfo task) {
        SingleTask which = tasks.remove(task);
        this.getChildren().remove(which.getPane());
    }

}

class SingleTask{
    private GridPane pane;
    private Initializable controller;
    private Integer index;

    SingleTask(GridPane pane, Initializable controller){
        this.pane = pane;
        this.controller = controller;
    }

    public GridPane getPane() {
        return pane;
    }

    public Initializable getController() {
        return controller;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}

class SingleTaskViewFactory {

    private static SingleTaskViewFactory instance;

    public SingleTaskViewFactory() {

    }

    static SingleTaskViewFactory getInstance() {
        if (instance == null)
            instance = new SingleTaskViewFactory();
        return instance;
    }

    public SingleTask getSingleTask(TaskInfo task) throws IOException {
        FXMLLoader loader = new FXMLLoader(TasksView.class.getResource("single-task.fxml"));
        SingleTaskViewController controller = new SingleTaskViewController();
        controller.setTask(task);
        loader.setController(controller);
        return new SingleTask(loader.load(), controller);
    }

    public SingleTask getEditableSingleTask(TaskInfo task) throws IOException {
        FXMLLoader loader = new FXMLLoader(TasksView.class.getResource("editable-single-task.fxml"));
        SingleEditableTaskViewController controller = new SingleEditableTaskViewController();
        controller.setTask(task);
        loader.setController(controller);
        return new SingleTask(loader.load(), controller);
    }
}

