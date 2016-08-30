package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import main.App;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import view.controller.MainViewController;

import static org.junit.Assert.assertTrue;

/**
 * <h1>Created by Mattias on 2016-08-30.</h1>
 */
public class MainViewTest extends Application {
    private App app;

    @Override
    public void start(Stage primaryStage) throws Exception {
        testToStartApp();
    }
    @Before
    public void init() throws Exception {
        new JFXPanel();
        app = new App();

    }
    @Test
    public void testToStartApp() throws Exception {
        Platform.runLater(() -> {
            try {
                app.start(new Stage());
                assertTrue(app.isLoaded());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testToAddTask() {
        App app = new App();
        Platform.runLater(() -> {
            try {
                app.start(new Stage());
                MainViewController controller = app.getController();
                assertTrue(controller.getTodos() > 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
