
package model;

public class MoveWindow {

    private double posX = 0.00;
    private double posY = 0.00;

    public void moveWindow(javafx.scene.Scene scene, javafx.stage.Stage stage) {
        scene.setOnMousePressed(e -> {
            posX = stage.getX() - e.getScreenX();
            posY = stage.getY() - e.getScreenY();
        });
        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + posX);
            stage.setY(e.getScreenY() + posY);
        });
    }
}
