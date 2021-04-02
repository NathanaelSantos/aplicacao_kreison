package model;

import javafx.scene.control.Alert;

public class AlertDialog {
    public void alertDialog(String msg) {
        Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
        dialogoInfo.setHeaderText(msg);
        dialogoInfo.showAndWait();
    }
}
