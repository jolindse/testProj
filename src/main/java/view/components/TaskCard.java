package view.components;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Individual;
import view.controller.MainViewController;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class TaskCard extends GridPane {
    private int status;
    private Color statusColor[] = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
    private Label header = new Label("New Task");

    public TaskCard(MainViewController controller) {
        this.status = 0;
        add(header, 0, 0);
        Button moveButton = new Button(">>");
        add(moveButton, 1, 0);
        moveButton.setOnAction(moveAction -> {
            if (status < 3) {
                controller.moveTask(this);
                status++;
                setColor();
            }
        });
        this.setOnMouseClicked(select -> controller.setSelected(this));
        setColor();
    }

    public int getStatus() {
        return status;
    }

    public Label getHeader() {
        return header;
    }

    private void setColor() {
        this.setBackground(new Background(new BackgroundFill(statusColor[getStatus()], null, null)));
    }
}
