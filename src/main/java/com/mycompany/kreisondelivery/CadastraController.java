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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.sql.Types.NULL;
import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;


public class CadastraController implements Initializable {

    private AlertDialog alertDialog = new AlertDialog();

    @FXML private Button buttonCadastrar;
    @FXML private TextField nome;
    @FXML private PasswordField senha;
    @FXML private PasswordField confirmaSenha;
    @FXML private TextField cpf;
    @FXML private DatePicker dataNascimento;
    @FXML private MenuButton tipoFuncionario;
    @FXML private RadioMenuItem entregador;
    @FXML private ToggleGroup selection;
    @FXML private RadioMenuItem gerente;
    @FXML private PasswordField codeAdmin;
    @FXML private TextField txtCodeAdmin;
    @FXML private Label alerta;

    @FXML private void buttonCadastrarEntered(){ getButtonCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #019AB8"); }
    @FXML private void buttonCadastrarExited(){ getButtonCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #00b4d8"); }
    @FXML private void mousePressedButtonCadastrar(){ getButtonCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color:  #019AB8"); }
    @FXML private void mouseReleaseButtonCadastrar(){ getButtonCadastrar().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8"); }
    @FXML private void mouseReleaseSetVisibleCode(){
        getTxtCodeAdmin().setVisible(false);
    }

    @FXML private void screenLogin() throws IOException { App.setRoot("Login"); }

    @FXML private void mousePressedSetVisibleCode(){
        getTxtCodeAdmin().setVisible(true);
        getTxtCodeAdmin().setText(getCodeAdmin().getText());
    }

    public boolean composVazios() {

        if (getNome().getText().isBlank())
            getNome().setStyle("-fx-border-color: red;");
        else
            getNome().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getSenha().getText().isBlank())
            getSenha().setStyle("-fx-border-color: red;");
        else
            getSenha().setStyle("-fx-border-color: rgba(27,72,171,0.4)");

        if (getConfirmaSenha().getText().isBlank())
            getConfirmaSenha().setStyle("-fx-border-color: red;");
        else
            getConfirmaSenha().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getCpf().getText().isBlank())
            getCpf().setStyle("-fx-border-color: red;");
        else
            getCpf().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getDataNascimento().getValue() == null)
            getDataNascimento().setStyle("-fx-border-color: red;");
        else
            getDataNascimento().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getSelection().getSelectedToggle() == null)
            getTipoFuncionario().setStyle("-fx-border-color: red;");
        else
            getTipoFuncionario().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getCodeAdmin().getText().isBlank())
            getCodeAdmin().setStyle("-fx-border-color: red;");
        else
            getCodeAdmin().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        return ((getNome().getText().isBlank() || getSenha().getText().isBlank() || getConfirmaSenha().getText().isBlank() || getCpf().getText().isBlank() || getCodeAdmin().getText().isBlank()) ||
                (getSelection().getSelectedToggle() == null || getDataNascimento().getValue() == null));
    }

    public int tipoFuncionario() {
        int tipoFunc = -1;
        if (getEntregador().isSelected()) {
            tipoFunc = 2;
        } else if (getGerente().isSelected()) {
            tipoFunc = 1;
        }
        return tipoFunc;
    }

    public String dateNascimento() { return getDataNascimento().getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); }

    public boolean verificaCodigoAdimin(String codAdmin) throws SQLException {

        int valueCodAdmin = 0;

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {

            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT codAdmin FROM db_usuario WHERE tipo_usuario = 1");
            res = preparedStatement.executeQuery();

            while (res.next()){
                valueCodAdmin = res.getInt("codAdmin");
                break;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            returnConnection.closeConnection(returnConnection.getConnection(), preparedStatement);
        }

        return (Integer.parseInt(codAdmin) == valueCodAdmin) ? true : false;
    }

    public void insertUsuarioDB() throws Exception {

        if (composVazios()) {
            getAlerta().setVisible(true);
        } else {
            getAlerta().setVisible(false);
            if (getSenha().getText().equals(getConfirmaSenha().getText())) {
                if (new ValidateCPF().validate(getCpf().getText())) {

                    ReturnConnection returnConnection = new ReturnConnection();
                    Usuario user = new Usuario(getNome().getText(), new StringUtil().gerarHash(getSenha().getText()), getCpf().getText(), dateNascimento(), tipoFuncionario());
                    PreparedStatement pstment = null;

                    if(new CadastraController().verificaCodigoAdimin(getCodeAdmin().getText())){
                        if (new CheckCpfdatabase().checkCpfdatabase(getCpf().getText())) {
                            getAlertDialog().alertDialog("Cpf ja cadastrado!");
                        } else {
                            try {
                                pstment = returnConnection.getConnection().prepareStatement("INSERT INTO db_usuario(nome, cpf, data_nasc, senha,tipo_usuario,codAdmin)  VALUES (?,?,?,?,?,?)");

                                pstment.setString(1, user.getNome());
                                pstment.setString(2, user.getCpf());
                                pstment.setString(3, user.getData_nasc());
                                pstment.setString(4, user.getPassword());
                                pstment.setInt(5, user.getUserType());

                                if(tipoFuncionario() == 1){
                                    pstment.setInt(6, Integer.parseInt(getCodeAdmin().getText()));
                                }else{
                                    pstment.setNull(6, NULL);
                                }

                                pstment.executeUpdate();

                                if (tipoFuncionario() == 1){
                                    getAlertDialog().alertDialog("Gerente cadastrado com sucesso!!!");
                                } else if (tipoFuncionario() == 2){
                                    getAlertDialog().alertDialog("Entregador cadastrado com sucesso!!!");
                                }

                                try { App.setRoot("cadastra"); } catch (IOException e) { e.printStackTrace(); }

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            } finally {
                                returnConnection.closeConnection(returnConnection.getConnection(), pstment);
                            }
                        }
                    }else{
                        getAlertDialog().alertDialog("Código inválido!");
                    }
                } else {
                    getAlertDialog().alertDialog("Cpf inválido!");
                }
            } else{
                getAlertDialog().alertDialog("Senhas diferentes!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getTxtCodeAdmin().setVisible(false);
        getAlerta().setVisible(false);
        isTextFormatterString(getNome());
        addTextLimiter(getNome(), 200);
        isTextFormatterNumber(getCpf());
        addTextLimiter(getCpf(), 11);
        isTextFormatterNumber(getSenha());
        addTextLimiter(getSenha(), 6);
        isTextFormatterNumber(getConfirmaSenha());
        addTextLimiter(getConfirmaSenha(), 6);
        isTextFormatterNumber(getCodeAdmin());
        addTextLimiter(getCodeAdmin(), 6);
        isTextFormatterNumber(getTxtCodeAdmin());
        addTextLimiter(getTxtCodeAdmin(), 6);

    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public TextField getNome() {
        return nome;
    }

    public void setNome(TextField nome) {
        this.nome = nome;
    }

    public PasswordField getSenha() {
        return senha;
    }

    public void setSenha(PasswordField senha) {
        this.senha = senha;
    }

    public PasswordField getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(PasswordField confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public TextField getCpf() {
        return cpf;
    }

    public void setCpf(TextField cpf) {
        this.cpf = cpf;
    }

    public DatePicker getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(DatePicker dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public MenuButton getTipoFuncionario() {
        return tipoFuncionario;
    }

    public void setTipoFuncionario(MenuButton tipoFuncionario) {
        this.tipoFuncionario = tipoFuncionario;
    }

    public RadioMenuItem getEntregador() {
        return entregador;
    }

    public void setEntregador(RadioMenuItem entregador) {
        this.entregador = entregador;
    }

    public ToggleGroup getSelection() {
        return selection;
    }

    public void setSelection(ToggleGroup selection) {
        this.selection = selection;
    }

    public RadioMenuItem getGerente() {
        return gerente;
    }

    public void setGerente(RadioMenuItem gerente) {
        this.gerente = gerente;
    }

    public Label getAlerta() {
        return alerta;
    }

    public void setAlerta(Label alerta) {
        this.alerta = alerta;
    }

    public Button getButtonCadastrar() {
        return buttonCadastrar;
    }

    public void setButtonCadastrar(Button buttonCadastrar) {
        this.buttonCadastrar = buttonCadastrar;
    }

    public PasswordField getCodeAdmin() {
        return codeAdmin;
    }

    public void setCodeAdmin(PasswordField codeAdmin) {
        this.codeAdmin = codeAdmin;
    }

    public TextField getTxtCodeAdmin() {
        return txtCodeAdmin;
    }

    public void setTxtCodeAdmin(TextField txtCodeAdmin) {
        this.txtCodeAdmin = txtCodeAdmin;
    }

}
