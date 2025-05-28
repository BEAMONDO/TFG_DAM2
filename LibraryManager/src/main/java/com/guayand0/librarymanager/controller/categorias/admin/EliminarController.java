package com.guayand0.librarymanager.controller.categorias.admin;

import com.guayand0.librarymanager.model.categoria.Categoria;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    @FXML public ComboBox<String> categoriaComboEliminar;

    @FXML public void initialize() {
        new Thread(this::cargarNombreCategorias).start();
    }

    @FXML private void onDeleteClick() {
        String categoriaSeleccionada = categoriaComboEliminar.getValue();

        if (categoriaSeleccionada == null || categoriaSeleccionada.isEmpty()) {
            ALERT.showError("Por favor, selecciona una Categoría válida.");
            return;
        }

        boolean eliminado = categoriaDAO.delete(categoriaSeleccionada);

        if (eliminado) {
            ALERT.showInformation("Categoría eliminada correctamente.");
            categoriaComboEliminar.getItems().remove(categoriaSeleccionada);
            categoriaComboEliminar.setValue(null);
            cargarNombreCategorias();
        } else {
            ALERT.showError("Error al eliminar la categoría.");
        }
    }

    private void cargarNombreCategorias() {
        categoriaComboEliminar.getItems().clear();

        List<Categoria> categorias = categoriaDAO.obtenerCategorias();
        for (Categoria categoria : categorias) {
            categoriaComboEliminar.getItems().add(categoria.getNombre());
        }
    }
}
