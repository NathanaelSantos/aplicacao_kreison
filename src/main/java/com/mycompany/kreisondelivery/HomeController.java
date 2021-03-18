package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class HomeController implements Initializable, Windows {

    static boolean onScreenLogin = false;

    @FXML
    private AnchorPane telaLogin;
    @FXML
    private TextField login;
    @FXML
    private PasswordField senha;
    @FXML
    private Label alerta;
    @FXML
    private Label alertaCpf;
    @FXML
    private Button buttonAdmin;
    @FXML
    private TextField codAdmin;


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
    private void adminScreen() throws IOException {
        App.setRoot("admin");
    }

    @FXML
    private void mouseExitedBtnAdmin() throws IOException {
        buttonAdmin.setStyle("-fx-background-color: #00b4d8");
    }

    @FXML
    private void mouseEnteredBtnAdmin() throws IOException {
        buttonAdmin.setStyle("-fx-background-color: #038ca9");
    }

    @FXML
    private void telaLogin() throws IOException {
        if(!onScreenLogin){
            telaLogin.setVisible(true);
            onScreenLogin = true;
        }else {
            telaLogin.setVisible(false);
            onScreenLogin = false;
        }

    }

    public boolean emptyFields() throws SQLException, ClassNotFoundException {
        boolean validate = false;

        if(login.getText().isBlank())

            login.setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            login.setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(senha.getText().isBlank())
            senha.setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            senha.setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(codAdmin.getText().isBlank())
            codAdmin.setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            codAdmin.setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(login.getText().isBlank()|| senha.getText().isBlank() || codAdmin.getText().isBlank() ){
            validate = true;
        }

        return validate;
    }

    @FXML
    private void switchToPrimary() throws Exception {
        ReturnConnection returnConnection = new ReturnConnection();

        if(emptyFields()){
            alerta.setVisible(true);
        }else {

            if (new ValidateCPF().validate(login.getText())) {
                alertaCpf.setVisible(false);

                if (returnConnection.getConnection() != null) {

                    if (new StringUtil().gerarHash(senha.getText()).equals(new LoginConnection().loginConection(login.getText(),"senha")) && new LoginConnection().loginConection(login.getText(),"codAdmin").equals(codAdmin.getText()))
                        App.setRoot("admin");
                    else
                        new AlertDialog().alertDialog("Erro ao fazer login!");
                } else
                    new AlertDialog().alertDialog("Erro de conexao!");
            } else {
                alertaCpf.setVisible(true);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        telaLogin.setVisible(false);
        alerta.setVisible(false);
        alertaCpf.setVisible(false);

        isTextFormatterNumber(login);
        addTextLimiter(login,11);
        isTextFormatterNumber(senha);
        addTextLimiter(senha,6);
        isTextFormatterNumber(codAdmin);
        addTextLimiter(codAdmin,6);
    }
}
