package com.guayand0.librarymanager.controller.libros.admin;

import com.guayand0.librarymanager.model.libro.Libro;
import com.guayand0.librarymanager.model.libro.LibroDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final LibroDAO libroDAO = new LibroDAO();

    @FXML
    public ComboBox<String> libroComboEliminar;

    @FXML public void initialize() {
        cargarNombreLibros();
    }

    @FXML private void onDeleteClick() {
        String libroSeleccionado = libroComboEliminar.getValue();

        if (libroSeleccionado == null || libroSeleccionado.isEmpty()) {
            ALERT.showError("Por favor, selecciona un libro válido.");
            return;
        }

        boolean eliminado = libroDAO.delete(libroSeleccionado);

        if (eliminado) {
            ALERT.showInformation("Libro eliminado correctamente.");
            libroComboEliminar.getItems().remove(libroSeleccionado);
            libroComboEliminar.setValue(null);
            cargarNombreLibros();
        } else {
            ALERT.showError("Error al eliminar el libro.");
        }
    }

    private void cargarNombreLibros() {
        libroComboEliminar.getItems().clear();

        List<String> libros = libroDAO.obtenerLibros();
        libroComboEliminar.getItems().addAll(libros);
    }
}
