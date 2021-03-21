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

    AlertDialog alertDialog = new AlertDialog();

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

        if (nome.getText().isBlank())
            nome.setStyle("-fx-border-color: red;");
        else
            nome.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (senha.getText().isBlank())
            senha.setStyle("-fx-border-color: red;");
        else
            senha.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (confirmaSenha.getText().isBlank())
            confirmaSenha.setStyle("-fx-border-color: red;");
        else
            confirmaSenha.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (cpf.getText().isBlank())
            cpf.setStyle("-fx-border-color: red;");
        else
            cpf.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (dataNascimento.getValue() == null)
            dataNascimento.setStyle("-fx-border-color: red;");
        else
            dataNascimento.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (selection.getSelectedToggle() == null)
            tipoFuncionario.setStyle("-fx-border-color: red;");
        else
            tipoFuncionario.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if (codAdmin.getText().isBlank())
            codAdmin.setStyle("-fx-border-color: red;");
        else
            codAdmin.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        return ((nome.getText().isBlank() || senha.getText().isBlank() || confirmaSenha.getText().isBlank() || cpf.getText().isBlank() || codAdmin.getText().isBlank()) ||
                (selection.getSelectedToggle() == null || dataNascimento.getValue() == null));
    }

    public int tipoFuncionario() {
        int tipoFunc = -1;
        if (entregador.isSelected()) {
            tipoFunc = 2;
        } else if (gerente.isSelected()) {
            tipoFunc = 1;
        }
        return tipoFunc;
    }

    public String dateNascimento() {
        return dataNascimento.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            alerta.setVisible(true);
        } else {
            alerta.setVisible(false);
            if (senha.getText().equals(confirmaSenha.getText())) {
                if (new ValidateCPF().validate(cpf.getText())) {
                    if (new CheckCpfdatabase().checkCpfdatabase(cpf.getText())) {
                        alertDialog.alertDialog("Cpf ja cadastrado!");
                    } else {
                        if (new CadastraController().verificaCodigoAdimin(codAdmin.getText())) {

                            if (tipoFuncionario() == 1) {
                                codAdmin1.setVisible(true);
                            }
                            ReturnConnection returnConnection = new ReturnConnection();
                            Usuario user = new Usuario(nome.getText(), new StringUtil().gerarHash(senha.getText()), cpf.getText(), dateNascimento(), tipoFuncionario());
                            PreparedStatement pstment = null;

                            try {
                                pstment = returnConnection.getConnection().prepareStatement("INSERT INTO db_usuario(nome, cpf, data_nasc, senha,tipo_usuario)  VALUES (?,?,?,?,?)");

                                pstment.setString(1, user.getNome());
                                pstment.setString(2, user.getCpf());
                                pstment.setString(3, user.getData_nasc());
                                pstment.setString(4, user.getPassword());
                                pstment.setInt(5, user.getUserType());

                                pstment.executeUpdate();

                                alertDialog.alertDialog("Usuário cadastrado com sucesso!!!");

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
                            alertDialog.alertDialog("Código inválido!");
                        }
                    }
                } else {
                    alertDialog.alertDialog("Cpf inválido!");
                }
            } else {
                alertDialog.alertDialog("Senhas diferentes!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        alerta.setVisible(false);
        isTextFormatterString(nome);
        addTextLimiter(nome, 200);
        isTextFormatterNumber(cpf);
        addTextLimiter(cpf, 11);
        isTextFormatterNumber(senha);
        addTextLimiter(senha, 6);
        isTextFormatterNumber(confirmaSenha);
        addTextLimiter(confirmaSenha, 6);
        isTextFormatterNumber(codAdmin);
        addTextLimiter(codAdmin, 6);
    }

}
