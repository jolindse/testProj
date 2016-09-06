package view.controller;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

/**
 * <h1>Created by Mattias on 2016-09-06.</h1>
 */
public class ExpandedController {

    @FXML
    private Button collapseButton;
    @FXML
    private BorderPane expandedPane;

    @FXML
    private void collapse() {
        ScaleTransition sT = new ScaleTransition(Duration.seconds(1), expandedPane);
        sT.setFromY(1);
        sT.setToY(0);
        sT.playFromStart();
    }

    public void expand() {
        expandedPane.setScaleY(0.0);
        ScaleTransition sT = new ScaleTransition(Duration.seconds(1), expandedPane);
        sT.setFromY(0);
        sT.setToY(1);
        sT.playFromStart();
    }
}
