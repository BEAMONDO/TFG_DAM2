package com.guayand0.librarymanager.controller.usuarios.admin;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import com.guayand0.librarymanager.utils.MostrarContrasena;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();
    private final MostrarContrasena MC = new MostrarContrasena();

    private Usuario usuarioLogueado;
    private String contrasenaOriginal;

    @FXML private TextField nombreField, apellidoField, emailField, telefonoField, direccionField;
    @FXML private TextField passwordField, confirmPasswordField;
    @FXML private PasswordField passwordFieldMask, confirmPasswordFieldMask;
    @FXML private CheckBox passwordCheckHide, isAdmin;
    @FXML private DatePicker fechaField;
    @FXML private ComboBox<String> dniCombo, sexoCombo;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarMascaras();
        aplicarLimitesCaracteres();

        cargarDNIUsuarios();
        dniCombo.setOnAction(event -> cargarDatosUsuario());
    }

    private void aplicarMascaras() {
        MC.maskPassword(passwordFieldMask, passwordField, passwordCheckHide);
        MC.maskPassword(confirmPasswordFieldMask, confirmPasswordField, passwordCheckHide);
    }

    private void aplicarLimitesCaracteres() {
        nombreField.setTextFormatter(LC.createTextFormatter(255));
        apellidoField.setTextFormatter(LC.createTextFormatter(255));
        emailField.setTextFormatter(LC.createTextFormatter(255));
        telefonoField.setTextFormatter(LC.createTextFormatter(9));
        direccionField.setTextFormatter(LC.createTextFormatter(255));
        passwordField.setTextFormatter(LC.createTextFormatter(255));
        confirmPasswordField.setTextFormatter(LC.createTextFormatter(255));
    }

    private void cargarDNIUsuarios() {
        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        dniCombo.getItems().clear();

        for (Usuario usuario : usuarios) {
            dniCombo.getItems().add(usuario.getDNI());
        }

        Platform.runLater(() -> dniCombo.getParent().requestFocus());
    }

    private void cargarDatosUsuario() {
        String dniSeleccionado = dniCombo.getValue();
        if (dniSeleccionado == null) return;

        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.getDNI().equals(dniSeleccionado)) {
                contrasenaOriginal = usuario.getContrasena();

                nombreField.setText(usuario.getNombre());
                apellidoField.setText(usuario.getApellidos());
                emailField.setText(usuario.getEmail());
                telefonoField.setText(usuario.getTelefono());
                direccionField.setText(usuario.getDireccion());
                passwordField.clear();
                confirmPasswordField.clear();

                try {
                    fechaField.setValue(LocalDate.parse(usuario.getFechaDeNacimiento()));
                } catch (Exception e) {
                    fechaField.setValue(null);
                }

                sexoCombo.setValue(usuario.getSexo());
                isAdmin.setSelected("Administrador".equals(usuario.getPermiso()));
                break;
            }
        }
    }

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String dni = dniCombo.getValue();
        String nombre = nombreField.getText();
        String apellidos = apellidoField.getText();
        String email = emailField.getText();
        String contrasena = passwordField.getText();
        String confirmarContrasena = confirmPasswordField.getText();

        if (!contrasena.equals(confirmarContrasena)) {
            ALERT.showError("Las contraseñas no coinciden.");
            return;
        }

        String telefono = telefonoField.getText();
        String direccion = direccionField.getText();
        String fechaNacimiento = fechaField.getValue().toString();
        String sexo = sexoCombo.getValue();
        String permiso = isAdmin.isSelected() ? "Administrador" : "Usuario";

        String[] datos = {
                dni, nombre, apellidos, email, contrasena,
                confirmarContrasena, telefono, direccion,
                fechaNacimiento, sexo, permiso
        };

        boolean actualizado = usuarioDAO.modify(datos, contrasenaOriginal, usuarioLogueado.getPermiso());

        if (actualizado) {
            ALERT.showInformation("Datos actualizados correctamente.");

            dniCombo.setValue(null);
            nombreField.clear();
            apellidoField.clear();
            emailField.clear();
            telefonoField.clear();
            direccionField.clear();
            passwordField.clear();
            confirmPasswordField.clear();
            fechaField.setValue(null);
            sexoCombo.setValue(null);
            isAdmin.setSelected(false);
        }
    }

    private boolean validarCampos() {
        String dni = dniCombo.getValue();
        String email = emailField.getText();
        String direccion = direccionField.getText();

        if (dni == null) {
            ALERT.showWarning("El campo DNI es obligatorio.");
            return false;
        }

        if (!dni.matches("^[0-9]{8}[A-Za-z]$")) {
            ALERT.showWarning("DNI inválido. Debe tener 8 dígitos seguidos de una letra.");
            return false;
        }

        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        char letraIngresada = Character.toUpperCase(dni.charAt(8));

        if (letraCorrecta != letraIngresada) {
            ALERT.showWarning("La letra del DNI no coincide con los números.");
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

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            ALERT.showWarning("Formato de email inválido.");
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
            ALERT.showWarning("La Fecha de Nacimiento es obligatoria.");
            return false;
        }

        if (sexoCombo.getValue() == null) {
            ALERT.showWarning("El campo Sexo es obligatorio.");
            return false;
        }

        return true;
    }
}
