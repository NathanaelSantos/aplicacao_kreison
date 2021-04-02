package com.mycompany.kreisondelivery;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private AlertDialog alertDialog = new AlertDialog();

    @FXML
    private TableView<Pessoa> deletaFuncionarioTable;
    @FXML
    private TableColumn<Pessoa, String> nomeFuncionario;
    @FXML
    private TableColumn<Pessoa, String> cpf;
    @FXML
    private TableColumn<Pessoa, Integer> id;

    @FXML
    private Button deletaFuncionario;

    @FXML
    private void buttonDeleteEntered() {
        getDeletaFuncionario().setStyle("-fx-background-radius: 3em; -fx-background-color: #d93636");
    }

    @FXML
    private void buttonDeleteExited() {
        getDeletaFuncionario().setStyle("-fx-background-radius: 3em; -fx-background-color: #ff4848");
    }

    @FXML
    private void mousePressedButtonDelete() {
        getDeletaFuncionario().setStyle("-fx-background-radius: 3em; -fx-background-color: #d93636");
    }

    @FXML
    private void mouseReleaseButtonDelete() {
        getDeletaFuncionario().setStyle("-fx-background-radius: 3em; -fx-background-color: #ff4848");
    }

    @FXML
    void adminScreen() throws IOException {
        App.setRoot("admin");
    }

    public void getUserDB() throws SQLException, ClassNotFoundException {

        ReturnConnection returnConnection = new ReturnConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ObservableList<Pessoa> oblist = FXCollections.observableArrayList();

        preparedStatement = returnConnection.getConnection()
                .prepareStatement("SELECT nome,cpf, id_usuario FROM db_usuario");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            oblist.add(new Pessoa(resultSet.getString("cpf"), resultSet.getString("nome"),
                    resultSet.getInt("id_usuario")));
        }

        this.getNomeFuncionario().setCellValueFactory(new PropertyValueFactory<>("nome"));
        this.getCpf().setCellValueFactory(new PropertyValueFactory<>("cpf"));
        this.getId().setCellValueFactory(new PropertyValueFactory<>("id_usuario"));

        getDeletaFuncionarioTable().getItems().setAll(oblist);

        returnConnection.closeConnection(returnConnection.getConnection(), preparedStatement);

    }

    public void deletaFuncionario() {

        if (getDeletaFuncionarioTable().getSelectionModel().isEmpty()) {
            getAlertDialog().alertDialog("Selecione uma linha da tabela!");
        } else {
            Alert dialogoInfo = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoInfo.setHeaderText("Deseja mesmo deletar o funcionário selecionado?");

            dialogoInfo.showAndWait().filter(ButtonType.OK::equals).ifPresent(b -> {
                ReturnConnection connection = new ReturnConnection();
                PreparedStatement preparedStatement = null;

                try {
                    preparedStatement = connection.getConnection()
                            .prepareStatement("DELETE FROM db_usuario WHERE id_usuario = ?");
                    preparedStatement.setInt(1,
                            getDeletaFuncionarioTable().getSelectionModel().getSelectedItem().getId_usuario());
                    preparedStatement.executeUpdate();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                getAlertDialog().alertDialog("Funcionário deletado com sucesso!");

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

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }

    public void setAlertDialog(AlertDialog alertDialog) {
        this.alertDialog = alertDialog;
    }

    public TableView<Pessoa> getDeletaFuncionarioTable() {
        return deletaFuncionarioTable;
    }

    public void setDeletaFuncionarioTable(TableView<Pessoa> deletaFuncionarioTable) {
        this.deletaFuncionarioTable = deletaFuncionarioTable;
    }

    public TableColumn<Pessoa, String> getNomeFuncionario() {
        return nomeFuncionario;
    }

    public void setNomeFuncionario(TableColumn<Pessoa, String> nomeFuncionario) {
        this.nomeFuncionario = nomeFuncionario;
    }

    public TableColumn<Pessoa, String> getCpf() {
        return cpf;
    }

    public void setCpf(TableColumn<Pessoa, String> cpf) {
        this.cpf = cpf;
    }

    public TableColumn<Pessoa, Integer> getId() {
        return id;
    }

    public void setId(TableColumn<Pessoa, Integer> id) {
        this.id = id;
    }

    public Button getDeletaFuncionario() {
        return deletaFuncionario;
    }

    public void setDeletaFuncionario(Button deletaFuncionario) {
        this.deletaFuncionario = deletaFuncionario;
    }
}
