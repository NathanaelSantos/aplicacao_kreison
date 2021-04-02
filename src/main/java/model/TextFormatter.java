package model;

import javafx.scene.control.TextField;

public class TextFormatter {
    public static void isTextFormatterNumber(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*")) {
                textField.setText(oldValue);
            }
        });
    }

    public static void isTextFormatterString(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-z A-Z]*")) {
                textField.setText(oldValue);
            }
        });
    }

    public static void isTextFormatterFloat(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^([+-]?\\d*\\.?\\d*)$")) {
                textField.setText(oldValue);
            }
        });
    }
}
