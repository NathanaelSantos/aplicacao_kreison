package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnConnection {

    public Connection returnConnection() throws ClassNotFoundException, SQLException {

        Connection connection = null;
        MysqlConnection mysqlConnection = new MysqlConnection();

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(mysqlConnection.getUrl(),mysqlConnection.getUser(),mysqlConnection.getPassword());

        return  connection;
    }

    public void closeConnection(Connection connection) throws SQLException {
       connection.close();
    }

    public void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

}
