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
    AlertDialog alertDialog = new AlertDialog();
    Random random = new Random();
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);

    @FXML
    private StackPane stackPaneLogin;
    @FXML
    private StackPane stackPaneCode;
    @FXML
    private AnchorPane paneLogin;
    @FXML
    private TextField cpf;
    @FXML
    private TextField email;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confPassword;
    @FXML
    private TextField codeEmail;
    @FXML
    private Label alerta;
    @FXML
    private Label alertaCpf;
    @FXML
    private Button btnCadastrarNovaSenha;
    @FXML
    private AnchorPane paneCode;
    @FXML
    private Label alertaCode;


    @FXML
    private void screenLogin() throws IOException {
        App.setRoot("Login");
    }
    @FXML
    private void btnSendCodeEmail() throws UnirestException {
        if(composVazios()){
            composVazios();
        }else{
            if(new ValidateCPF().validate(cpf.getText())){
                getAlertaCpf().setVisible(false);
                if(newPassword.getText().equals(confPassword.getText())){
                    String value = Integer.toString(random.nextInt(999999));
                    while (value.length() < 6)
                        value = Integer.toString(random.nextInt(999999));

                    setRandomCode(Integer.parseInt(value));

                    if(new EmailValidation().emailValidation(getEmail().getText())){
                        new EmailClient(getEmail().getText(),"nathanaelsantos15@gmail.com","Recupera senha", getRandomCode()).sendSimpleMessage();

                        getStackPaneLogin().setVisible(false);
                        getStackPaneCode().setVisible(true);
                    }else{
                        alertDialog.alertDialog("Email inválido!");
                    }
                }else{
                    alertDialog.alertDialog("Senhas diferentes!");
                }
            }else{
                getAlertaCpf().setVisible(true);
            }
        }
    }

    @FXML
    private void cadastraNovaSenha() throws Exception {

        if(Integer.parseInt(codeEmail.getText()) == getRandomCode()){
            getAlertaCode().setVisible(false);
            ReturnConnection returnConnection = new ReturnConnection();
            PreparedStatement pstment = null;
            try {
                pstment = returnConnection.getConnection().prepareStatement("UPDATE db_usuario SET senha = ? WHERE cpf = ?");
                pstment.setString(1,new StringUtil().gerarHash(newPassword.getText()));
                pstment.setString(2,cpf.getText());
                pstment.executeUpdate();

                alertDialog.alertDialog("Senha alterada com sucesso!");
                screenLogin();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                returnConnection.closeConnection(returnConnection.getConnection(),pstment);
            }
        }else{
            getAlertaCode().setVisible(true);
        }
    }

    public boolean composVazios() {

        if(cpf.getText().isBlank())
            cpf.setStyle("-fx-border-color: red;");
        else
            cpf.setStyle("-fx-border-color: rgba(27,72,171,0.4)");

        if(email.getText().isBlank())
            email.setStyle("-fx-border-color: red;");
        else
            email.setStyle("-fx-border-color: rgba(27,72,171,0.4)");

        if(newPassword.getText().isBlank())
            newPassword.setStyle("-fx-border-color: red;");
        else
            newPassword.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(confPassword.getText().isBlank())
            confPassword.setStyle("-fx-border-color: red;");
        else
            confPassword.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        return (cpf.getText().isBlank()|| email.getText().isBlank() || newPassword.getText().isBlank() || confPassword.getText().isBlank());
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
}
