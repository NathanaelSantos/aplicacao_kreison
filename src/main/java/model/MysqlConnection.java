package model;

public class MysqlConnection {

    private String url = "jdbc:mysql://localhost:3306/db_kreison_delivery";
    private String user = "root";
    private String password = "";


    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

}
