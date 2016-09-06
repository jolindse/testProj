package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.App;
import main.Controller;
import models.Task;
import view.components.TaskCard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class MainViewController implements Initializable {
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonRemove;
    @FXML
    private GridPane mainGrid;
    @FXML
    private ScrollPane todoScrollPane;
    @FXML
    private ScrollPane progressScrollPane;
    @FXML
    private ScrollPane testScrollPane;
    @FXML
    private ScrollPane doneScrollPane;
    @FXML
    private VBox todoBox;
    @FXML
    private VBox progressBox;
    @FXML
    private VBox testBox;
    @FXML
    private VBox doneBox;

    private ObservableList<TaskCard> todoList, progressList, testList, doneList;
    private ObservableList<TaskCard> listArray[];
    private TaskCard selectedTask = null;
    private FXMLLoader loader;

    private Controller mainController;
    @FXML
    public void addTask() {
        loader.setLocation(getClass().getResource("../../addNewTaskView.fxml"));
        loadWindow();
        AddNewTaskController controller = loader.getController();
        controller.setMainController(this);

        redraw();
    }

    @FXML
    private void removeTask() {
        if (selectedTask != null) {
            int index = selectedTask.getStatus();
            listArray[index].remove(selectedTask);
            mainController.removeTask(selectedTask.getTask());
            redraw();
        }
    }

    public void moveTask(TaskCard task) {
        int index = task.getStatus();
        listArray[index].remove(task);
        listArray[index+1].add(task);
        redraw();
    }
    public int getTodos() {
        return todoBox.getChildren().size();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loader = new FXMLLoader();
        mainController = App.getMainController();
        todoList = FXCollections.observableArrayList();
        progressList = FXCollections.observableArrayList();
        testList = FXCollections.observableArrayList();
        doneList = FXCollections.observableArrayList();
        listArray = new ObservableList[]{todoList, progressList, testList, doneList};
    }

    private void redraw() {
        todoBox.getChildren().setAll(todoList);
        progressBox.getChildren().setAll(progressList);
        testBox.getChildren().setAll(testList);
        doneBox.getChildren().setAll(doneList);
    }

    public void setSelected(TaskCard taskSelected) {
        for(int i = 0; i<listArray.length;i++) {
            listArray[i].forEach(task -> {
                task.getHeader().setTextFill(Color.BLACK);
            });
        }
        taskSelected.getHeader().setTextFill(Color.BLUE);
        selectedTask = taskSelected;
    }

    private void loadWindow() {
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTask(Task task) {
        Task completeTask = mainController.addTask(task);
        TaskCard card = new TaskCard(this, completeTask);
        todoList.add(card);
        redraw();
    }
}
