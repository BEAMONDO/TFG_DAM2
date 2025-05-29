module com.guayand0.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens com.guayand0.librarymanager to javafx.fxml;
    exports com.guayand0.librarymanager;

    opens com.guayand0.librarymanager.controller.acceso to javafx.fxml;

    exports com.guayand0.librarymanager.controller.main;
    opens com.guayand0.librarymanager.controller.main;

    exports com.guayand0.librarymanager.controller.libros;
    opens com.guayand0.librarymanager.controller.libros;
    opens com.guayand0.librarymanager.controller.libros.admin to javafx.fxml;
    opens com.guayand0.librarymanager.controller.libros.user to javafx.fxml;
    opens com.guayand0.librarymanager.model.libro to javafx.base;

    exports com.guayand0.librarymanager.controller.autores;
    opens com.guayand0.librarymanager.controller.autores;
    opens com.guayand0.librarymanager.controller.autores.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.categorias;
    opens com.guayand0.librarymanager.controller.categorias;
    opens com.guayand0.librarymanager.controller.categorias.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.editoriales;
    opens com.guayand0.librarymanager.controller.editoriales;
    opens com.guayand0.librarymanager.controller.editoriales.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.idiomas;
    opens com.guayand0.librarymanager.controller.idiomas;
    opens com.guayand0.librarymanager.controller.idiomas.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.usuarios;
    opens com.guayand0.librarymanager.controller.usuarios;
    opens com.guayand0.librarymanager.controller.usuarios.user to javafx.fxml;
    opens com.guayand0.librarymanager.controller.usuarios.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.prestamos;
    opens com.guayand0.librarymanager.controller.prestamos;
    opens com.guayand0.librarymanager.controller.prestamos.admin to javafx.fxml;

    exports com.guayand0.librarymanager.controller.devoluciones;
    opens com.guayand0.librarymanager.controller.devoluciones;
    opens com.guayand0.librarymanager.controller.devoluciones.admin to javafx.fxml;
}
