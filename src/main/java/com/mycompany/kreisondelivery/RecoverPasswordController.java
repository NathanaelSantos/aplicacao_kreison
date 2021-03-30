package com.mycompany.kreisondelivery;

import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;

public class RecoverPasswordController implements Initializable {

    private static int randomCode;
    private AlertDialog alertDialog = new AlertDialog();
    private Random random = new Random();
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(getEmailPattern(), Pattern.CASE_INSENSITIVE);

    @FXML private StackPane stackPaneLogin;
    @FXML private StackPane stackPaneCode;
    @FXML private AnchorPane paneLogin;
    @FXML private AnchorPane paneCode;

    @FXML private TextField cpf;
    @FXML private TextField email;
    @FXML private TextField codeEmail;

    @FXML private PasswordField newPassword;
    @FXML private PasswordField confPassword;

    @FXML private Label alerta;
    @FXML private Label alertaCpf;
    @FXML private Label alertaCode;

    @FXML private Button btnCadastrarNovaSenha;
    @FXML private Button btnCadastrar;


    @FXML private void buttonBtnCadastrarEntered(){ getBtnCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae"); }
    @FXML private void buttonBtnCadastrarExited(){ getBtnCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #00b4d8"); }
    @FXML private void mousePressedBtnCadastrar(){ getBtnCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae"); }
    @FXML private void mouseReleaseBtnCadastrar(){ getBtnCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8"); }

    @FXML private void buttonCadastrarNovaSenhaEntered(){ getBtnCadastrarNovaSenha().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae"); }
    @FXML private void buttonCadastrarNovaSenhaExited(){ getBtnCadastrarNovaSenha().setStyle("-fx-background-radius: 3em; -fx-background-color:  #00b4d8"); }
    @FXML private void mousePressedCadastrarNovaSenha(){ getBtnCadastrarNovaSenha().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae"); }
    @FXML private void mouseReleaseCadastrarNovaSenha(){ getBtnCadastrarNovaSenha().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8"); }

    @FXML private void screenLogin() throws IOException, ClassNotFoundException { App.setRoot("Login"); }

    @FXML
    private void btnSendCodeEmail() throws UnirestException {
        if(composVazios()){
            composVazios();
        }else{
            if(new ValidateCPF().validate(getCpf().getText())){
                getAlertaCpf().setVisible(false);
                if(getNewPassword().getText().equals(getConfPassword().getText())){
                    String value = Integer.toString(getRandom().nextInt(999999));
                    while (value.length() < 6)
                        value = Integer.toString(getRandom().nextInt(999999));

                    setRandomCode(Integer.parseInt(value));

                    if(new EmailValidation().emailValidation(getEmail().getText())){
                        new EmailClient(getEmail().getText(),"nathanaelsantos15@gmail.com","Recuperação de senha", getRandomCode()).sendSimpleMessage();
                        getStackPaneLogin().setVisible(false);
                        getStackPaneCode().setVisible(true);
                    }else{
                        getAlertDialog().alertDialog("Email inválido!");
                    }
                }else{
                    getAlertDialog().alertDialog("Senhas diferentes!");
                }
            }else{
                getAlertaCpf().setVisible(true);
            }
        }
    }

    @FXML
    private void cadastraNovaSenha() throws Exception {

        if(getCodeEmail().getText().isBlank()){
            getCodeEmail().setStyle("-fx-border-color: red;");
        }else{
            getCodeEmail().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);");
            if(Integer.parseInt(getCodeEmail().getText()) == getRandomCode()){
                getAlertaCode().setVisible(false);
                ReturnConnection connection = new ReturnConnection();
                PreparedStatement preparedStatement = null;
                try {
                    preparedStatement = connection.getConnection().prepareStatement("UPDATE db_usuario SET senha = ? WHERE cpf = ?");
                    preparedStatement.setString(1,new StringUtil().gerarHash(getNewPassword().getText()));
                    preparedStatement.setString(2, getCpf().getText());
                    preparedStatement.executeUpdate();

                    getAlertDialog().alertDialog("Senha alterada com sucesso!");
                    screenLogin();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } finally {
                    connection.closeConnection(connection.getConnection(),preparedStatement);
                }
            }else{
                getAlertaCode().setVisible(true);
            }
        }
    }

    public static String getEmailPattern() {
        return EMAIL_PATTERN;
    }

    public static Pattern getPattern() {
        return pattern;
    }

    public boolean composVazios() {

        if(getCpf().getText().isBlank())
            getCpf().setStyle("-fx-border-color: red;");
        else
            getCpf().setStyle("-fx-border-color: rgba(27,72,171,0.4)");

        if(getEmail().getText().isBlank())
            getEmail().setStyle("-fx-border-color: red;");
        else
            getEmail().setStyle("-fx-border-color: rgba(27,72,171,0.4)");

        if(getNewPassword().getText().isBlank())
            getNewPassword().setStyle("-fx-border-color: red;");
        else
            getNewPassword().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(getConfPassword().getText().isBlank())
            getConfPassword().setStyle("-fx-border-color: red;");
        else
            getConfPassword().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        return (getCpf().getText().isBlank()|| getEmail().getText().isBlank() || getNewPassword().getText().isBlank() || getConfPassword().getText().isBlank());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getAlertaCode().setVisible(false);
        getAlertaCpf().setVisible(false);
        getStackPaneCode().setVisible(false);

        isTextFormatterNumber(getCpf());
            addTextLimiter(getCpf(),11);
        isTextFormatterNumber(getNewPassword());
            addTextLimiter(getNewPassword(),6);
        isTextFormatterNumber(getConfPassword());
            addTextLimiter(getConfPassword(),6);
        isTextFormatterNumber(getCodeEmail());
            addTextLimiter(getCodeEmail(),6);
    }

    public int getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(int randomCode) {
        this.randomCode = randomCode;
    }

    public StackPane getStackPaneLogin() {
        return stackPaneLogin;
    }

    public void setStackPaneLogin(StackPane stackPaneLogin) {
        this.stackPaneLogin = stackPaneLogin;
    }

    public StackPane getStackPaneCode() {
        return stackPaneCode;
    }

    public void setStackPaneCode(StackPane stackPaneCode) {
        this.stackPaneCode = stackPaneCode;
    }

    public AnchorPane getPaneLogin() {
        return paneLogin;
    }

    public void setPaneLogin(AnchorPane paneLogin) {
        this.paneLogin = paneLogin;
    }

    public TextField getCpf() {
        return cpf;
    }

    public void setCpf(TextField cpf) {
        this.cpf = cpf;
    }

    public TextField getEmail() {
        return email;
    }

    public void setEmail(TextField email) {
        this.email = email;
    }

    public PasswordField getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(PasswordField newPassword) {
        this.newPassword = newPassword;
    }

    public PasswordField getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(PasswordField confPassword) {
        this.confPassword = confPassword;
    }

    public TextField getCodeEmail() {
        return codeEmail;
    }

    public void setCodeEmail(TextField codeEmail) {
        this.codeEmail = codeEmail;
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

    public AnchorPane getPaneCode() {
        return paneCode;
    }

    public void setPaneCode(AnchorPane paneCode) {
        this.paneCode = paneCode;
    }

    public Button getBtnCadastrarNovaSenha() {
        return btnCadastrarNovaSenha;
    }

    public void setBtnCadastrarNovaSenha(Button btnCadastrarNovaSenha) {
        this.btnCadastrarNovaSenha = btnCadastrarNovaSenha;
    }

    public Label getAlertaCode() {
        return alertaCode;
    }

    public void setAlertaCode(Label alertaCode) {
        this.alertaCode = alertaCode;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Button getBtnCadastrar() {
        return btnCadastrar;
    }

    public void setBtnCadastrar(Button btnCadastrar) {
        this.btnCadastrar = btnCadastrar;
    }
}
