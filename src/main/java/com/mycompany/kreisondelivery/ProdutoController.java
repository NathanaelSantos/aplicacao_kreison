/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.ReturnConnection;
import model.Windows;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.*;



public class ProdutoController implements Initializable, Windows{

    private boolean check = false;

    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField quantidadeProduto;
    @FXML
    private TextField precoProduto;
    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
    }
    @FXML
    private void adminScreen() throws IOException {
        App.setRoot("admin");
    }

    public void composVazios() throws SQLException, ClassNotFoundException {
        boolean validate = false;
        if(nomeProduto.getText().isBlank()){
            nomeProduto.setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            nomeProduto.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }
        if(quantidadeProduto.getText().isBlank()){
            validate = false;
            quantidadeProduto.setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            quantidadeProduto.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }
        if(precoProduto.getText().isBlank()){
            validate = false;
            precoProduto.setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            precoProduto.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if(validate){
            insertProdutoDB();
        }
    }


    public void insertProdutoDB() throws SQLException, ClassNotFoundException {
        ReturnConnection returnConnection = new ReturnConnection();
        Produto produto = new Produto(nomeProduto.getText(),Float.parseFloat(precoProduto.getText()),Integer.parseInt(quantidadeProduto.getText()));
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = returnConnection.getConnection().prepareStatement("INSERT INTO db_produto(nome, preco, quantidade,venda)  VALUES (?,?,?,?)");
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setFloat(2,produto.getPreco());
            preparedStatement.setInt(3,produto.getQuantidade());
            preparedStatement.setInt(4,produto.getVenda());

            preparedStatement.executeUpdate();

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setHeaderText("Produto cadastrado com sucesso!!!");
            dialogoInfo.showAndWait();

            try {
                App.setRoot("produto");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isTextFormatterString(nomeProduto);
            addTextLimiter(nomeProduto,200);
        isTextFormatterNumber(quantidadeProduto);
            addTextLimiter(quantidadeProduto,5);
        isTextFormatterFloat(precoProduto);
            addTextLimiter(precoProduto,5);
    }
}
