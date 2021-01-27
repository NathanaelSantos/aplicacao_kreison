/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.jar.JarOutputStream;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private void screenLogin() throws IOException {
        App.setRoot("Login");      
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
        if(new ValidateCPF().validate(cpf.getText())){
            ReturnConnection returnConnection = new ReturnConnection();
            Usuario usuario = new Usuario(nome.getText(),Integer.parseInt(senha.getText()),cpf.getText(),dateNascimento(),tipoFuncionario());
            PreparedStatement preparedStatement = null;

            try {
                preparedStatement = returnConnection.returnConnection().prepareStatement("INSERT INTO db_usuario(nome, cpf, data_nasc, senha,tipo_usuario)  VALUES (?,?,?,?,?)");

                preparedStatement.setString(1,usuario.getNome());
                preparedStatement.setString(2,usuario.getCpf());
                preparedStatement.setString(3,usuario.getDataNascimento());
                preparedStatement.setInt(4,usuario.getSenha());
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

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                returnConnection.closeConnection(returnConnection.returnConnection());
                returnConnection.closePreparedStatement(preparedStatement);
            }
        }else{
            Alert dialogoInfo = new Alert(Alert.AlertType.ERROR);
            dialogoInfo.setHeaderText("Cpf inválido!");
            dialogoInfo.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
