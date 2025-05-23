package com.guayand0.librarymanager.controller.usuarios.admin;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    private final LimiteCaracteres LC = new LimiteCaracteres();
    private final MostrarContrasena MC = new MostrarContrasena();
    private final ComprobarDNI CDNI = new ComprobarDNI();
    private final ComprobarEmail CE = new ComprobarEmail();

    private Usuario usuarioLogueado;

    @FXML private TextField dniField, nombreField, apellidoField, emailField, telefonoField, direccionField;
    @FXML private TextField passwordField, confirmPasswordField;
    @FXML private PasswordField passwordFieldMask, confirmPasswordFieldMask;
    @FXML private CheckBox passwordCheckHide, isAdmin;
    @FXML private DatePicker fechaField;
    @FXML private ComboBox<String> sexoCombo;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarMascaras();
        aplicarLimitesCaracteres();
    }

    private void aplicarMascaras() {
        MC.maskPassword(passwordFieldMask, passwordField, passwordCheckHide);
        MC.maskPassword(confirmPasswordFieldMask, confirmPasswordField, passwordCheckHide);
    }

    private void aplicarLimitesCaracteres() {
        dniField.setTextFormatter(LC.createTextFormatter(9));
        nombreField.setTextFormatter(LC.createTextFormatter(255));
        apellidoField.setTextFormatter(LC.createTextFormatter(255));
        emailField.setTextFormatter(LC.createTextFormatter(255));
        passwordField.setTextFormatter(LC.createTextFormatter(255));
        confirmPasswordField.setTextFormatter(LC.createTextFormatter(255));
        telefonoField.setTextFormatter(LC.createTextFormatter(9));
        direccionField.setTextFormatter(LC.createTextFormatter(255));
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String dni = dniField.getText();
        String nombre = nombreField.getText();
        String apellidos = apellidoField.getText();
        String email = emailField.getText();
        String contrasena = passwordField.getText();
        String confirmar = confirmPasswordField.getText();

        if (!contrasena.equals(confirmar)) {
            ALERT.showError("Las contraseñas no coinciden.");
            return;
        }

        String telefono = telefonoField.getText();
        String direccion = direccionField.getText();
        String fechaNacimiento = fechaField.getValue().toString();
        String sexo = sexoCombo.getValue();
        String permiso = isAdmin.isSelected() ? "Administrador" : "Usuario";
        String fechaRegistro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Usuario usuario = new Usuario(
                dni, nombre, apellidos, email, contrasena,
                telefono, direccion, fechaNacimiento, fechaRegistro,
                permiso, sexo
        );

        UsuarioDAO dao = new UsuarioDAO();
        boolean registrado = dao.register(usuario);

        if (registrado) {
            ALERT.showInformation("Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar el usuario.");
        }
    }

    private void limpiarCampos() {
        dniField.clear();
        nombreField.clear();
        apellidoField.clear();
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        telefonoField.clear();
        direccionField.clear();
        fechaField.setValue(null);
        sexoCombo.setValue(null);
        passwordCheckHide.setSelected(false);
        isAdmin.setSelected(false);
    }

    private boolean validarCampos() {
        String dni = dniField.getText();
        String email = emailField.getText();
        String direccion = direccionField.getText();

        if (dni.isEmpty()) {
            ALERT.showWarning("El campo DNI es obligatorio.");
            return false;
        }

        if (!CDNI.validarDNI(dni)) {
            ALERT.showWarning("El DNI está mal formado.");
            return false;
        }

        if (nombreField.getText().isEmpty()) {
            ALERT.showWarning("El campo Nombre es obligatorio.");
            return false;
        }

        if (apellidoField.getText().isEmpty()) {
            ALERT.showWarning("El campo Apellidos es obligatorio.");
            return false;
        }

        if (email.isEmpty()) {
            ALERT.showWarning("El campo Email es obligatorio.");
            return false;
        }

        if (!CE.validarEmail(email)) {
            ALERT.showWarning("El Email está mal formado.");
            return false;
        }

        if (passwordField.getText().isEmpty()) {
            ALERT.showWarning("La contraseña es obligatoria.");
            return false;
        }

        if (confirmPasswordField.getText().isEmpty()) {
            ALERT.showWarning("Confirmar contraseña es obligatorio.");
            return false;
        }

        if (telefonoField.getText().isEmpty()) {
            ALERT.showWarning("El campo Teléfono es obligatorio.");
            return false;
        }

        if (direccion.isEmpty()) {
            ALERT.showWarning("El campo Dirección es obligatorio.");
            return false;
        }

        if (fechaField.getValue() == null) {
            ALERT.showWarning("Fecha de nacimiento es obligatoria.");
            return false;
        }

        if (sexoCombo.getValue() == null) {
            ALERT.showWarning("El campo Sexo es obligatorio.");
            return false;
        }

        return true;
    }

    private boolean validarLetraDNI(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        char letraIngresada = Character.toUpperCase(dni.charAt(8));
        return letraCorrecta == letraIngresada;
    }
}
