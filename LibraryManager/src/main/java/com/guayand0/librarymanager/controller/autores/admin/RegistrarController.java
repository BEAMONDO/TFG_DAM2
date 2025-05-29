package com.guayand0.librarymanager.controller.autores.admin;

import com.guayand0.librarymanager.model.autor.Autor;
import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    private final AutorDAO autorDAO = new AutorDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField nombreField, apellidoField, paisField;
    @FXML private DatePicker fechaField;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
    }

    private void aplicarLimitesCaracteres() {
        nombreField.setTextFormatter(LC.createTextFormatter(50));
        apellidoField.setTextFormatter(LC.createTextFormatter(50));
        paisField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String pais = paisField.getText();
        String fechaNacimiento = fechaField.getValue().toString();

        Autor autor = new Autor(
                nombre, apellido, pais, fechaNacimiento
        );

        boolean registrado = autorDAO.register(autor);

        if (registrado) {
            ALERT.showInformation("Autor registrado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar el autor.");
        }
    }

    private boolean validarCampos() {
        if (nombreField.getText().isEmpty()) {
            ALERT.showWarning("Nombre de autor es obligatorio.");
            return false;
        }

        if (apellidoField.getText().isEmpty()) {
            ALERT.showWarning("Apellido de autor es obligatorio.");
            return false;
        }

        if (paisField.getText().isEmpty()) {
            ALERT.showWarning("Pais de autor es obligatorio.");
            return false;
        }

        if (fechaField.getValue() == null) {
            ALERT.showWarning("Fecha de nacimiento es obligatorio.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        paisField.clear();
        fechaField.setValue(null);
    }
}
