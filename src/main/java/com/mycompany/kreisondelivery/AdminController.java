package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private Button buttonCadastraProduto;
    @FXML private Button buttonDeletaEditaProduto;
    @FXML private Button buttonDeletaFuncionario;

    @FXML private void mouseEnteredButtonCadastraProduto() { getButtonCadastraProduto().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #009abc"); }
    @FXML private void mouseExitedButtonCadastraProduto() { getButtonCadastraProduto().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #00b4d8"); }
    @FXML private void mouseEnteredButtonDeletaEditaProduto() { getButtonDeletaEditaProduto().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #009abc"); }
    @FXML private void mouseExitedButtonDeletaEditaProduto() { getButtonDeletaEditaProduto().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #00b4d8"); }
    @FXML private void mouseEnteredButtonDeletaFuncionario() { getButtonDeletaFuncionario().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #009abc"); }
    @FXML private void mouseExitedButtonDeletaFuncionario() { getButtonDeletaFuncionario().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #00b4d8"); }

    @FXML private void homeScreen() throws IOException { App.setRoot("home"); }

    @FXML private void cadastraProduto() throws IOException { App.setRoot("produto"); }
    @FXML private void editOrDeleteProduct() throws IOException { App.setRoot("editOrDeleteProduct"); }

    public void updateTableProduct() throws IOException { editOrDeleteProduct(); }

    @FXML private void deletaFuncionario() throws IOException { App.setRoot("deletaFuncionario"); }

    @Override public void initialize(URL location, ResourceBundle resources) { }

    public Button getButtonCadastraProduto() {
        return buttonCadastraProduto;
    }

    public void setButtonCadastraProduto(Button buttonCadastraProduto) { this.buttonCadastraProduto = buttonCadastraProduto; }

    public Button getButtonDeletaEditaProduto() {
        return buttonDeletaEditaProduto;
    }

    public void setButtonDeletaEditaProduto(Button buttonDeletaEditaProduto) { this.buttonDeletaEditaProduto = buttonDeletaEditaProduto; }

    public Button getButtonDeletaFuncionario() {
        return buttonDeletaFuncionario;
    }

    public void setButtonDeletaFuncionario(Button buttonDeletaFuncionario) {
        this.buttonDeletaFuncionario = buttonDeletaFuncionario;
    }
}
