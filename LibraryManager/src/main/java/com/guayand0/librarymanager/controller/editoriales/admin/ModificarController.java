package com.guayand0.librarymanager.controller.editoriales.admin;

import com.guayand0.librarymanager.model.editorial.Editorial;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final EditorialDAO editorialDAO = new EditorialDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    @FXML public ComboBox<String> editorialComboModificar;
    @FXML private TextField editorialField;
    @FXML public void initialize() {
        aplicarLimitesCaracteres();

        cargarNombreEditoriales();
    }

    private void aplicarLimitesCaracteres() {
        editorialField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML
    private void onModifyClick() {
        if (!validarCampos()) return;

        String nombre = editorialComboModificar.getValue();
        String nuevoNombre = editorialField.getText();

        String[] datos = {
                nombre, nuevoNombre
        };

        boolean actualizado = editorialDAO.modify(datos);

        if (actualizado) {
            ALERT.showInformation("Editorial modificada correctamente.");
            limpiarCampos();
            cargarNombreEditoriales();
        } else {
            ALERT.showError("Error al modificar la editorial.");
        }
    }

    private boolean validarCampos() {
        String editorial = editorialComboModificar.getValue();

        if (editorial == null) {
            ALERT.showWarning("El campo Nombre editorial es obligatorio.");
            return false;
        }

        if (editorialField.getText().isEmpty()) {
            ALERT.showWarning("El campo Nuevo nombre es obligatoria.");
            return false;
        }

        return true;
    }

    private void cargarNombreEditoriales() {
        editorialComboModificar.getItems().clear();

        List<Editorial> editoriales = editorialDAO.obtenerEditoriales();
        for (Editorial editorial : editoriales) {
            editorialComboModificar.getItems().add(editorial.getNombre());
        }
    }

    private void limpiarCampos() {
        editorialComboModificar.getItems().clear();
        editorialField.clear();
    }
}
