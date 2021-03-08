package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.*;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class HomeController implements Initializable, Windows {

    App app = new App();
    private static boolean screenMax = false;

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
    private Button btnLogin;

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
    private void telaLogin() throws IOException {
        telaLogin.setVisible(true);
    }

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
    }
}
