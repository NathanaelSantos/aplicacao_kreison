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
import model.MysqlConnection;
import model.ReturnConnection;
import model.Windows;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
    private TextField especificacaoProduto;
    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
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
        if(especificacaoProduto.getText().isBlank()){
            validate = false;
            especificacaoProduto.setStyle("-fx-border-color: red;");
        }else {
            validate = true;
            especificacaoProduto.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if(validate){
            insertProdutoDB();
        }
    }


    public void insertProdutoDB() throws SQLException, ClassNotFoundException {
        ReturnConnection returnConnection = new ReturnConnection();
        Produto produto = new Produto(nomeProduto.getText(),Float.parseFloat(precoProduto.getText()),Integer.parseInt(quantidadeProduto.getText()),especificacaoProduto.getText());
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = returnConnection.returnConnection().prepareStatement("INSERT INTO db_produto(nome, preco, quantidade, tipo_produto,venda)  VALUES (?,?,?,?,?)");
            preparedStatement.setString(1,produto.getNome());
            preparedStatement.setFloat(2,produto.getPreco());
            preparedStatement.setInt(3,produto.getQuantidade());
            preparedStatement.setString(4,produto.getTipoProduto());
            preparedStatement.setInt(5,produto.getVenda());

            preparedStatement.executeUpdate();

            Alert dialogoInfo = new Alert(Alert.AlertType.INFORMATION);
            dialogoInfo.setHeaderText("Produto cadastrado com sucesso!!!");
            dialogoInfo.showAndWait();

            try {
                System.out.println("Successful insert");
                App.setRoot("produto");
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.returnConnection());
            returnConnection.closePreparedStatement(preparedStatement);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isTextFormatterString(nomeProduto);
            addTextLimiter(nomeProduto,200);
        isTextFormatterNumber(quantidadeProduto);
            addTextLimiter(quantidadeProduto,5);
        isTextFormatterFloat(precoProduto);
        isTextFormatterString(especificacaoProduto);
            addTextLimiter(quantidadeProduto,100);
    }
}