package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegisterViewController {

    @FXML
    private Button btnRegisterUser;

    @FXML
    private TextField registerNombre, registerApellidos, registerDNI, registerEmail, registerPassword, registerPasswordConfirmation, registerTelefono, registerDireccion;

    @FXML
    private DatePicker registerFechaNacimiento;

    private final Alertas alertas = new Alertas();

    @FXML
    protected void actionEvent(ActionEvent e) {
        if (e.getSource().equals(btnRegisterUser)) {
            // Obtener los datos de los campos
            String password = registerPassword.getText();
            String passwordConfirm = registerPasswordConfirmation.getText();

            // Comprobar que todos los campos están completos
            if (registerDNI.getText().isEmpty()) {
                alertas.showWarning("El campo DNI es obligatorio.");
                return;
            }

            if (registerNombre.getText().isEmpty()) {
                alertas.showWarning("El campo Nombre es obligatorio.");
                return;
            }

            if (registerApellidos.getText().isEmpty()) {
                alertas.showWarning("El campo Apellidos es obligatorio.");
                return;
            }

            if (registerEmail.getText().isEmpty()) {
                alertas.showWarning("El campo Email es obligatorio.");
                return;
            }

            if (password.isEmpty()) {
                alertas.showWarning("El campo Contraseña es obligatorio.");
                return;
            }

            if (passwordConfirm.isEmpty()) {
                alertas.showWarning("El campo Confirmar Contraseña es obligatorio.");
                return;
            }

            if (registerTelefono.getText().isEmpty()) {
                alertas.showWarning("El campo Teléfono es obligatorio.");
                return;
            }

            if (registerDireccion.getText().isEmpty()) {
                alertas.showWarning("El campo Dirección es obligatorio.");
                return;
            }

            if (registerFechaNacimiento.getValue() == null) {
                alertas.showWarning("El campo Fecha de Nacimiento es obligatorio.");
                return;
            }

            // Verificar si las contraseñas coinciden
            if (!password.equals(passwordConfirm)) {
                alertas.showWarning("Las contraseñas no coinciden.");
                return;
            }

            // Obtener la fecha y hora actuales en formato DATETIME de MySQL
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String fechaDeRegistro = now.format(formatter);

            // Crear el objeto Usuario con la fecha de registro
            Usuario usuario = new Usuario(
                    registerDNI.getText(),
                    registerNombre.getText(),
                    registerApellidos.getText(),
                    registerEmail.getText(),
                    password,
                    registerTelefono.getText(),
                    registerDireccion.getText(),
                    registerFechaNacimiento.getValue().toString(),
                    fechaDeRegistro  // Establecer la fecha de registro
            );

            // Intentar registrar el usuario
            UsuarioDAO dao = new UsuarioDAO();
            boolean registrado = dao.register(usuario);

            if (registrado) {
                alertas.showInformation("Usuario registrado exitosamente.");
                // Aquí puedes cambiar de vista o mostrar mensaje de bienvenida
            } else {
                alertas.showWarning("Error al registrar usuario.");
            }
        }
    }
}
