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
import javafx.scene.input.MouseEvent;
import model.ReturnConnection;
import model.Windows;

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
public class EstoqueController implements Initializable, Windows {

    Integer valueEstoque = 0;
    Integer valueVenda = 0;
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
    private void handleClose(MouseEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void handleMinimize(MouseEvent event) throws IOException {
        new App().getStage().setIconified(true);
    }

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
                new PieChart.Data("",this.valueEstoque),
                new PieChart.Data("",this.valueVenda)
        );
       
        pieChartData.forEach((PieChart.Data data) -> {
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " ", (int) data.getPieValue()
                    )
            );
        });
        pieChart.setData(pieChartData);
    }

    public void getQuantidadeEstoqueDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet res = null;

        try {

            String query = "SELECT quantidade,venda FROM db_produto";
            preparedStatement = returnConnection.getConnection().prepareStatement(query);

            res = preparedStatement.executeQuery();


            while (res.next()){
                valueEstoque += res.getInt("quantidade");
                valueVenda += res.getInt("venda");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
        }
    }
}
