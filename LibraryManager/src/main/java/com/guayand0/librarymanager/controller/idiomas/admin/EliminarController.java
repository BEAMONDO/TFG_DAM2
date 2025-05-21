package com.guayand0.librarymanager.controller.idiomas.admin;

import com.guayand0.librarymanager.model.idioma.Idioma;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final IdiomaDAO idiomaDAO = new IdiomaDAO();

    @FXML public ComboBox<String> idiomaComboEliminar;

    @FXML public void initialize() {
        cargarNombreIdiomas();
    }

    @FXML private void onDeleteClick() {
        String idiomaSeleccionada = idiomaComboEliminar.getValue();

        if (idiomaSeleccionada == null || idiomaSeleccionada.isEmpty()) {
            ALERT.showError("Por favor, selecciona un idioma válida.");
            return;
        }

        boolean eliminado = idiomaDAO.delete(idiomaSeleccionada);

        if (eliminado) {
            ALERT.showInformation("Idioma eliminada correctamente.");
            idiomaComboEliminar.getItems().remove(idiomaSeleccionada);
            idiomaComboEliminar.setValue(null);
            cargarNombreIdiomas();
        } else {
            ALERT.showError("Error al eliminar el idioma.");
        }
    }

    private void cargarNombreIdiomas() {
        idiomaComboEliminar.getItems().clear();

        List<Idioma> idiomas = idiomaDAO.obtenerIdiomas();
        for (Idioma idioma : idiomas) {
            idiomaComboEliminar.getItems().add(idioma.getNombre());
        }
    }
}
