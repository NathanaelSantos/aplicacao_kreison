module com.mycompany.kreisondelivery {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires javafx.base;
    requires java.sql;
    requires java.desktop;

    opens com.mycompany.kreisondelivery to javafx.fxml,sample.Datamodel, javafx.base;
    exports com.mycompany.kreisondelivery;
}
    