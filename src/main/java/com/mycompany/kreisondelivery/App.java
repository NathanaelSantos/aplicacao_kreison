package com.mycompany.kreisondelivery;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Deserializer;
import model.Serializer;

public class App extends Application {

    private static Scene scene;
    public static Stage stage;
    Deserializer deserializer = new Deserializer();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Application.launch(App.class, args);
        new Serializer().Serializer(stage.isMaximized());
    }

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/k.png")));

        App.stage = stage;
        scene = new Scene(loadFXML("home"), 800, 500);
        stage.setScene(scene);

        deserializer.deserializer();
        if (deserializer.isFullScreen()) {
            stage.setMaximized(true);
        } else {
            stage.setMaximized(false);
        }

        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

}
