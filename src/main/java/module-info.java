module com.mycompany.kreisondelivery {
    requires javafx.fxml;
    requires java.base;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;
    requires unirest.java;
    requires javafx.controls;


    opens com.mycompany.kreisondelivery to javafx.controls, javafx.fxml;
    exports com.mycompany.kreisondelivery;
}
    