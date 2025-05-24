package com.guayand0.librarymanager.controller.idiomas.admin;

import com.guayand0.librarymanager.model.idioma.Idioma;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final IdiomaDAO idiomaDAO = new IdiomaDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    @FXML public ComboBox<String> idiomaComboModificar;
    @FXML private TextField idiomaField;
    @FXML public void initialize() {
        aplicarLimitesCaracteres();

        cargarNombreIdiomas();
    }

    private void aplicarLimitesCaracteres() {
        idiomaField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String nombre = idiomaComboModificar.getValue();
        String nuevoNombre = idiomaField.getText();

        String[] datos = {
                nombre, nuevoNombre
        };

        boolean actualizado = idiomaDAO.modify(datos);

        if (actualizado) {
            ALERT.showInformation("Idioma modificado correctamente.");
            limpiarCampos();
            cargarNombreIdiomas();
        } else {
            ALERT.showError("Error al modificar el idioma.");
        }
    }

    private boolean validarCampos() {
        String idioma = idiomaComboModificar.getValue();

        if (idioma == null) {
            ALERT.showWarning("El campo Idioma es obligatorio.");
            return false;
        }

        if (idiomaField.getText().isEmpty()) {
            ALERT.showWarning("El campo Nuevo idioma es obligatoria.");
            return false;
        }

        return true;
    }

    private void cargarNombreIdiomas() {
        idiomaComboModificar.getItems().clear();

        List<Idioma> idiomas = idiomaDAO.obtenerIdiomas();
        for (Idioma idioma : idiomas) {
            idiomaComboModificar.getItems().add(idioma.getNombre());
        }
    }

    private void limpiarCampos() {
        idiomaComboModificar.getItems().clear();
        idiomaField.clear();
    }
}
