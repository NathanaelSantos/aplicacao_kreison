/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;

public class CadastraController implements Initializable,Windows {
    
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
    private Label alerta;

    @FXML
    private void screenLogin() throws IOException {
        App.setRoot("Login");      
    }

    public boolean composVazios() throws SQLException, ClassNotFoundException {
        boolean validate = false;

        if(nome.getText().isBlank())
            nome.setStyle("-fx-border-color: red;");
        else
            nome.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(senha.getText().isBlank())
            senha.setStyle("-fx-border-color: red;");
        else
            senha.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(confirmaSenha.getText().isBlank())
            confirmaSenha.setStyle("-fx-border-color: red;");
        else
            confirmaSenha.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(cpf.getText().isBlank())
            cpf.setStyle("-fx-border-color: red;");
        else
            cpf.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(dataNascimento.getValue() == null)
            dataNascimento.setStyle("-fx-border-color: red;");
        else
            dataNascimento.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(selection.getSelectedToggle() == null)
            tipoFuncionario.setStyle("-fx-border-color: red;");
        else
            tipoFuncionario.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(nome.getText().isBlank()||
                senha.getText().isBlank() ||
                confirmaSenha.getText().isBlank() ||
                cpf.getText().isBlank()){validate = true;}
        if(selection.getSelectedToggle() == null || dataNascimento.getValue() == null)
            validate = true;

        return validate;
    }
    
    public int tipoFuncionario(){
        int tipoFunc = -1;
        if(entregador.isSelected()){
            tipoFunc = 2;
        }else if(gerente.isSelected()){
            tipoFunc = 1;
        }
        return tipoFunc;
    }

    public String dateNascimento(){
        return dataNascimento.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void insertUsuarioDB() throws SQLException, ClassNotFoundException {
        if(composVazios()){
            alerta.setVisible(true);
        }else{
            alerta.setVisible(false);
            if(senha.getText().equals(confirmaSenha.getText())){
                if(new ValidateCPF().validate(cpf.getText())){
                    if(new VerificaCpfDB().verificaCpfDB(cpf.getText())){
                        Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                        dialogoInfo.setHeaderText("Cpf ja cadastrado!");
                        dialogoInfo.show();
                    }else{
                        ReturnConnection returnConnection = new ReturnConnection();
                        Usuario usuario = new Usuario(nome.getText(),new StringUtil().encodeBase64String(senha.getText()),cpf.getText(),dateNascimento(),tipoFuncionario());
                        PreparedStatement preparedStatement = null;

                        try {
                            preparedStatement = returnConnection.getConnection().prepareStatement("INSERT INTO db_usuario(nome, cpf, data_nasc, senha,tipo_usuario)  VALUES (?,?,?,?,?)");

                            preparedStatement.setString(1,usuario.getNome());
                            preparedStatement.setString(2,usuario.getCpf());
                            preparedStatement.setString(3,usuario.getDataNascimento());
                            preparedStatement.setString(4,usuario.getSenha());
                            preparedStatement.setInt(5,usuario.getTipoUsuario());

                            preparedStatement.executeUpdate();

                            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
                            dialogoInfo.setHeaderText("Usuário cadastrado com sucesso!!!");
                            dialogoInfo.showAndWait();

                            try {
                                App.setRoot("cadastra");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        } finally {
                            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
                        }
                    }
                }else{
                    Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                    dialogoInfo.setHeaderText("Cpf inválido!");
                    dialogoInfo.show();
                }
            }else{
                Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
                dialogoInfo.setHeaderText("Senhas diferentes!");
                dialogoInfo.show();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alerta.setVisible(false);
        isTextFormatterString(nome);
        addTextLimiter(nome,200);
        isTextFormatterNumber(cpf);
        addTextLimiter(cpf,11);
        isTextFormatterNumber(senha);
        addTextLimiter(senha,6);
        isTextFormatterNumber(confirmaSenha);
        addTextLimiter(confirmaSenha,6);
    }

}
