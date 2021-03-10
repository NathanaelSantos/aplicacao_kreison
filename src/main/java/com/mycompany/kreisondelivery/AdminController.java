package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void cadastraProduto() throws IOException {
        App.setRoot("produto");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}