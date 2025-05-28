package com.guayand0.librarymanager.controller.editoriales.admin;

import com.guayand0.librarymanager.model.editorial.Editorial;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final EditorialDAO editorialDAO = new EditorialDAO();

    @FXML public ComboBox<String> editorialComboEliminar;

    @FXML public void initialize() {
        new Thread(this::cargarNombreEditoriales).start();
    }

    @FXML private void onDeleteClick() {
        String editorialSeleccionada = editorialComboEliminar.getValue();

        if (editorialSeleccionada == null || editorialSeleccionada.isEmpty()) {
            ALERT.showError("Por favor, selecciona una editorial válida.");
            return;
        }

        boolean eliminado = editorialDAO.delete(editorialSeleccionada);

        if (eliminado) {
            ALERT.showInformation("Editorial eliminada correctamente.");
            editorialComboEliminar.getItems().remove(editorialSeleccionada);
            editorialComboEliminar.setValue(null);
            cargarNombreEditoriales();
        } else {
            ALERT.showError("Error al eliminar la editorial.");
        }
    }

    private void cargarNombreEditoriales() {
        editorialComboEliminar.getItems().clear();

        List<Editorial> editoriales = editorialDAO.obtenerEditoriales();
        for (Editorial editorial : editoriales) {
            editorialComboEliminar.getItems().add(editorial.getNombre());
        }
    }
}
