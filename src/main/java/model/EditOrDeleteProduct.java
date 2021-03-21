package model;

import com.mycompany.kreisondelivery.AdminController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditOrDeleteProduct {
    AlertDialog alertDialog = new AlertDialog();

    public void editProduct(String newNameProduct, float priceProduct, int quantidade, int idProduct){
        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement pstment = null;

        try {
            pstment = returnConnection.getConnection().prepareStatement("UPDATE db_produto SET nome = ?,preco = ?,quantidade =? WHERE id_produto = ?");
            pstment.setString(1,newNameProduct);
            pstment.setFloat(2, priceProduct);
            pstment.setInt(3,quantidade);
            pstment.setInt(4,idProduct);
            pstment.executeUpdate();

            alertDialog.alertDialog("Produto alterado com sucesso!!!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            returnConnection.closeConnection(returnConnection.getConnection(), pstment);
        }
    }

    public void deleteProduct(int idProduct){

        Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoInfo.setHeaderText("Deseja mesmo excluir o produto selecionada?");

        dialogoInfo.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {

            ReturnConnection returnConnection = new ReturnConnection();
            PreparedStatement pstment = null;

            try {

                pstment = returnConnection.getConnection().prepareStatement("DELETE FROM db_produto WHERE id_produto = ?");
                pstment.setInt(1,idProduct);
                pstment.executeUpdate();

                try {
                    new AdminController().updateTableProduct();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                alertDialog.alertDialog("Produto excluído com sucesso!");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                returnConnection.closeConnection(returnConnection.getConnection(),pstment);
            }
        });
    }
}
