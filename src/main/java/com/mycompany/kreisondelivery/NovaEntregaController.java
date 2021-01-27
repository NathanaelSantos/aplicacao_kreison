/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.ReturnConnection;
import model.Windows;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;

public class NovaEntregaController implements Initializable, Windows {

    @FXML
    private TableView<Produto> table_entegador;
    @FXML
    private TableView<Pessoa> tb_nome_entregador;
    @FXML
    private TableColumn<Pessoa, String> nome_entregador;
    @FXML
    private TableColumn<Produto, String> entregador;
    @FXML
    private TableColumn<Produto, Integer> qtd_restante;
    @FXML
    private TableColumn<Produto, String> precoProduto;
    @FXML
    private TableView<Produto> tb_especificacaoProd;
    @FXML
    private TableColumn<Produto, String> especificacaoProd;
    @FXML
    private TextField endereco_entrega;
    @FXML
    private TextField nome_cliente;
    @FXML
    private TextField bairro;
    @FXML
    private TextField cep;
    @FXML
    private TextField numero;
    @FXML
    private TextField quantidade;
    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
    }

    public void getProdutoDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        ObservableList<Produto> oblist = FXCollections.observableArrayList();

        try {
            preparedStatement = returnConnection.returnConnection().prepareStatement("SELECT nome, preco,quantidade FROM db_produto");
            res = preparedStatement.executeQuery();


            while (res.next()){
                oblist.add(new Produto(res.getString("nome"), res.getFloat("preco"),res.getInt("quantidade")));
            }


            this.entregador.setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.precoProduto.setCellValueFactory(new PropertyValueFactory<>("preco"));
            this.qtd_restante.setCellValueFactory(new PropertyValueFactory<>("quantidade"));


            table_entegador.getItems().setAll(oblist);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.returnConnection());
            returnConnection.closePreparedStatement(preparedStatement);
        }
    }

    public void getNomeEntregador() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

        try {

            String query = "SELECT nome FROM db_usuario WHERE tipo_usuario = 2";
            preparedStatement = returnConnection.returnConnection().prepareStatement(query);

            res = preparedStatement.executeQuery();


            while (res.next()){
                oblist.add(new Pessoa(res.getString("nome")));
            }


            this.nome_entregador.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tb_nome_entregador.getItems().setAll(oblist);


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

        isTextFormatterString(endereco_entrega);
            addTextLimiter(endereco_entrega,200);
        isTextFormatterString(nome_cliente);
            addTextLimiter(nome_cliente,200);
        isTextFormatterString(bairro);
            addTextLimiter(bairro,200);
        isTextFormatterNumber(quantidade);
            addTextLimiter(quantidade,3);
        isTextFormatterNumber(numero);
            addTextLimiter(numero,6);
        isTextFormatterNumber(cep);
            addTextLimiter(cep,8);

        try {
            getProdutoDB();
            getNomeEntregador();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }    
    
}
