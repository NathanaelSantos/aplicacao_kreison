/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.kreisondelivery;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.isTextFormatterNumber;
import static model.TextFormatter.isTextFormatterString;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.scene.control.ToggleGroup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AlertDialog;
import model.ReturnConnection;

public class NovaEntregaController implements Initializable {

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private AlertDialog alertDialog = new AlertDialog();

    private ReturnConnection connection = new ReturnConnection();
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

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
    private Button cadastraEntrega;
    @FXML
    private MenuButton btnTypeOfClient;

    @FXML
    private RadioMenuItem personType;
    @FXML
    private RadioMenuItem merchantType;

    @FXML
    private ToggleGroup selection;

    @FXML
    private void buttonCadastraEntregaEntered() {
        getCadastraEntrega().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae");
    }

    @FXML
    private void buttonCadastraEntregaExited() {
        getCadastraEntrega().setStyle("-fx-background-radius: 3em; -fx-background-color:  #00b4d8");
    }

    @FXML
    private void mousePressedButtonCadastraEntrega() {
        getCadastraEntrega().setStyle("-fx-background-radius: 3em; -fx-background-color:  #0190ae");
    }

    @FXML
    private void mouseReleaseButtonCadastraEntrega() {
        getCadastraEntrega().setStyle("-fx-background-radius: 3em; -fx-background-color: #00b4d8");
    }

    @FXML
    private void homeScreen() throws IOException {
        App.setRoot("home");
    }

    /**
     * @return a client type.
     * If @return is 4, this client is a person.
     * If @return is 3, this client is a merchant.
     */
    public int typeOfClient(){
        int typeOfCliente = -1;
        if(getMerchantType().isSelected()){
            typeOfCliente = 3; // Tipo comerciante
        }else if(getPersonType().isSelected()){
            typeOfCliente = 4; // Tipo pessoa
        }

        return typeOfCliente;
    }

    public boolean emptyTable() {
        boolean validate = false;

        if (getTable_entegador().getSelectionModel().isEmpty()) {
            getTable_entegador().setStyle("-fx-border-color: red;");
        } else {
            getTable_entegador().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if (getTb_nome_entregador().getSelectionModel().isEmpty()) {
            getTb_nome_entregador().setStyle("-fx-border-color: red;");
        } else {
            getTb_nome_entregador().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4)");
        }

        if (getTable_entegador().getSelectionModel().isEmpty()
                || getTb_nome_entregador().getSelectionModel().isEmpty()) {
            validate = true;
        }
        return validate;
    }

