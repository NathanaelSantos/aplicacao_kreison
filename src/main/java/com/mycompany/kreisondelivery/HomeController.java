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

public class HomeController implements Initializable {

    private static boolean onScreenLogin = false;

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
    private Button buttonConsultaEstoque;
    @FXML
    private Button buttonNovaEntrega;
    @FXML
    private Button btnLogin;


    public static boolean isOnScreenLogin() {
        return onScreenLogin;
    }

    public static void setOnScreenLogin(boolean onScreenLogin) {
        HomeController.onScreenLogin = onScreenLogin;
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
    private void adminScreen() throws IOException {
        App.setRoot("admin");
    }
    @FXML
    private void buttonLoginEntered(){ getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color:  #009cba; -fx-text-fill: #ffffff"); }
    @FXML
    private void setButtonLoginExited(){ getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #ffffff"); }
    @FXML
    private void mousePressedButtonLogin(){ getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #038ca9"); }
    @FXML
    private void mouseReleaseButtonLogin(){ getBtnLogin().setStyle("-fx-background-radius: 3em; -fx-background-color: #ffffff"); }
    @FXML
    private void mouseExitedBtnAdmin() throws IOException { getButtonAdmin().setStyle("-fx-background-color: #00b4d8"); }
    @FXML
    private void mouseEnteredBtnAdmin() throws IOException { getButtonAdmin().setStyle("-fx-background-color: #038ca9"); }
    @FXML
    private void mouseEnteredButtonConsultaEstoque() throws IOException { getButtonConsultaEstoque().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #009abc"); }
    @FXML
    private void mouseExitedButtonConsultaEstoque() throws IOException { getButtonConsultaEstoque().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #00b4d8"); }
    @FXML
    private void mouseEnteredButtonNovaEntrega() throws IOException { getButtonNovaEntrega().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #009abc"); }
    @FXML
    private void mouseExitedButtonNovaEntrega() throws IOException { getButtonNovaEntrega().setStyle("-fx-background-radius: 1.2em; -fx-background-color: #00b4d8"); }




    @FXML
    private void telaLogin() throws IOException {
        if(!isOnScreenLogin()){
            getTelaLogin().setVisible(true);
            setOnScreenLogin(true);
        }else {
            getTelaLogin().setVisible(false);
            setOnScreenLogin(false);
        }

    }

    public boolean emptyFields() throws SQLException, ClassNotFoundException {
        boolean validate = false;

        if(getLogin().getText().isBlank())

            getLogin().setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            getLogin().setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(getSenha().getText().isBlank())
            getSenha().setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            getSenha().setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(getCodAdmin().getText().isBlank())
            getCodAdmin().setStyle(" -fx-border-color: #ff3b3b;\n" +
                    "        -fx-border-radius: 2em;\n" +
                    "        -fx-background-color: #00b4d8;\n" +
                    "        -fx-text-fill: #ffffff;");
        else
            getCodAdmin().setStyle(" -fx-border-color: #ffffff;\n" +
                    "    -fx-border-radius: 2em;\n" +
                    "    -fx-background-color: #00b4d8;\n" +
                    "    -fx-text-fill: #ffffff;");

        if(getLogin().getText().isBlank()|| getSenha().getText().isBlank() || getCodAdmin().getText().isBlank() ){
            validate = true;
        }

        return validate;
    }

    @FXML
    private void switchToPrimary() throws Exception {
        ReturnConnection returnConnection = new ReturnConnection();

        if(emptyFields()){
            getAlerta().setVisible(true);
        }else {

            if (new ValidateCPF().validate(getLogin().getText())) {
                getAlertaCpf().setVisible(false);

                if (returnConnection.getConnection() != null) {

                    if (new StringUtil().gerarHash(getSenha().getText()).equals(new LoginConnection().loginConection(getLogin().getText(),"senha")) && new LoginConnection().loginConection(getLogin().getText(),"codAdmin").equals(getCodAdmin().getText()))
                        App.setRoot("admin");
                    else
                        new AlertDialog().alertDialog("Erro ao fazer login!");
                } else
                    new AlertDialog().alertDialog("Erro de conexao!");
            } else {
                getAlertaCpf().setVisible(true);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getTelaLogin().setVisible(false);
        getAlerta().setVisible(false);
        getAlertaCpf().setVisible(false);

        isTextFormatterNumber(getLogin());
        addTextLimiter(getLogin(),11);
        isTextFormatterNumber(getSenha());
        addTextLimiter(getSenha(),6);
        isTextFormatterNumber(getCodAdmin());
        addTextLimiter(getCodAdmin(),6);
    }

    public AnchorPane getTelaLogin() {
        return telaLogin;
    }

    public void setTelaLogin(AnchorPane telaLogin) {
        this.telaLogin = telaLogin;
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

    public Button getButtonAdmin() {
        return buttonAdmin;
    }

    public void setButtonAdmin(Button buttonAdmin) {
        this.buttonAdmin = buttonAdmin;
    }

    public TextField getCodAdmin() {
        return codAdmin;
    }

    public void setCodAdmin(TextField codAdmin) {
        this.codAdmin = codAdmin;
    }

    public Button getButtonConsultaEstoque() {
        return buttonConsultaEstoque;
    }

    public void setButtonConsultaEstoque(Button buttonConsultaEstoque) {
        this.buttonConsultaEstoque = buttonConsultaEstoque;
    }

    public Button getButtonNovaEntrega() {
        return buttonNovaEntrega;
    }

    public void setButtonNovaEntrega(Button buttonNovaEntrega) {
        this.buttonNovaEntrega = buttonNovaEntrega;
    }

    public Button getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(Button btnLogin) {
        this.btnLogin = btnLogin;
    }
}
