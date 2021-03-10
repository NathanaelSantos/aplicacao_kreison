package com.mycompany.kreisondelivery;

import model.ReturnConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckCpfdatabase {
    ReturnConnection returnConnection = new ReturnConnection();

    public boolean checkCpfdatabase(String cpfUser) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        boolean existe = false;

        try {

            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT (1) FROM db_usuario WHERE cpf = ?");
            preparedStatement.setString(1,cpfUser);
            res = preparedStatement.executeQuery();

            while (res.next()) {
                if(res.getRow() > 0)
                    existe = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
        }

        return existe;
    }
}
