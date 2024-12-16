module org.guayando.libarymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.desktop;
    requires java.sql;

    opens org.guayando.librarymanager to javafx.fxml;
    exports org.guayando.librarymanager;
    exports org.guayando.librarymanager.controller;
    opens org.guayando.librarymanager.controller to javafx.fxml;
}