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

    AlertDialog alertDialog = new AlertDialog();

    @FXML
    private TableView<Produto> deliveryTable;

    @FXML
    private TableColumn<Produto, String> deliveryMan;

    @FXML
    private TableColumn<Produto, String> priceProduct;

    @FXML
    private TableColumn<Produto, Integer> remainingQuantity;

    @FXML
    private TableColumn<Produto, Integer> idProduct;

    @FXML
    private TextField newNameProduct;

    @FXML
    private TextField newPrice;

    @FXML
    private TextField quantidade;

    @FXML
    private StackPane alertModificaProduto;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnDeletar;


    @FXML
    private void adminScreen() throws IOException, ClassNotFoundException {
        App.setRoot("admin");
    }

    @FXML
    public void editProduct(){
        if(deliveryTable.getSelectionModel().isEmpty()){
            alertDialog.alertDialog("Selecione uma linha da tabela!");
        }else {

            alertModificaProduto.setVisible(true);
            btnEditar.setDisable(true);
            btnDeletar.setDisable(true);
            deliveryTable.setDisable(true);

            newNameProduct.setText(deliveryTable.getSelectionModel().getSelectedItem().getNome());
            newPrice.setText(String.valueOf(deliveryTable.getSelectionModel().getSelectedItem().getPreco()));
            quantidade.setText(Integer.toString(deliveryTable.getSelectionModel().getSelectedItem().getQuantidade()));
        }

    }
    @FXML
    public void buttonEditProduct() throws IOException, SQLException, ClassNotFoundException {
        new EditOrDeleteProduct().editProduct(
                newNameProduct.getText(),
                Float.parseFloat(newPrice.getText()),
                Integer.parseInt(quantidade.getText()),
                deliveryTable.getSelectionModel().getSelectedItem().getId_produto());

        exitEditProdutoScreen();
        getProdutoDB();
    }

    @FXML
    private void exitEditProdutoScreen() throws IOException {
        alertModificaProduto.setVisible(false);
        btnEditar.setDisable(false);
        btnDeletar.setDisable(false);
        deliveryTable.setDisable(false);
    }

    @FXML
    public void deleteProduct(){
        if(deliveryTable.getSelectionModel().isEmpty()){
            alertDialog.alertDialog("Selecione uma linha da tabela!");
        }else {
            new EditOrDeleteProduct().deleteProduct(
                    deliveryTable.getSelectionModel().getSelectedItem().getId_produto()
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

            this.deliveryMan.setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.priceProduct.setCellValueFactory(new PropertyValueFactory<>("preco"));
            this.remainingQuantity.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            this.idProduct.setCellValueFactory(new PropertyValueFactory<>("id_produto"));

            deliveryTable.getItems().setAll(oblist);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),pstment);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alertModificaProduto.setVisible(false);

        isTextFormatterString(newNameProduct);
        addTextLimiter(newNameProduct, 200);
        isTextFormatterFloat(newPrice);
        addTextLimiter(newPrice,5);
        isTextFormatterFloat(quantidade);
        addTextLimiter(quantidade,5);

        try {
            getProdutoDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
