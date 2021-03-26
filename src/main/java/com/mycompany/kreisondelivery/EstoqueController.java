/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ReturnConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author natha
 */
public class EstoqueController implements Initializable {

    private Integer valueEstoque = 0;
    private Integer valueVenda = 0;
    @FXML
    private PieChart pieChart;

    @FXML
    private TableView<Produto> table_estoque;

    @FXML
    private TableColumn<Produto, String> especproduto;

    @FXML
    private TableColumn<Produto, Integer> qtd_restante;

    @FXML
    private TableColumn<Produto, Float> precoProduto;

    @FXML
    private TableColumn<Produto, Integer> total_vendas;


    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            getQuantidadeEstoqueDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("", this.getValueEstoque()),
                new PieChart.Data("", this.getValueVenda())
        );
       
        pieChartData.forEach((PieChart.Data data) -> {
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " ", (int) data.getPieValue()
                    )
            );
        });
        getPieChart().setData(pieChartData);
    }

    public void getQuantidadeEstoqueDB() throws SQLException, ClassNotFoundException {

        ReturnConnection connection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            String query = "SELECT quantidade,venda FROM db_produto";
            preparedStatement = connection.getConnection().prepareStatement(query);

            resultSet = preparedStatement.executeQuery();


            while (resultSet.next()){
                setValueEstoque(getValueEstoque() + resultSet.getInt("quantidade"));
                setValueVenda(getValueVenda() + resultSet.getInt("venda"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            connection.closeConnection(connection.getConnection(),preparedStatement);
        }
    }

    public Integer getValueEstoque() {
        return valueEstoque;
    }

    public void setValueEstoque(Integer valueEstoque) {
        this.valueEstoque = valueEstoque;
    }

    public Integer getValueVenda() {
        return valueVenda;
    }

    public void setValueVenda(Integer valueVenda) {
        this.valueVenda = valueVenda;
    }

    public PieChart getPieChart() {
        return pieChart;
    }

    public void setPieChart(PieChart pieChart) {
        this.pieChart = pieChart;
    }

    public TableView<Produto> getTable_estoque() {
        return table_estoque;
    }

    public void setTable_estoque(TableView<Produto> table_estoque) {
        this.table_estoque = table_estoque;
    }

    public TableColumn<Produto, String> getEspecproduto() {
        return especproduto;
    }

    public void setEspecproduto(TableColumn<Produto, String> especproduto) {
        this.especproduto = especproduto;
    }

    public TableColumn<Produto, Integer> getQtd_restante() {
        return qtd_restante;
    }

    public void setQtd_restante(TableColumn<Produto, Integer> qtd_restante) {
        this.qtd_restante = qtd_restante;
    }

    public TableColumn<Produto, Float> getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(TableColumn<Produto, Float> precoProduto) {
        this.precoProduto = precoProduto;
    }

    public TableColumn<Produto, Integer> getTotal_vendas() {
        return total_vendas;
    }

    public void setTotal_vendas(TableColumn<Produto, Integer> total_vendas) {
        this.total_vendas = total_vendas;
    }
}
