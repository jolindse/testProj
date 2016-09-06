package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controller.MainViewController;

import java.awt.*;
import java.io.IOException;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class App extends Application {
    private Parent root;
    private boolean loaded = false;
    private FXMLLoader loader;
    private static Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        final double WIDTH = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        final double HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        root = loadGui();
        Scene mainScene = new Scene(root, WIDTH, HEIGHT);
        stage.setScene(mainScene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        controller = new Controller();
        launch(args);
    }

    public Parent loadGui() {
        Parent view = null;
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../mainView.fxml"));
        try {
            view = loader.load();
            loaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public MainViewController getController() {
        return loader.getController();
    }

    public static Controller getMainController() {
        return controller;
    }

}
