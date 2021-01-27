/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Windows;

public class LoginController implements Initializable, Windows {


    @FXML
    private void handleClose(MouseEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void handleMinimize(MouseEvent event) throws IOException {
        new App().getStage().setIconified(true);
    }

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("home");
    }
    
    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastra");      
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
