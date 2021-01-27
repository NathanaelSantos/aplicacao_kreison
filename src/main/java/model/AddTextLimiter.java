package model;

import javafx.scene.control.TextField;

public class AddTextLimiter {
    public static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (textField.getText().length() > maxLength) {
                String s = textField.getText().substring(0, maxLength);
                textField.setText(s);
            }
        });
    }
}
