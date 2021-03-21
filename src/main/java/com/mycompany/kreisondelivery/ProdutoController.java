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
        if(getNomeProduto().getText().isBlank()){
            getNomeProduto().setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            getNomeProduto().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }
        if(getQuantidadeProduto().getText().isBlank()){
            validate = false;
            getQuantidadeProduto().setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            getQuantidadeProduto().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }
        if(getPrecoProduto().getText().isBlank()){
            validate = false;
            getPrecoProduto().setStyle("-fx-border-color: red;");
        }else{
            validate = true;
            getPrecoProduto().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if(validate){
            insertProdutoDB();
        }
    }


    public void insertProdutoDB() throws SQLException, ClassNotFoundException {
        ReturnConnection connection = new ReturnConnection();
        Produto produto = new Produto(getNomeProduto().getText(),Float.parseFloat(getPrecoProduto().getText()),Integer.parseInt(getQuantidadeProduto().getText()));
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.getConnection().prepareStatement("INSERT INTO db_produto(nome, preco, quantidade,venda)  VALUES (?,?,?,?)");
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
            connection.closeConnection(connection.getConnection(),preparedStatement);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isTextFormatterString(getNomeProduto());
            addTextLimiter(getNomeProduto(),200);
        isTextFormatterNumber(getQuantidadeProduto());
            addTextLimiter(getQuantidadeProduto(),5);
        isTextFormatterFloat(getPrecoProduto());
            addTextLimiter(getPrecoProduto(),5);
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public TextField getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(TextField nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public TextField getQuantidadeProduto() {
        return quantidadeProduto;
    }

    public void setQuantidadeProduto(TextField quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }

    public TextField getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(TextField precoProduto) {
        this.precoProduto = precoProduto;
    }
}
