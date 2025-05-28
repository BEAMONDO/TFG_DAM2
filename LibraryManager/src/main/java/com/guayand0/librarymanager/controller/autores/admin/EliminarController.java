package com.guayand0.librarymanager.controller.autores.admin;

import com.guayand0.librarymanager.model.autor.Autor;
import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final AutorDAO autorDAO = new AutorDAO();

    @FXML public ComboBox<String> nombreCompletoCombo;

    private final Map<String, Autor> autorMap = new HashMap<>();

    @FXML public void initialize() {
        new Thread(this::cargarNombreAutores).start();
    }

    @FXML private void onDeleteClick() {
        String autorSeleccionado = nombreCompletoCombo.getValue();

        if (autorSeleccionado == null || autorSeleccionado.isEmpty()) {
            ALERT.showError("Por favor, selecciona un Autor válido.");
            return;
        }

        Autor autor = autorMap.get(autorSeleccionado);
        if (autor == null) {
            ALERT.showError("No se pudo encontrar el autor.");
            return;
        }

        boolean eliminado = autorDAO.delete(autor.getNombre(), autor.getApellido());

        if (eliminado) {
            ALERT.showInformation("Autor eliminado correctamente.");
            nombreCompletoCombo.getItems().remove(autorSeleccionado);
            nombreCompletoCombo.setValue(null);
            cargarNombreAutores();
        } else {
            ALERT.showError("Error al eliminar el autor.");
        }
    }

    private void cargarNombreAutores() {
        nombreCompletoCombo.getItems().clear();
        autorMap.clear();

        List<Autor> autores = autorDAO.obtenerAutores();
        for (Autor autor : autores) {
            String nombreCompleto = autor.getNombre() + " " + autor.getApellido();
            nombreCompletoCombo.getItems().add(nombreCompleto);
            autorMap.put(nombreCompleto, autor);
        }
    }
}
