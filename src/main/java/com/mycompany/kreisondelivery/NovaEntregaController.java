/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AlertDialog;
import model.ReturnConnection;
import model.Windows;

import javax.xml.transform.Result;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;

public class NovaEntregaController implements Initializable, Windows {

    Produto produtoId = new Produto();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    AlertDialog alertDialog = new AlertDialog();

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
    private TableColumn<Produto, Integer> idProduto;
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

    public boolean emptyTable(){
        boolean validate = false;

        if(table_entegador.getSelectionModel().isEmpty()){
            table_entegador.setStyle("-fx-border-color: red;");
        }else{
            table_entegador.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if(tb_nome_entregador.getSelectionModel().isEmpty()){
            tb_nome_entregador.setStyle("-fx-border-color: red;");
        }else{
            tb_nome_entregador.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if(table_entegador.getSelectionModel().isEmpty() || tb_nome_entregador.getSelectionModel().isEmpty()){
            validate = true;
        }
        return validate;
    }

    public boolean composVazios() throws SQLException, ClassNotFoundException {
        boolean validate = false;

        if(endereco_entrega.getText().isBlank())
            endereco_entrega.setStyle("-fx-border-color: red;");
        else
            endereco_entrega.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(nome_cliente.getText().isBlank())
            nome_cliente.setStyle("-fx-border-color: red;");
        else
            nome_cliente.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(bairro.getText().isBlank())
            bairro.setStyle("-fx-border-color: red;");
        else
            bairro.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(cep.getText().isBlank())
            cep.setStyle("-fx-border-color: red;");
        else
            cep.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(numero.getText().isBlank())
            numero.setStyle("-fx-border-color: red;");
        else
            numero.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(quantidade.getText().isBlank())
            quantidade.setStyle("-fx-border-color: red;");
        else
            quantidade.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");

        if(endereco_entrega.getText().isBlank()|| nome_cliente.getText().isBlank() || bairro.getText().isBlank() || cep.getText().isBlank() || numero.getText().isBlank() || quantidade.getText().isBlank()){
            validate = true;
        }

        return validate;
    }

    private String getDateTime() {
        Date date = new Date(System.currentTimeMillis());
        return (dateFormat.format(date));
    }


    public void cadastraEntrega() throws SQLException, ClassNotFoundException {

        if(emptyTable()){
            alertDialog.alertDialog("É precisso selecionar um campo da tabela!!!");
        }else if(composVazios()){
            alertDialog.alertDialog("Campos precisam estar preenchidos!");
        }else {
            if(Integer.parseInt(quantidade.getText()) == 0){
                alertDialog.alertDialog("Digite um valor válido para a quantidade!");
            }else {

                if (Integer.parseInt(quantidade.getText()) <= table_entegador.getSelectionModel().getSelectedItem().getQuantidade()) {

                    ReturnConnection returnConnection = new ReturnConnection();
                    PreparedStatement preparedStatement = null;


                    ObservableList<Produto> oblist = FXCollections.observableArrayList();

                    try {
                        preparedStatement = returnConnection.getConnection().prepareStatement("INSERT INTO db_pedido(especiProduto,nomeEntregador,enderEntrega,nomeCliente,bairro,cep,numero,quantidade, data_entrega) VALUES (?,?,?,?,?,?,?,?,?)");
                        preparedStatement.setString(1, table_entegador.getSelectionModel().getSelectedItem().getNome());
                        preparedStatement.setString(2, tb_nome_entregador.getSelectionModel().getSelectedItem().getNome());
                        preparedStatement.setString(3, endereco_entrega.getText());
                        preparedStatement.setString(4, nome_cliente.getText());
                        preparedStatement.setString(5, bairro.getText());
                        preparedStatement.setInt(6, Integer.parseInt(cep.getText()));
                        preparedStatement.setInt(7, Integer.parseInt(numero.getText()));
                        preparedStatement.setInt(8, Integer.parseInt(quantidade.getText()));
                        preparedStatement.setString(9, getDateTime());


                        preparedStatement.executeUpdate();

                        atualizaVenda();

                        alertDialog.alertDialog("Pedido realizado com sucesso!");

                        try {
                            App.setRoot("home");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } finally {
                        returnConnection.closeConnection(returnConnection.getConnection(), preparedStatement);
                    }

                } else {
                    alertDialog.alertDialog("Não há galões suficientes!\n\nGalões restantes: " + table_entegador.getSelectionModel().getSelectedItem().getQuantidade());
                }
            }
        }
    }

    public void atualizaVenda() throws SQLException {
        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement pStatement = null;
        ResultSet res = null;

        int valueVenda = 0;

        try {

            pStatement = returnConnection.getConnection().prepareStatement("SELECT venda FROM db_produto WHERE id_produto = ?");
            pStatement.setInt(1,table_entegador.getSelectionModel().getSelectedItem().getId_produto());
            res = pStatement.executeQuery();

            while (res.next()){
                valueVenda = res.getInt("venda");
            }

            pStatement = null;
            pStatement = returnConnection.getConnection().prepareStatement("UPDATE db_produto SET venda = ? WHERE id_produto = ?");
            pStatement.setInt(1,valueVenda + Integer.parseInt(quantidade.getText()));
            pStatement.setInt(2,table_entegador.getSelectionModel().getSelectedItem().getId_produto());

            pStatement.executeUpdate();

            pStatement = null;
            pStatement = returnConnection.getConnection().prepareStatement("UPDATE db_produto SET quantidade = ? WHERE id_produto = ?");
            pStatement.setInt(1,(table_entegador.getSelectionModel().getSelectedItem().getQuantidade() - Integer.parseInt(quantidade.getText())));
            pStatement.setInt(2,table_entegador.getSelectionModel().getSelectedItem().getId_produto());

            pStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),pStatement);
        }
    }

    public void getProdutoDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        ObservableList<Produto> oblist = FXCollections.observableArrayList();

        try {
            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT nome, preco, quantidade, id_produto FROM db_produto");
            res = preparedStatement.executeQuery();


            while (res.next()){
                oblist.add(new Produto(res.getFloat("preco"),res.getString("nome"),res.getInt("quantidade"),res.getInt("id_produto")));
            }

            this.entregador.setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.precoProduto.setCellValueFactory(new PropertyValueFactory<>("preco"));
            this.qtd_restante.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            this.idProduto.setCellValueFactory(new PropertyValueFactory<>("id_produto"));

            table_entegador.getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
        }
    }

    public void getNomeEntregador() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

        try {

            String query = "SELECT nome FROM db_usuario WHERE tipo_usuario = 2";
            preparedStatement = returnConnection.getConnection().prepareStatement(query);

            res = preparedStatement.executeQuery();


            while (res.next()){
                oblist.add(new Pessoa(res.getString("nome")));
            }

            this.nome_entregador.setCellValueFactory(new PropertyValueFactory<>("nome"));
            tb_nome_entregador.getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
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
