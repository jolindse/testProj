package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.controller.MainViewController;

import java.io.IOException;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class App extends Application {
    private Parent root;
    private boolean loaded = false;
    private FXMLLoader loader;

    @Override
    public void start(Stage stage) throws Exception {
        root = loadGui();
        Scene mainScene = new Scene(root, 600, 400);
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
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

}
