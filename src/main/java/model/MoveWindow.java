
package model;

public class MoveWindow {

    private double posX = 0.00;
    private double posY = 0.00;

    public void moveWindow(javafx.scene.Scene scene, javafx.stage.Stage stage) {
        scene.setOnMousePressed(e -> {
            setPosX(stage.getX() - e.getScreenX());
            setPosY(stage.getY() - e.getScreenY());
        });
        scene.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + getPosX());
            stage.setY(e.getScreenY() + getPosY());
        });
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}
