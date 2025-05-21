package com.guayand0.librarymanager.controller.idiomas.admin;

import com.guayand0.librarymanager.model.idioma.Idioma;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    IdiomaDAO idiomaDAO = new IdiomaDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField idiomaField;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
    }

    private void aplicarLimitesCaracteres() {
        idiomaField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String nombre = idiomaField.getText();

        Idioma idioma = new Idioma(
                nombre
        );

        boolean registrado = idiomaDAO.register(idioma);

        if (registrado) {
            ALERT.showInformation("Idioma registrada correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar el idioma.");
        }
    }

    private boolean validarCampos() {
        if (idiomaField.getText().isEmpty()) {
            ALERT.showWarning("Idioma es obligatoria.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        idiomaField.clear();
    }
}
