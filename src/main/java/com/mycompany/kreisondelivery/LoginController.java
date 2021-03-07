/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class LoginController implements Initializable, Windows{
    ReturnConnection returnConnection = new ReturnConnection();
    private Timer time;


    @FXML
    private AnchorPane home1;

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
    private void switchToPrimary() throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        ReturnConnection returnConnection = new ReturnConnection();

        if(login.getText().isBlank() || senha.getText().isBlank()){
            alerta.setVisible(true);
        }else {

            if (new ValidateCPF().validate(login.getText())) {
                alertaCpf.setVisible(false);

                if (returnConnection.getConnection() != null) {

                    if (new StringUtil().encodeBase64String(senha.getText()).equals(new LoginConnection().loginConection(login.getText())))
                        App.setRoot("home");
                    else
                        new AlertDialog().alertDialog("Erro ao fazer login!");
                } else
                    new AlertDialog().alertDialog("Erro de conexao!");
            } else {
                alertaCpf.setVisible(true);
            }
        }
    }
    
    @FXML
    private void cadastrar() throws IOException {
        App.setRoot("cadastra");
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        alerta.setVisible(false);
        alertaCpf.setVisible(false);

        isTextFormatterNumber(login);
            addTextLimiter(login,11);
        isTextFormatterNumber(senha);
            addTextLimiter(senha,6);
    }

}
