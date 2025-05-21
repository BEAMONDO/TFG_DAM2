package com.guayand0.librarymanager.controller.categorias.admin;

import com.guayand0.librarymanager.model.categoria.Categoria;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    @FXML public ComboBox<String> categoriaComboModificar;
    @FXML private TextField categoriaField;
    @FXML public void initialize() {
        aplicarLimitesCaracteres();

        cargarNombreCategorias();
    }

    private void aplicarLimitesCaracteres() {
        categoriaField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML
    private void onModifyClick() {
        if (!validarCampos()) return;

        String nombre = categoriaComboModificar.getValue();
        String nuevoNombre = categoriaField.getText();

        String[] datos = {
                nombre, nuevoNombre
        };

        boolean actualizado = categoriaDAO.modify(datos);

        if (actualizado) {
            ALERT.showInformation("Categoría modificada correctamente.");
            limpiarCampos();
            cargarNombreCategorias();
        } else {
            ALERT.showError("Error al modificar la categoría.");
        }
    }

    private boolean validarCampos() {
        String categoria = categoriaComboModificar.getValue();

        if (categoria == null) {
            ALERT.showWarning("El campo Nombre categoria es obligatorio.");
            return false;
        }

        if (categoriaField.getText().isEmpty()) {
            ALERT.showWarning("El campo Nuevo nombre es obligatoria.");
            return false;
        }

        return true;
    }

    private void cargarNombreCategorias() {
        categoriaComboModificar.getItems().clear();

        List<Categoria> categorias = categoriaDAO.obtenerCategorias();
        for (Categoria categoria : categorias) {
            categoriaComboModificar.getItems().add(categoria.getNombre());
        }
    }

    private void limpiarCampos() {
        categoriaComboModificar.getItems().clear();
        categoriaField.clear();
    }
}
