package com.guayand0.librarymanager.controller.usuarios.user;

import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField dniField, nombreField, apellidoField, emailField, telefonoField, direccionField;
    @FXML private CheckBox passwordCheckHide;
    @FXML private TextField oldPasswordField, newPasswordField;
    @FXML private PasswordField oldPasswordFieldMask, newPasswordFieldMask;
    @FXML private DatePicker fechaField;
    @FXML private ComboBox sexoCombo;
    @FXML private Button modificarButton;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        cargarDNIUsuario();
        cargaFechaUsuario();
        cargarNombreUsuario();
        cargarApellidosUsuario();
        cargarEmailUsuario();
        cargarTelefonoUsuario();
        cargarDireccionUsuario();
        cargarSexoUsuario();
    }

    // Método para iniciar el controlador
    @FXML
    public void initialize() {
        dniField.setTextFormatter(LC.createTextFormatter(9));
    }

    // Método para cargar el DNI en el TextField
    private void cargarDNIUsuario() {
        dniField.setText(usuarioLogueado.getDNI().toUpperCase());
        Platform.runLater(() -> dniField.getParent().requestFocus()); // Quita el foco del TextField
    }

    // Método para cargar la Fecha en el TextField
    private void cargaFechaUsuario() {
        try {
            LocalDate fecha = LocalDate.parse(usuarioLogueado.getFechaDeNacimiento());
            fechaField.setValue(fecha);
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener la fecha de nacimiento, añádela de nuevo.");
        }
    }

    // Método para cargar el Nombre en el TextField
    private void cargarNombreUsuario() {
        try {
            nombreField.setText(usuarioLogueado.getNombre());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener el nombre, añádelo de nuevo.");
        }
    }

    // Método para cargar el Apellido en el TextField
    private void cargarApellidosUsuario() {
        try {
            apellidoField.setText(usuarioLogueado.getApellidos());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener los apellidos, añádelos de nuevo.");
        }
    }

    // Método para cargar el Email en el TextField
    private void cargarEmailUsuario() {
        try {
            emailField.setText(usuarioLogueado.getEmail());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener el email, añádelo de nuevo.");
        }
    }

    // Método para cargar el Telefono en el TextField
    private void cargarTelefonoUsuario() {
        try {
            telefonoField.setText(usuarioLogueado.getTelefono());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener el télefono, añádelo de nuevo.");
        }
    }

    // Método para cargar la Direccion en el TextField
    private void cargarDireccionUsuario() {
        try {
            direccionField.setText(usuarioLogueado.getDireccion());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener la dirección, añádela de nuevo.");
        }
    }

    // Método para cargar el Sexo en el Combobox
    private void cargarSexoUsuario() {
        try {
            sexoCombo.setValue(usuarioLogueado.getSexo());
        } catch (Exception e) {
            ALERT.showError("No se pudo obtener el sexo, añádelo de nuevo.");
        }
    }

    // Método para activar el botón que modifica los datos
    @FXML private void onModifyClick() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        //usuarioDAO.modify(usuarioLogueado, String.valueOf(oldPasswordField));
    }

}
