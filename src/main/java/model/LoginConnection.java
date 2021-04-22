package model;

import java.sql.*;

public class LoginConnection {
    ReturnConnection connection = new ReturnConnection();
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;

    public String loginConection(String parameterValue, String columnName) {

        String returnStr = null;

        try {
            preparedStatement = connection.getConnection().prepareStatement("SELECT " + columnName + " FROM db_usuario WHERE cpf = ?");

            if (preparedStatement != null) {
                preparedStatement.setString(1, parameterValue);
                resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    returnStr = resultSet.getString(columnName);
                }
            } else {
                new AlertDialog().alertDialog("Erro de conexao!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (preparedStatement != null)
                connection.closeConnection(connection.getConnection(), preparedStatement);
        }

        return returnStr;
    }
}
