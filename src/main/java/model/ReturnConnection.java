package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReturnConnection {

    private Connection connection;

    public Connection getConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_kreison_delivery", "root", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return connection;
    }

    public void closeConnection(Connection connection, PreparedStatement preparedStatement) {
        try {
            connection.close();
            preparedStatement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
