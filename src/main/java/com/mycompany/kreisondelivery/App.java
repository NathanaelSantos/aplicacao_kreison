package com.mycompany.kreisondelivery;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage stage;
    
    public static void main(String[] args) {
        Application.launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {

        stage.getIcons (). add (new Image(new File("images/icon.ico").toURI().toString()));
        Image icon =  new Image(getClass().getResourceAsStream("/images/k.png"));
        stage.getIcons().add(icon);

        App.stage = stage;
        scene = new Scene(loadFXML("Login"), 800, 500);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public Stage getStage() {
        return stage;
    }

}
