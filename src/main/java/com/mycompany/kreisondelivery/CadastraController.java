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
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;


public class CadastraController implements Initializable, Windows {

    private AlertDialog alertDialog = new AlertDialog();

    @FXML
    private TextField nome;

    @FXML
    private PasswordField senha;

    @FXML
    private PasswordField confirmaSenha;

    @FXML
    private TextField cpf;

    @FXML
    private DatePicker dataNascimento;

    @FXML
    private MenuButton tipoFuncionario;

    @FXML
    private RadioMenuItem entregador;

    @FXML
    private ToggleGroup selection;

    @FXML
    private RadioMenuItem gerente;

    @FXML
    private TextField codAdmin;
    @FXML
    private TextField codAdmin1;

    @FXML
    private Label alerta;

    @FXML
    private void screenLogin() throws IOException {
        App.setRoot("Login");
    }

    public boolean composVazios() {

        if (getNome().getText().isBlank())
            getNome().setStyle("-fx-border-color: red;");
        else
            getNome().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (getSenha().getText().isBlank())
            getSenha().setStyle("-fx-border-color: red;");
        else
            getSenha().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

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

        if (getCodAdmin().getText().isBlank())
            getCodAdmin().setStyle("-fx-border-color: red;");
        else
            getCodAdmin().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        return ((getNome().getText().isBlank() || getSenha().getText().isBlank() || getConfirmaSenha().getText().isBlank() || getCpf().getText().isBlank() || getCodAdmin().getText().isBlank()) ||
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

    public String dateNascimento() {
        return getDataNascimento().getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public boolean verificaCodigoAdimin(String codAdmin) {

        int valueCodAdmin = 0;

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {

            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT codAdmin FROM db_usuario WHERE tipo_usuario = 1");
            res = preparedStatement.executeQuery();

            while (res.next())
                valueCodAdmin = res.getInt("codAdmin");

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
                    if (new CheckCpfdatabase().checkCpfdatabase(getCpf().getText())) {
                        getAlertDialog().alertDialog("Cpf ja cadastrado!");
                    } else {
                        if (new CadastraController().verificaCodigoAdimin(getCodAdmin().getText())) {

                            if (tipoFuncionario() == 1) {
                                getCodAdmin1().setVisible(true);
                            }
                            ReturnConnection returnConnection = new ReturnConnection();
                            Usuario user = new Usuario(getNome().getText(), new StringUtil().gerarHash(getSenha().getText()), getCpf().getText(), dateNascimento(), tipoFuncionario());
                            PreparedStatement pstment = null;

                            try {
                                pstment = returnConnection.getConnection().prepareStatement("INSERT INTO db_usuario(nome, cpf, data_nasc, senha,tipo_usuario)  VALUES (?,?,?,?,?)");

                                pstment.setString(1, user.getNome());
                                pstment.setString(2, user.getCpf());
                                pstment.setString(3, user.getData_nasc());
                                pstment.setString(4, user.getPassword());
                                pstment.setInt(5, user.getUserType());

                                pstment.executeUpdate();

                                getAlertDialog().alertDialog("Usuário cadastrado com sucesso!!!");

                                try {
                                    App.setRoot("cadastra");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            } finally {
                                returnConnection.closeConnection(returnConnection.getConnection(), pstment);
                            }
                        } else {
                            getAlertDialog().alertDialog("Código inválido!");
                        }
                    }
                } else {
                    getAlertDialog().alertDialog("Cpf inválido!");
                }
            } else {
                getAlertDialog().alertDialog("Senhas diferentes!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getAlerta().setVisible(false);
        isTextFormatterString(getNome());
        addTextLimiter(getNome(), 200);
        isTextFormatterNumber(getCpf());
        addTextLimiter(getCpf(), 11);
        isTextFormatterNumber(getSenha());
        addTextLimiter(getSenha(), 6);
        isTextFormatterNumber(getConfirmaSenha());
        addTextLimiter(getConfirmaSenha(), 6);
        isTextFormatterNumber(getCodAdmin());
        addTextLimiter(getCodAdmin(), 6);
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

    public TextField getCodAdmin() {
        return codAdmin;
    }

    public void setCodAdmin(TextField codAdmin) {
        this.codAdmin = codAdmin;
    }

    public TextField getCodAdmin1() {
        return codAdmin1;
    }

    public void setCodAdmin1(TextField codAdmin1) {
        this.codAdmin1 = codAdmin1;
    }

    public Label getAlerta() {
        return alerta;
    }

    public void setAlerta(Label alerta) {
        this.alerta = alerta;
    }
}
