package view.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Task;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>Created by Mattias on 2016-09-06.</h1>
 */
public class AddNewTaskController {
    @FXML
    private TextField taskName;
    @FXML
    private TextArea taskInfo;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private ComboBox storyDropdown;
    @FXML
    private ComboBox sprintDropdown;
    @FXML
    private Parent root;

    private MainViewController mainViewController;
    private ObservableList<Integer> sprintList;

    @FXML
    public void save() {
        if(taskName.getText() != null) {
            Task task = new Task();
            task.setName(taskName.getText());
            task.setInfo(taskInfo.getText());
            if(sprintDropdown.getSelectionModel().getSelectedItem() != null) {
                task.setSprint((int) sprintDropdown.getSelectionModel().getSelectedItem());
            } else {
                task.setSprint(0);
            }
            task.setStatus(0);
            mainViewController.saveTask(task);
            close();
        }

    }
    @FXML
    public void close() {
        Stage thisStage = (Stage) cancelButton.getScene().getWindow();
        thisStage.close();
    }

    public void setMainController(MainViewController mainController) {
        this.mainViewController = mainController;
        init();
    }

    private void init() {
        sprintList = FXCollections.observableArrayList();
        sprintList.add(1);
        sprintDropdown.setItems(sprintList);
    }

}
