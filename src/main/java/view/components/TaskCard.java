package view.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import models.Task;
import view.controller.ExpandedController;
import view.controller.MainViewController;

import java.io.IOException;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class TaskCard extends AnchorPane {
    private int status;
    private Color statusColor[] = new Color[] {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN};
    private Label header = new Label();
    private Task task;
    private MainViewController controller;
    private BorderPane expandedPane;
    private BooleanProperty isExpanded = new SimpleBooleanProperty(false);

    public TaskCard(MainViewController controller, Task task) {
        this.controller = controller;
        this.status = 0;
        this.task = task;
        header.setText(task.getName());


        Button expandButton = new Button();
        BackgroundImage expandImage = new BackgroundImage(new Image(getClass()
            .getResource("../../icons/arrowDown.png").toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
        expandButton.setMinWidth(expandImage.getImage().getWidth());
        expandButton.setBackground(new Background(expandImage));
        expandButton.disableProperty().bind(isExpanded);
        expandButton.setOnAction(e -> {
            expand();
        });

        Button moveButton = new Button();
        BackgroundImage moveImage = new BackgroundImage(new Image(getClass()
            .getResource("../../icons/arrowRight.png").toString()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
        moveButton.setBackground(new Background(moveImage));
        moveButton.setMinWidth(moveImage.getImage().getWidth());
        moveButton.getStyleClass().add("taskButton");
        moveButton.setOnAction(moveAction -> {
            if (status < 3) {
                this.controller.moveTask(this);
                status++;
                setColor();
            }
        });
        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setMinWidth((controller.getMainGrid().getWidth()/4) -
            header.getWidth() - moveButton.getWidth() - expandButton.getWidth());
        separator.setVisible(false);
        getChildren().addAll(header, separator, expandButton, moveButton);
        setLeftAnchor(header, 10.0);
        setRightAnchor(moveButton, 10.0);
        setRightAnchor(expandButton, 65.0);

        this.setOnMouseClicked(select -> this.controller.setSelected(this));
        setColor();
    }

    private void expand() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../TaskCard.fxml"));
        try {
            setExpanded(loader.load());
            ExpandedController expandedController = loader.getController();
            this.controller.expandTask(this, expandedController);
            isExpanded.set(true);
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

    public void setExpanded(BorderPane expandedPane) {
        this.expandedPane = expandedPane;
    }

    public BorderPane getExpanded() {
        return this.expandedPane;
    }

    public Task getTask() {
        return task;
    }

    public void setCollapsed() {
        isExpanded.set(false);
    }
}