    public boolean composVazios() {

        if (getEndereco_entrega().getText().isBlank())
            getEndereco_entrega().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getEndereco_entrega().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getNome_cliente().getText().isBlank())
            getNome_cliente().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getNome_cliente().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getBairro().getText().isBlank())
            getBairro().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getBairro().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getCep().getText().isBlank())
            getCep().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getCep().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getNumero().getText().isBlank())
            getNumero().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getNumero().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getQuantidade().getText().isBlank())
            getQuantidade().setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            getQuantidade().setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        if (getSelection().getSelectedToggle() == null)
            btnTypeOfClient.setStyle("-fx-border-color: red;-fx-text-fill: #00b4d8");
        else
            btnTypeOfClient.setStyle("-fx-border-color: rgba(27, 72, 171, 0.4);-fx-text-fill: #00b4d8");

        return (getEndereco_entrega().getText().isBlank() || getNome_cliente().getText().isBlank()
                || getBairro().getText().isBlank() || getCep().getText().isBlank() || getNumero().getText().isBlank()
                || getQuantidade().getText().isBlank() || (getSelection().getSelectedToggle() == null));
    }

    private String getDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void cadastraEntrega() throws SQLException, ClassNotFoundException {

        if (emptyTable()) {
            getAlertDialog().alertDialog("É precisso selecionar um campo da tabela!!!");
        } else if (composVazios()) {
            getAlertDialog().alertDialog("Campos precisam estar preenchidos ou selecionados!");
        } else {

            if (Integer.parseInt(getQuantidade().getText()) == 0) {
                getAlertDialog().alertDialog("Digite um valor válido para a quantidade!");
            } else {

                if (Integer.parseInt(getQuantidade().getText()) <= getTable_entegador().getSelectionModel()
                        .getSelectedItem().getQuantidade()) {


                    try {
                        setPreparedStatement(getConnection().getConnection().prepareStatement(
                                "INSERT INTO db_pedido(especiProduto,nomeEntregador,enderEntrega,nomeCliente,bairro,cep,numero,quantidade, tipoCliente, data_entrega) VALUES (?,?,?,?,?,?,?,?,?,?)"));
                        getPreparedStatement().setString(1,
                                getTable_entegador().getSelectionModel().getSelectedItem().getNome());
                        getPreparedStatement().setString(2,
                                getTb_nome_entregador().getSelectionModel().getSelectedItem().getNome());
                        getPreparedStatement().setString(3, getEndereco_entrega().getText());
                        getPreparedStatement().setString(4, getNome_cliente().getText());
                        getPreparedStatement().setString(5, getBairro().getText());
                        getPreparedStatement().setInt(6, Integer.parseInt(getCep().getText()));
                        getPreparedStatement().setInt(7, Integer.parseInt(getNumero().getText()));
                        getPreparedStatement().setInt(8, Integer.parseInt(getQuantidade().getText()));
                        getPreparedStatement().setInt(9, typeOfClient());
                        getPreparedStatement().setString(10, getDateTime());

                        getPreparedStatement().executeUpdate();
                        atualizaVenda();
                        getAlertDialog().alertDialog("Pedido realizado com sucesso!");

                        App.setRoot("novaEntrega");

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
                    }

                } else {
                    getAlertDialog().alertDialog("Não há galões suficientes!\n\nGalões restantes: "
                            + getTable_entegador().getSelectionModel().getSelectedItem().getQuantidade());
                }
            }
        }
    }

    public void atualizaVenda() throws SQLException {

        int valueVenda = 0;

        try {

            setPreparedStatement(getConnection().getConnection()
                    .prepareStatement("SELECT venda FROM db_produto WHERE id_produto = ?"));
            getPreparedStatement().setInt(1, getTable_entegador().getSelectionModel().getSelectedItem().getId_produto());
            setResultSet(getPreparedStatement().executeQuery());

            while (getResultSet().next()) {
                valueVenda = getResultSet().getInt("venda");
            }

            setPreparedStatement(null);
            setPreparedStatement(getConnection().getConnection()
                    .prepareStatement("UPDATE db_produto SET venda = ? WHERE id_produto = ?"));
            getPreparedStatement().setInt(1, valueVenda + Integer.parseInt(getQuantidade().getText()));
            getPreparedStatement().setInt(2, getTable_entegador().getSelectionModel().getSelectedItem().getId_produto());

            getPreparedStatement().executeUpdate();

            setPreparedStatement(null);
            setPreparedStatement(getConnection().getConnection()
                    .prepareStatement("UPDATE db_produto SET quantidade = ? WHERE id_produto = ?"));
            getPreparedStatement().setInt(1, (getTable_entegador().getSelectionModel().getSelectedItem().getQuantidade()
                    - Integer.parseInt(getQuantidade().getText())));
            getPreparedStatement().setInt(2, getTable_entegador().getSelectionModel().getSelectedItem().getId_produto());

            getPreparedStatement().executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
        }
    }

    public void getProdutoDB() throws SQLException, ClassNotFoundException {

        ObservableList<Produto> oblist = FXCollections.observableArrayList();

        try {
            setPreparedStatement(getConnection().getConnection()
                    .prepareStatement("SELECT nome, preco, quantidade, id_produto FROM db_produto"));
            setResultSet(getPreparedStatement().executeQuery());

            while (getResultSet().next()) {
                oblist.add(new Produto(getResultSet().getFloat("preco"), getResultSet().getString("nome"),
                        getResultSet().getInt("quantidade"), getResultSet().getInt("id_produto")));
            }

            this.getEntregador().setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.getPrecoProduto().setCellValueFactory(new PropertyValueFactory<>("preco"));
            this.getQtd_restante().setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            this.getIdProduto().setCellValueFactory(new PropertyValueFactory<>("id_produto"));

            getTable_entegador().getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
        }
    }

    public void getNomeEntregador() throws SQLException, ClassNotFoundException {

        ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

        try {

            String query = "SELECT nome FROM db_usuario WHERE tipo_usuario = 2";
            setPreparedStatement(getConnection().getConnection().prepareStatement(query));

            setResultSet(getPreparedStatement().executeQuery());

            while (getResultSet().next()) {
                oblist.add(new Pessoa(getResultSet().getString("nome")));
            }

            this.getNome_entregador().setCellValueFactory(new PropertyValueFactory<>("nome"));
            getTb_nome_entregador().getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            getConnection().closeConnection(getConnection().getConnection(), getPreparedStatement());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        isTextFormatterString(getEndereco_entrega());
        addTextLimiter(getEndereco_entrega(), 200);
        isTextFormatterString(getNome_cliente());
        addTextLimiter(getNome_cliente(), 200);
        isTextFormatterString(getBairro());
        addTextLimiter(getBairro(), 200);
        isTextFormatterNumber(getQuantidade());
        addTextLimiter(getQuantidade(), 3);
        isTextFormatterNumber(getNumero());
        addTextLimiter(getNumero(), 6);
        isTextFormatterNumber(getCep());
        addTextLimiter(getCep(), 8);

        try {
            getProdutoDB();
            getNomeEntregador();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public TableView<Produto> getTable_entegador() {
        return table_entegador;
    }

    public void setTable_entegador(TableView<Produto> table_entegador) {
        this.table_entegador = table_entegador;
    }

    public TableView<Pessoa> getTb_nome_entregador() {
        return tb_nome_entregador;
    }

    public void setTb_nome_entregador(TableView<Pessoa> tb_nome_entregador) {
        this.tb_nome_entregador = tb_nome_entregador;
    }

    public TableColumn<Pessoa, String> getNome_entregador() {
        return nome_entregador;
    }

    public void setNome_entregador(TableColumn<Pessoa, String> nome_entregador) {
        this.nome_entregador = nome_entregador;
    }

    public TableColumn<Produto, String> getEntregador() {
        return entregador;
    }

    public void setEntregador(TableColumn<Produto, String> entregador) {
        this.entregador = entregador;
    }

    public TableColumn<Produto, Integer> getQtd_restante() {
        return qtd_restante;
    }

    public void setQtd_restante(TableColumn<Produto, Integer> qtd_restante) {
        this.qtd_restante = qtd_restante;
    }

    public TableColumn<Produto, String> getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(TableColumn<Produto, String> precoProduto) {
        this.precoProduto = precoProduto;
    }

    public TableColumn<Produto, Integer> getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(TableColumn<Produto, Integer> idProduto) {
        this.idProduto = idProduto;
    }

    public TextField getEndereco_entrega() {
        return endereco_entrega;
    }

    public void setEndereco_entrega(TextField endereco_entrega) {
        this.endereco_entrega = endereco_entrega;
    }

    public TextField getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(TextField nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public TextField getBairro() {
        return bairro;
    }

    public void setBairro(TextField bairro) {
        this.bairro = bairro;
    }

    public TextField getCep() {
        return cep;
    }

    public void setCep(TextField cep) {
        this.cep = cep;
    }

    public TextField getNumero() {
        return numero;
    }

    public void setNumero(TextField numero) {
        this.numero = numero;
    }

    public TextField getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(TextField quantidade) {
        this.quantidade = quantidade;
    }

    public Button getCadastraEntrega() {
        return cadastraEntrega;
    }

    public void setCadastraEntrega(Button cadastraEntrega) {
        this.cadastraEntrega = cadastraEntrega;
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

    public MenuButton getBtnTypeOfClient() {
        return btnTypeOfClient;
    }

    public void setBtnTypeOfClient(MenuButton btnTypeOfClient) {
        this.btnTypeOfClient = btnTypeOfClient;
    }

    public RadioMenuItem getPersonType() {
        return personType;
    }

    public void setPersonType(RadioMenuItem personType) {
        this.personType = personType;
    }

    public RadioMenuItem getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(RadioMenuItem merchantType) {
        this.merchantType = merchantType;
    }

    public ToggleGroup getSelection() {
        return selection;
    }

    public void setSelection(ToggleGroup selection) {
        this.selection = selection;
    }
}
