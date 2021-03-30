package com.mycompany.kreisondelivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.AlertDialog;
import model.EditOrDeleteProduct;
import model.ReturnConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static model.AddTextLimiter.addTextLimiter;
import static model.TextFormatter.*;

public class EditOrDeleteProductController implements Initializable {

    private AlertDialog alertDialog = new AlertDialog();

    @FXML private TableView<Produto> deliveryTable;
    @FXML private TableColumn<Produto, String> deliveryMan;
    @FXML private TableColumn<Produto, String> priceProduct;
    @FXML private TableColumn<Produto, Integer> remainingQuantity;
    @FXML private TableColumn<Produto, Integer> idProduct;

    @FXML private TextField newNameProduct;
    @FXML private TextField newPrice;
    @FXML private TextField quantidade;

    @FXML private StackPane alertModificaProduto;

    @FXML private Button btnEditar;
    @FXML private Button btnDeletar;


    @FXML private void adminScreen() throws IOException, ClassNotFoundException { App.setRoot("admin"); }

    @FXML
    public void editProduct(){
        if(getDeliveryTable().getSelectionModel().isEmpty()){
            getAlertDialog().alertDialog("Selecione uma linha da tabela!");
        }else {

            getAlertModificaProduto().setVisible(true);
            getBtnEditar().setDisable(true);
            getBtnDeletar().setDisable(true);
            getDeliveryTable().setDisable(true);

            getNewNameProduct().setText(getDeliveryTable().getSelectionModel().getSelectedItem().getNome());
            getNewPrice().setText(String.valueOf(getDeliveryTable().getSelectionModel().getSelectedItem().getPreco()));
            getQuantidade().setText(Integer.toString(getDeliveryTable().getSelectionModel().getSelectedItem().getQuantidade()));
        }

    }
    @FXML
    public void buttonEditProduct() throws IOException, SQLException, ClassNotFoundException {
        new EditOrDeleteProduct().editProduct(
                getNewNameProduct().getText(),
                Float.parseFloat(getNewPrice().getText()),
                Integer.parseInt(getQuantidade().getText()),
                getDeliveryTable().getSelectionModel().getSelectedItem().getId_produto());

        exitEditProdutoScreen();
        getProdutoDB();
    }

    @FXML
    private void exitEditProdutoScreen() throws IOException {
        getAlertModificaProduto().setVisible(false);
        getBtnEditar().setDisable(false);
        getBtnDeletar().setDisable(false);
        getDeliveryTable().setDisable(false);
    }

    @FXML
    public void deleteProduct(){
        if(getDeliveryTable().getSelectionModel().isEmpty()){
            getAlertDialog().alertDialog("Selecione uma linha da tabela!");
        }else {
            new EditOrDeleteProduct().deleteProduct(
                    getDeliveryTable().getSelectionModel().getSelectedItem().getId_produto()
            );
        }
    }

    public void getProdutoDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement pstment = null;
        ResultSet res = null;

        ObservableList<Produto> oblist = FXCollections.observableArrayList();

        try {
            pstment = returnConnection.getConnection().prepareStatement("SELECT nome, preco, quantidade, id_produto FROM db_produto");
            res = pstment.executeQuery();

            while (res.next()){ oblist.add(new Produto(res.getFloat("preco"),res.getString("nome"),res.getInt("quantidade"),res.getInt("id_produto"))); }

            this.getDeliveryMan().setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.getPriceProduct().setCellValueFactory(new PropertyValueFactory<>("preco"));
            this.getRemainingQuantity().setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            this.getIdProduct().setCellValueFactory(new PropertyValueFactory<>("id_produto"));

            getDeliveryTable().getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),pstment);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getAlertModificaProduto().setVisible(false);

        isTextFormatterString(getNewNameProduct());
        addTextLimiter(getNewNameProduct(), 200);
        isTextFormatterFloat(getNewPrice());
        addTextLimiter(getNewPrice(),5);
        isTextFormatterFloat(getQuantidade());
        addTextLimiter(getQuantidade(),5);

        try {
            getProdutoDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public TableView<Produto> getDeliveryTable() {
        return deliveryTable;
    }

    public void setDeliveryTable(TableView<Produto> deliveryTable) {
        this.deliveryTable = deliveryTable;
    }

    public TableColumn<Produto, String> getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(TableColumn<Produto, String> deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public TableColumn<Produto, String> getPriceProduct() {
        return priceProduct;
    }

    public void setPriceProduct(TableColumn<Produto, String> priceProduct) {
        this.priceProduct = priceProduct;
    }

    public TableColumn<Produto, Integer> getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(TableColumn<Produto, Integer> remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public TableColumn<Produto, Integer> getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(TableColumn<Produto, Integer> idProduct) {
        this.idProduct = idProduct;
    }

    public TextField getNewNameProduct() {
        return newNameProduct;
    }

    public void setNewNameProduct(TextField newNameProduct) {
        this.newNameProduct = newNameProduct;
    }

    public TextField getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(TextField newPrice) {
        this.newPrice = newPrice;
    }

    public TextField getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(TextField quantidade) {
        this.quantidade = quantidade;
    }

    public StackPane getAlertModificaProduto() {
        return alertModificaProduto;
    }

    public void setAlertModificaProduto(StackPane alertModificaProduto) {
        this.alertModificaProduto = alertModificaProduto;
    }

    public Button getBtnEditar() {
        return btnEditar;
    }

    public void setBtnEditar(Button btnEditar) {
        this.btnEditar = btnEditar;
    }

    public Button getBtnDeletar() {
        return btnDeletar;
    }

    public void setBtnDeletar(Button btnDeletar) {
        this.btnDeletar = btnDeletar;
    }
}
