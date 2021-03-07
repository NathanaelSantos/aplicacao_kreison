package com.mycompany.kreisondelivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        App.stage = stage;
        scene = new Scene(loadFXML("Login"), 800, 500);
        //stage.initStyle(StageStyle.UNDECORATED);
        //new MoveWindow().moveWindow(scene, stage);

        stage.setScene(scene);
        stage.show();
    }


    static void setRoot(String fxml) throws IOException {
        stage.setFullScreen(false);
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

}
