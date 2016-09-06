package view.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import view.components.TaskCard;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * <h1>Created by Mattias on 2016-09-06.</h1>
 */
public class ExpandedController implements Initializable {

    @FXML
    private BorderPane expandedPane;
    @FXML
    private TextArea infoTextArea;
    @FXML
    private Label sprintLabel;

    private MainViewController mainViewController;
    private TaskCard card;
    private boolean isEditing = false;

    @FXML
    private void collapse() {
        ScaleTransition sT = new ScaleTransition(Duration.seconds(0.5), expandedPane);
        sT.setFromY(1);
        sT.setToY(0);
        sT.playFromStart();
        sT.setOnFinished(e -> {
            mainViewController.collapse(card);
        });

    }

    @FXML
    private void edit() {
        if(isEditing) {
            mainViewController.updateTaskInfo(card);
        }
        isEditing = !isEditing;
        infoTextArea.setEditable(isEditing);
    }

    public void expand(TaskCard card, MainViewController mainViewController) {
        this.card = card;
        this.mainViewController = mainViewController;
        this.card.setExpanded(expandedPane);
        expandedPane.setScaleY(0.0);
        ScaleTransition sT = new ScaleTransition(Duration.seconds(0.5), expandedPane);
        sT.setFromY(0);
        sT.setToY(1);
        sT.playFromStart();
        infoTextArea.setText(card.getTask().getInfo());
        sprintLabel.setText("Sprint #" + String.valueOf(card.getTask().getSprint()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
