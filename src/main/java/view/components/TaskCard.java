package view.components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Task;
import view.controller.MainViewController;

import java.io.IOException;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class TaskCard extends GridPane {
    private int status;
    private Color statusColor[] = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
    private Label header = new Label();
    private Task task;
    private MainViewController controller;

    public TaskCard(MainViewController controller, Task task) {
        this.controller = controller;
        this.status = 0;
        this.task = task;
        this.header.setText(task.getName());
        add(header, 0, 0);

        Button expandButton = new Button("V");
        add(expandButton, 1, 0);
        expandButton.setOnAction(e -> {
            expand();
        });

        Button moveButton = new Button(">>");
        add(moveButton, 2, 0);
        moveButton.getStyleClass().add("taskButton");
        moveButton.setOnAction(moveAction -> {
            if (status < 3) {
                this.controller.moveTask(this);
                status++;
                setColor();
            }
        });
        this.setOnMouseClicked(select -> this.controller.setSelected(this));
        setColor();
    }

    private void expand() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../TaskCard.fxml"));
        try {
            Parent taskCardRoot = loader.load();
            this.controller.expandTask(this, taskCardRoot, loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }

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

    public Task getTask() {
        return task;
    }
}
