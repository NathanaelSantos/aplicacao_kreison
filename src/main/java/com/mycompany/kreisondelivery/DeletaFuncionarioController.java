package com.mycompany.kreisondelivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.AlertDialog;
import model.ReturnConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeletaFuncionarioController implements Initializable {
    AlertDialog alertDialog = new AlertDialog();

    @FXML
    private TableView<Pessoa> deletaFuncionarioTable;

    @FXML
    private TableColumn<Pessoa, String> nomeFuncionario;

    @FXML
    private TableColumn<Pessoa, String> cpf;

    @FXML
    private TableColumn<Pessoa, Integer> id;


    @FXML
    void adminScreen() throws IOException {
        App.setRoot("admin");
    }

    public void getUserDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<Pessoa> oblist = FXCollections.observableArrayList();


            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT nome,cpf, id_usuario FROM db_usuario");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                oblist.add(new Pessoa(resultSet.getString("cpf"),resultSet.getString("nome"),resultSet.getInt("id_usuario")));
            }

            this.nomeFuncionario.setCellValueFactory(new PropertyValueFactory<>("nome"));
            this.cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
            this.id.setCellValueFactory(new PropertyValueFactory<>("id_usuario"));

            deletaFuncionarioTable.getItems().setAll(oblist);

            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);

    }

    public void deletaFuncionario()  {

        if(deletaFuncionarioTable.getSelectionModel().isEmpty()){
            alertDialog.alertDialog("Selecione uma linha da tabela!");
        }else {
            Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoInfo.setHeaderText("Deseja mesmo deletar o funcionário selecionado?");

            dialogoInfo.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                ReturnConnection connection = new ReturnConnection();
                PreparedStatement preparedStatement = null;

                try {
                    preparedStatement = connection.getConnection().prepareStatement("DELETE FROM db_usuario WHERE id_usuario = ?");
                    preparedStatement.setInt(1,deletaFuncionarioTable.getSelectionModel().getSelectedItem().getId_usuario());
                    preparedStatement.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                alertDialog.alertDialog("Funcionário deletado com sucesso!");

                try {
                    App.setRoot("deletaFuncionario");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                connection.closeConnection(connection.getConnection(), preparedStatement);
            });
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getUserDB();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
