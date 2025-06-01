package com.guayand0.librarymanager.controller.usuarios.user;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();
    private final EncriptarContrasena EC = new EncriptarContrasena();
    private final MostrarContrasena MC = new MostrarContrasena();
    private final ComprobarDNI CDNI = new ComprobarDNI();

    private Usuario usuarioLogueado;

    @FXML private TextField dniField, nombreField, apellidoField, emailField, telefonoField, direccionField;
    @FXML private TextField oldPasswordField, newPasswordField;
    @FXML private PasswordField oldPasswordFieldMask, newPasswordFieldMask;
    @FXML private DatePicker fechaField;
    @FXML private ComboBox<String> sexoCombo;
    @FXML private CheckBox passwordCheckHide;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
        new Thread(this::cargarDatosUsuario).start();
    }

    @FXML public void initialize() {
        aplicarMascaras();
        aplicarLimitesCaracteres();
    }

    private void aplicarMascaras() {
        MC.maskPassword(oldPasswordFieldMask, oldPasswordField, passwordCheckHide);
        MC.maskPassword(newPasswordFieldMask, newPasswordField, passwordCheckHide);
    }

    private void aplicarLimitesCaracteres() {
        dniField.setTextFormatter(LC.createTextFormatter(9));
        nombreField.setTextFormatter(LC.createTextFormatter(255));
        apellidoField.setTextFormatter(LC.createTextFormatter(255));
        emailField.setTextFormatter(LC.createTextFormatter(255));
        telefonoField.setTextFormatter(LC.createTextFormatter(9));
        direccionField.setTextFormatter(LC.createTextFormatter(255));
        oldPasswordField.setTextFormatter(LC.createTextFormatter(255));
        newPasswordField.setTextFormatter(LC.createTextFormatter(255));
    }

    private void cargarDatosUsuario() {
        try {
            dniField.setText(usuarioLogueado.getDNI().toUpperCase());
            nombreField.setText(usuarioLogueado.getNombre());
            apellidoField.setText(usuarioLogueado.getApellidos());
            emailField.setText(usuarioLogueado.getEmail());
            telefonoField.setText(usuarioLogueado.getTelefono());
            direccionField.setText(usuarioLogueado.getDireccion());
            sexoCombo.setValue(usuarioLogueado.getSexo());
            fechaField.setValue(LocalDate.parse(usuarioLogueado.getFechaDeNacimiento()));
        } catch (Exception e) {
            ALERT.showError("Error al cargar los datos del usuario.");
        }

        Platform.runLater(() -> dniField.getParent().requestFocus());
    }

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String dni = dniField.getText();
        String nombre = nombreField.getText();
        String apellidos = apellidoField.getText();
        String email = emailField.getText();
        String telefono = telefonoField.getText();
        String direccion = direccionField.getText();
        String fechaNacimiento = fechaField.getValue().toString();
        String sexo = sexoCombo.getValue();
        String permiso = usuarioLogueado.getPermiso();

        String contrasena = oldPasswordField.getText();
        String nuevaContrasena = newPasswordField.getText().isEmpty() ? contrasena : newPasswordField.getText();

        String[] datos = {
                dni, nombre, apellidos, email, contrasena, nuevaContrasena,
                telefono, direccion, fechaNacimiento, sexo, permiso
        };

        boolean actualizado = usuarioDAO.modify(datos, EC.encriptar(contrasena), permiso);

        if (actualizado) {
            usuarioLogueado.setNombre(nombre);
            usuarioLogueado.setApellidos(apellidos);
            usuarioLogueado.setEmail(email);
            usuarioLogueado.setContrasena(nuevaContrasena);
            usuarioLogueado.setTelefono(telefono);
            usuarioLogueado.setDireccion(direccion);
            usuarioLogueado.setFechaDeNacimiento(fechaNacimiento);
            usuarioLogueado.setSexo(sexo);
            usuarioLogueado.setPermiso(permiso);

            ALERT.showInformation("Datos actualizados correctamente.");
            oldPasswordField.clear();
            newPasswordField.clear();
        }
    }

    private boolean validarCampos() {
        String dni = dniField.getText();
        String email = emailField.getText();
        String direccion = direccionField.getText();

        if (dni.isEmpty()) {
            ALERT.showWarning("DNI inválido.");
            return false;
        }

        if (!CDNI.validarDNI(dni)) {
            ALERT.showWarning("El DNI está mal formado.");
            return false;
        }

        if (nombreField.getText().isEmpty() || apellidoField.getText().isEmpty()) {
            ALERT.showWarning("Nombre y apellidos son obligatorios.");
            return false;
        }

        if (email.isEmpty() || !email.matches("^[\\w+_.-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            ALERT.showWarning("Formato de email inválido.");
            return false;
        }

        if (telefonoField.getText().isEmpty()) {
            ALERT.showWarning("El teléfono es obligatorio.");
            return false;
        }

        if (direccion.isEmpty()) {
            ALERT.showWarning("La dirección es obligatoria.");
            return false;
        }

        if (fechaField.getValue() == null) {
            ALERT.showWarning("La fecha de nacimiento es obligatoria.");
            return false;
        }

        if (sexoCombo.getValue() == null) {
            ALERT.showWarning("El sexo es obligatorio.");
            return false;
        }

        return true;
    }

}
