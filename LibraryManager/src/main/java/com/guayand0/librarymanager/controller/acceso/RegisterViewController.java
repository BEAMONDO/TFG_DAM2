package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegisterViewController implements Initializable {

    @FXML
    private Button btnRegisterUser;

    @FXML
    private TextField registerNombre, registerApellidos, registerDNI, registerEmail, registerPassword, registerPasswordConfirmation, registerTelefono, registerDireccion;

    @FXML
    private DatePicker registerFechaNacimiento;

    @FXML
    private PasswordField registerPasswordMask, registerPasswordConfirmationMask;

    @FXML
    private CheckBox registerPasswordOpen;

    private final Alertas alertas = new Alertas();

    @FXML
    protected void actionEvent(ActionEvent e) {
        if (e.getSource().equals(btnRegisterUser)) {
            // Obtener los datos de los campos
            String password = registerPassword.getText();
            String passwordConfirm = registerPasswordConfirmation.getText();

            // Validar campos
            if (!validarCampos(password, passwordConfirm)) {
                return;
            }

            // Verificar si las contraseñas coinciden
            if (!verificarContrasenas(password, passwordConfirm)) {
                return;
            }

            // Obtener la fecha y hora actuales
            String fechaDeRegistro = obtenerFechaDeRegistro();

            // Crear el objeto Usuario
            Usuario usuario = new Usuario(
                    registerDNI.getText(),
                    registerNombre.getText(),
                    registerApellidos.getText(),
                    registerEmail.getText(),
                    password,
                    registerTelefono.getText(),
                    registerDireccion.getText(),
                    registerFechaNacimiento.getValue().toString(),
                    fechaDeRegistro
            );

            // Intentar registrar el usuario
            UsuarioDAO dao = new UsuarioDAO();
            boolean registrado = dao.register(usuario);

            if (registrado) {
                alertas.showInformation("Usuario registrado exitosamente.");
            } else {
                alertas.showWarning("Error al registrar usuario.");
            }
        }
    }

    // Método para validar los campos
    private boolean validarCampos(String password, String passwordConfirm) {
        if (registerDNI.getText().isEmpty()) {
            alertas.showWarning("El campo DNI es obligatorio.");
            return false;
        }
        if (registerNombre.getText().isEmpty()) {
            alertas.showWarning("El campo Nombre es obligatorio.");
            return false;
        }
        if (registerApellidos.getText().isEmpty()) {
            alertas.showWarning("El campo Apellidos es obligatorio.");
            return false;
        }
        if (registerEmail.getText().isEmpty()) {
            alertas.showWarning("El campo Email es obligatorio.");
            return false;
        }
        if (password.isEmpty()) {
            alertas.showWarning("El campo Contraseña es obligatorio.");
            return false;
        }
        if (passwordConfirm.isEmpty()) {
            alertas.showWarning("El campo Confirmar Contraseña es obligatorio.");
            return false;
        }
        if (registerTelefono.getText().isEmpty()) {
            alertas.showWarning("El campo Teléfono es obligatorio.");
            return false;
        }
        if (registerDireccion.getText().isEmpty()) {
            alertas.showWarning("El campo Dirección es obligatorio.");
            return false;
        }
        if (registerFechaNacimiento.getValue() == null) {
            alertas.showWarning("El campo Fecha de Nacimiento es obligatorio.");
            return false;
        }
        return true;
    }

    // Método para verificar si las contraseñas coinciden
    private boolean verificarContrasenas(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            alertas.showWarning("Las contraseñas no coinciden.");
            return false;
        }
        return true;
    }

    // Método para obtener la fecha y hora actuales en formato DATETIME de MySQL
    private String obtenerFechaDeRegistro() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maskPassword(registerPasswordMask, registerPassword, registerPasswordOpen);
        maskPassword(registerPasswordConfirmationMask, registerPasswordConfirmation, registerPasswordOpen);

        registerDNI.setTextFormatter(createTextFormatter(9));
        registerNombre.setTextFormatter(createTextFormatter(255));
        registerApellidos.setTextFormatter(createTextFormatter(255));
        registerEmail.setTextFormatter(createTextFormatter(255));
        registerPassword.setTextFormatter(createTextFormatter(255));
        registerTelefono.setTextFormatter(createTextFormatter(9));
        registerDireccion.setTextFormatter(createTextFormatter(255));
    }

    private TextFormatter<String> createTextFormatter(int maxLength) {
        // Crear un filtro de texto para restringir la longitud máxima de caracteres
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                change.setText("");  // Ignorar el cambio si el texto excede el límite
                change.setCaretPosition(change.getCaretPosition() - 1); // Ajustar la posición del cursor
            }
            return change;
        });
    }

    public void maskPassword(PasswordField pass, TextField text, CheckBox check) {

        text.setVisible(false);
        text.setManaged(false);

        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());

        text.textProperty().bindBidirectional(pass.textProperty());
    }
}
