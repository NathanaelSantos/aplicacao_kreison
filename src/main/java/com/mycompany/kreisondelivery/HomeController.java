package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.Windows;

public class HomeController implements Initializable, Windows {

    App app = new App();
    private static boolean screenMax = false;

    @FXML
    private void handleClose(MouseEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void handleMinimize(MouseEvent event) throws IOException {
        app.getStage().setIconified(true);
    }

    @FXML
    private void screenLogin() throws IOException {
        App.setRoot("Login");
    }
    
    @FXML
    private void novaEntrega() throws IOException {
        App.setRoot("novaEntrega");
    }
    
    @FXML
    private void estoqueScreen() throws IOException {
        App.setRoot("estoque");
    }
    
    @FXML
    private void cadastraProduto() throws IOException {
        App.setRoot("produto");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
