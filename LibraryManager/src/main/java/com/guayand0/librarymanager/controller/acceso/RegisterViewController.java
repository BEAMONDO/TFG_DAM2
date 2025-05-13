package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.MostrarContrasena;
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
    private ComboBox sexo;

    @FXML
    private DatePicker registerFechaNacimiento;

    @FXML
    private PasswordField registerPasswordMask, registerPasswordConfirmationMask;

    @FXML
    private CheckBox registerPasswordOpen;

    private final Alertas ALERT = new Alertas();
    private final MostrarContrasena MC = new MostrarContrasena();

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

            String permiso = "Usuario";

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
                    fechaDeRegistro,
                    permiso,
                    sexo.getValue().toString()
            );

            // Intentar registrar el usuario
            UsuarioDAO dao = new UsuarioDAO();
            boolean registrado = dao.register(usuario);

            if (registrado) {
                ALERT.showInformation("Usuario registrado exitosamente.");

                registerDNI.clear();
                registerNombre.clear();
                registerApellidos.clear();
                registerEmail.clear();
                registerPassword.clear();
                registerPasswordConfirmation.clear();
                registerPasswordOpen.setSelected(false);
                registerTelefono.clear();
                registerDireccion.clear();
                registerFechaNacimiento.setValue(null);
                sexo.setValue(null);

            } else {
                ALERT.showWarning("Error al registrar usuario.");
            }
        }
    }

    private boolean validarCampos(String password, String passwordConfirm) {
        String dni = registerDNI.getText();
        String email = registerEmail.getText();
        String direccion = registerDireccion.getText();

        if (dni.isEmpty()) {
            ALERT.showWarning("El campo DNI es obligatorio.");
            return false;
        }
        if (!dni.matches("^[0-9]{8}[A-Za-z]$")) {
            ALERT.showWarning("El DNI no es válido. Debe tener 8 números seguidos de una letra.");
            return false;
        } else {
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int numero = Integer.parseInt(dni.substring(0, 8));
            char letraCorrecta = letras.charAt(numero % 23);
            char letraIngresada = Character.toUpperCase(dni.charAt(8));

            if (letraCorrecta != letraIngresada) {
                ALERT.showWarning("La letra del DNI no coincide con los números ingresados.");
                return false;
            }
        }

        if (registerNombre.getText().isEmpty()) {
            ALERT.showWarning("El campo Nombre es obligatorio.");
            return false;
        }
        if (registerApellidos.getText().isEmpty()) {
            ALERT.showWarning("El campo Apellidos es obligatorio.");
            return false;
        }

        if (email.isEmpty()) {
            ALERT.showWarning("El campo Email es obligatorio.");
            return false;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            ALERT.showWarning("El Email no tiene un formato válido. Debe contener '@', un dominio y una extensión de al menos 2 letras.");
            return false;
        }

        if (password.isEmpty()) {
            ALERT.showWarning("El campo Contraseña es obligatorio.");
            return false;
        }
        if (passwordConfirm.isEmpty()) {
            ALERT.showWarning("El campo Confirmar Contraseña es obligatorio.");
            return false;
        }

        if (registerTelefono.getText().isEmpty()) {
            ALERT.showWarning("El campo Teléfono es obligatorio.");
            return false;
        }

        if (direccion.isEmpty()) {
            ALERT.showWarning("El campo Dirección es obligatorio.");
            return false;
        }
/*        if (!direccion.matches("^[\\p{L}0-9 ',\\.]+,\\s*\\d+(-bis)?(\\s*\\d{1,2}[A-Za-z]?)?$")) {
            ALERT.showWarning("La direccion tiene que tener este formato 'calle, numero(-bis) (piso(letra))'. Ej. 'Calle Santos, 33'. Ej. 'Calle Cantantes, 12-bis 2º'.");
            return false;
        }
*/
        if (registerFechaNacimiento.getValue() == null) {
            ALERT.showWarning("El campo Fecha de Nacimiento es obligatorio.");
            return false;
        }

        if (sexo.getValue() == null) {
            ALERT.showWarning("El campo Sexo es obligatorio.");
            return false;
        }

        return true;
    }

    // Método para verificar si las contraseñas coinciden
    private boolean verificarContrasenas(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            ALERT.showWarning("Las contraseñas no coinciden.");
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
        sexo.getItems().addAll("Hombre", "Mujer", "Otro");

        MC.maskPassword(registerPasswordMask, registerPassword, registerPasswordOpen);
        MC.maskPassword(registerPasswordConfirmationMask, registerPasswordConfirmation, registerPasswordOpen);

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
}
