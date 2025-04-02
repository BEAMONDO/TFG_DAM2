module com.guayand0.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires org.controlsfx.controls;

    //requires mysql.connector.java;  // Asegúrate de que se agrega el conector JDBC

    opens com.guayand0.librarymanager to javafx.fxml;
    exports com.guayand0.librarymanager;
    exports com.guayand0.librarymanager.controller;
    opens com.guayand0.librarymanager.controller to javafx.fxml;
}