package app;

import app.util.SceneManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        SceneManager.setStage(stage);
        SceneManager.switchTo("primary.fxml");
    }

    public static void main(String[] args) {
        launch();
    }

}
