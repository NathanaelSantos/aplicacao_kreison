package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckCpfdatabase {
    private ReturnConnection returnConnection = new ReturnConnection();

    public boolean checkCpfdatabase(String cpfUser) throws SQLException, ClassNotFoundException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean check = false;

        try {

            preparedStatement = getReturnConnection().getConnection().prepareStatement("SELECT (1) FROM db_usuario WHERE cpf = ?");
            preparedStatement.setString(1,cpfUser);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if(resultSet.getRow() > 0)
                    check = true;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            getReturnConnection().closeConnection(getReturnConnection().getConnection(),preparedStatement);
        }

        return check;
    }

    public ReturnConnection getReturnConnection() {
        return returnConnection;
    }

    public void setReturnConnection(ReturnConnection returnConnection) {
        this.returnConnection = returnConnection;
    }
}
