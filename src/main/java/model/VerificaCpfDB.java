package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VerificaCpfDB {
    ReturnConnection returnConnection = new ReturnConnection();

    public boolean verificaCpfDB(String cpfUser) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatement = null;
        ResultSet res = null;
        boolean existe = false;

        try {
            preparedStatement = returnConnection.getConnection().prepareStatement("SELECT COUNT(1) FROM db_usuario WHERE cpf = ?");
            preparedStatement.setString(1,cpfUser);
            res = preparedStatement.executeQuery();

            while (res.next())
                existe = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            returnConnection.closeConnection(returnConnection.getConnection(),preparedStatement);
        }

        return existe;
    }
}
