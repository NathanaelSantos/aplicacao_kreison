package model;

import javafx.scene.control.Alert;

import java.sql.*;


public class LoginConnection {
    ReturnConnection returnConnection = new ReturnConnection();
    PreparedStatement preparedStatement = null;
    ResultSet res;

    public String loginConection(String parameterValue, String columnName)  {

        String returnStr = null;

            try {
                preparedStatement = returnConnection.getConnection().prepareStatement("SELECT " + columnName + " FROM db_usuario WHERE cpf = ?");

                if(preparedStatement!= null){
                    preparedStatement.setString(1, parameterValue);
                    res = preparedStatement.executeQuery();

                    while (res.next()) {
                        returnStr = res.getString(columnName);
                    }
                }else{
                    new AlertDialog().alertDialog("Erro de conexao!");
                }
            }catch (SQLException ex) {
                ex.printStackTrace();
            }finally {
                if(preparedStatement != null)
                    returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
            }

        return returnStr;
    }
}
