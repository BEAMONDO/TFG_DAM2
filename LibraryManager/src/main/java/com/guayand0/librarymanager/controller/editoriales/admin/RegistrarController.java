package com.guayand0.librarymanager.controller.editoriales.admin;

import com.guayand0.librarymanager.model.editorial.Editorial;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    EditorialDAO editorialDAO = new EditorialDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField editorialField;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
    }

    private void aplicarLimitesCaracteres() {
        editorialField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String nombre = editorialField.getText();

        Editorial editorial = new Editorial(
                nombre
        );

        boolean registrado = editorialDAO.register(editorial);

        if (registrado) {
            ALERT.showInformation("Editorial registrada correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar la editorial.");
        }
    }

    private boolean validarCampos() {
        if (editorialField.getText().isEmpty()) {
            ALERT.showWarning("Nombre de editorial es obligatoria.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        editorialField.clear();
    }
}
