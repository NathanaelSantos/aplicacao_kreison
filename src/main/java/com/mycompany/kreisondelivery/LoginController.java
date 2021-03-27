/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class LoginController implements Initializable {

    private static boolean setVisibleImgDonate = false;

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
    private ImageView imgDonate;
    @FXML
    private Label donate;


    @FXML
    private void recoverPassword() throws IOException {
        App.setRoot("recoverPassword");
    }

    @FXML
    private void setVisibleImgDonate() throws IOException {
        if(!setVisibleImgDonate){
            imgDonate.setVisible(true);
            setVisibleImgDonate = true;
        }else {
            imgDonate.setVisible(false);
            setVisibleImgDonate = false;
        }
    }

    @FXML
    private void switchToPrimary() throws Exception {

        ReturnConnection returnConnection = new ReturnConnection();
        AlertDialog alertDialog = new AlertDialog();

        if(getLogin().getText().isBlank() || getSenha().getText().isBlank()){
            getAlerta().setVisible(true);
        }else {
            getAlerta().setVisible(false);
            if (new ValidateCPF().validate(getLogin().getText())) {
                getAlertaCpf().setVisible(false);

                    if ( returnConnection.getConnection() != null) {
                        try {
                            if (new StringUtil().gerarHash(getSenha().getText()).equals(new LoginConnection().loginConection(getLogin().getText(),"senha")))
                                App.setRoot("home");
                            else
                                new AlertDialog().alertDialog("Erro ao fazer login!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        new AlertDialog().alertDialog("Erro de conexao!");
                    }
            }else{
                alertDialog.alertDialog("CPF fornecido não é válido!");
            }
        }
    }

    @FXML
    private void mouseEnteredTxtCad(){ getTxtCadastra().setStyle("-fx-text-fill:  #037f99");}
    @FXML
    private void mouseExitedTxtCad(){ getTxtCadastra().setStyle("-fx-text-fill-color:  #ffffff");}
    @FXML
    private void mouseEnteredRecoverPassword(){ getRecoverPassword().setStyle("-fx-text-fill:  #038ca9"); }
    @FXML
    private void mouseExitedRecoverPassword(){ getRecoverPassword().setStyle("-fx-text-fill-color:  #ffffff"); }
    @FXML
    private void buttonLoginEntered(){ btnLogin.setStyle("-fx-background-radius: 3em; -fx-background-color:  #e0e0e0"); }
    @FXML
    private void setButtonLoginExited(){ btnLogin.setStyle("-fx-background-radius: 3em; -fx-background-color: #ffffff"); }
    @FXML
    private void mousePressedButtonLogin(){ btnLogin.setStyle("-fx-background-radius: 3em; -fx-background-color:  #d6d6d6"); }
    @FXML
    private void mouseReleaseButtonLogin(){ btnLogin.setStyle("-fx-background-radius: 3em; -fx-background-color: #ffffff"); }
    @FXML
    private void donateEntered(){ donate.setStyle("-fx-background-radius: 3em; -fx-background-color:  #0185a2"); }
    @FXML
    private void donateExited(){ donate.setStyle("-fx-background-radius: 3em; -fx-background-color: #ffffff"); }


    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastra");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        imgDonate.setVisible(false);
        getAlerta().setVisible(false);
        getAlertaCpf().setVisible(false);

        isTextFormatterNumber(getLogin());
            addTextLimiter(getLogin(),11);
        isTextFormatterNumber(getSenha());
            addTextLimiter(getSenha(),6);
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
}
