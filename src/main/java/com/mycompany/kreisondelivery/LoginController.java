/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;

    @FXML
    private Label alerta;
    @FXML
    private Label alertaCpf;
    @FXML
    private Label txtCadastra;
    @FXML
    private Label recoverPassword;

    @FXML
    private void recoverPassword() throws IOException {
        App.setRoot("recoverPassword");
    }

    @FXML
    private void switchToPrimary() throws Exception {

        ReturnConnection returnConnection = new ReturnConnection();
        AlertDialog alertDialog = new AlertDialog();

        if (getLogin().getText().isBlank() || getSenha().getText().isBlank()) {
            getAlerta().setVisible(true);
        } else {
            getAlerta().setVisible(false);
            if (new ValidateCPF().validate(getLogin().getText())) {

                if (returnConnection.getConnection() != null) {
                    try {
                        if (new StringUtil().gerarHash(getSenha().getText())
                                .equals(new LoginConnection().loginConection(getLogin().getText(), "senha")))
                            App.setRoot("home");
                        else
                            new AlertDialog().alertDialog("Erro ao fazer login!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    new AlertDialog().alertDialog("Erro de conexao!");
                }
            } else {
                alertDialog.alertDialog("CPF fornecido não é válido!");
            }
        }
    }

    @FXML
    private void emptyFilds() {
        getAlerta().setVisible(false);
    }

    @FXML
    private void mouseEnteredTxtCad() {
        getTxtCadastra().setStyle("-fx-text-fill: #019AB8");
    }

    @FXML
    private void mouseExitedTxtCad() {
        getTxtCadastra().setStyle("-fx-text-fill-color: #019ab8");
    }

    @FXML
    private void mouseEnteredRecoverPassword() {
        getRecoverPassword().setStyle("-fx-text-fill: #019ab8");
    }

    @FXML
    private void mouseExitedRecoverPassword() {
        getRecoverPassword().setStyle("-fx-text-fill-color: #00b4d8");
    }

    @FXML
    private void buttonLoginEntered() {
        getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #019ab8");
    }

    @FXML
    private void setButtonLoginExited() {
        getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8");
    }

    @FXML
    private void mousePressedButtonLogin() {
        getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #019ab8");
    }

    @FXML
    private void mouseReleaseButtonLogin() {
        getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8");
    }

    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastra");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getAlerta().setVisible(false);
        getAlertaCpf().setVisible(false);

        isTextFormatterNumber(getLogin());
        addTextLimiter(getLogin(), 11);
        isTextFormatterNumber(getSenha());
        addTextLimiter(getSenha(), 6);
    }

    public TextField getLogin() {
        return login;
    }

    public void setLogin(TextField login) {
        this.login = login;
    }

    public PasswordField getSenha() {
        return senha;
    }

    public void setSenha(PasswordField senha) {
        this.senha = senha;
    }

    public Label getAlerta() {
        return alerta;
    }

    public void setAlerta(Label alerta) {
        this.alerta = alerta;
    }

    public Label getAlertaCpf() {
        return alertaCpf;
    }

    public void setAlertaCpf(Label alertaCpf) {
        this.alertaCpf = alertaCpf;
    }

    public Label getTxtCadastra() {
        return txtCadastra;
    }

    public void setTxtCadastra(Label txtCadastra) {
        this.txtCadastra = txtCadastra;
    }

    public Label getRecoverPassword() {
        return recoverPassword;
    }

    public void setRecoverPassword(Label recoverPassword) {
        this.recoverPassword = recoverPassword;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(Button btnLogin) {
        this.btnLogin = btnLogin;
    }
}
