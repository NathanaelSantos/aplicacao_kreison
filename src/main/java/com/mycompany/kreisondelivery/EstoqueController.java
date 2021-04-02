
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class EstoqueController implements Initializable {

    private Integer stockQuantity = 0;
    private Integer salesAmount = 0;
    private LocalDate startingDayOfMonth;
    private LocalDate endDayOfMonth;

    private ReturnConnection connection = new ReturnConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

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

    public void stockQuantity() throws SQLException, ClassNotFoundException {

        try {
            setPreparedStatement(getConnection().getConnection().prepareStatement("SELECT quantidade FROM db_produto"));
            setResultSet(getPreparedStatement().executeQuery());

            while (getResultSet().next()) {
                setStockQuantity(getStockQuantity() + getResultSet().getInt("quantidade"));
            }
        } finally {
            getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
            getResultSet().close();
        }
    }

    public void salesQuantity() throws SQLException {

        LocalDate initial = LocalDate.now();
        setStartingDayOfMonth(initial.with(firstDayOfMonth()));
        setEndDayOfMonth(initial.with(lastDayOfMonth()));

        String startDay = getStartingDayOfMonth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String endDay = getEndDayOfMonth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        try {
            setPreparedStatement(getConnection().getConnection()
                    .prepareStatement("SELECT quantidade FROM db_pedido WHERE data_entrega >= '" + startDay
                            + "' and  data_entrega <= '" + endDay + "'"));
            setResultSet(getPreparedStatement().executeQuery());

            while (getResultSet().next()) {
                setSalesAmount(getSalesAmount() + getResultSet().getInt("quantidade"));
            }
        } finally {
            getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
            getResultSet().close();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            stockQuantity();
            salesQuantity();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("", getStockQuantity()), new PieChart.Data("", getSalesAmount()));

        pieChartData.forEach((PieChart.Data data) -> {
            data.nameProperty().bind(Bindings.concat(data.getName(), " ", (int) data.getPieValue()));
        });
        getPieChart().setData(pieChartData);
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getSalesAmount() {
        return salesAmount;
    }

    public void setSalesAmount(Integer salesAmount) {
        this.salesAmount = salesAmount;
    }

    public ReturnConnection getConnection() {
        return connection;
    }

    public void setConnection(ReturnConnection connection) {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
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

    public LocalDate getStartingDayOfMonth() {
        return startingDayOfMonth;
    }

    public void setStartingDayOfMonth(LocalDate startingDayOfMonth) {
        this.startingDayOfMonth = startingDayOfMonth;
    }

    public LocalDate getEndDayOfMonth() {
        return endDayOfMonth;
    }

    public void setEndDayOfMonth(LocalDate endDayOfMonth) {
        this.endDayOfMonth = endDayOfMonth;
    }
}
