package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.controller.MainController;
import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.MostrarContrasena;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private Button btnLoginUser;

    @FXML
    private TextField loginDNI_Email, loginPassword;

    @FXML
    private PasswordField loginPasswordMask;

    @FXML
    private CheckBox loginPasswordOpen;

    private final Alertas ALERT = new Alertas();
    private final Ventanas VENTANA = new Ventanas();
    private final MostrarContrasena MC = new MostrarContrasena();

    @FXML
    protected void actionEvent(ActionEvent e) {
        if (e.getSource().equals(btnLoginUser)) {
            String dniEmail = loginDNI_Email.getText();
            String password = loginPassword.getText();

            // Validar campos
            if (!validarCamposLogin(dniEmail, password)) {
                return;
            }

            // Intentar iniciar sesión
            Usuario usuario = iniciarSesion(dniEmail, password);

            if (usuario != null) {
                //ALERT.showInformation("Has iniciado sesión como: " + usuario.getNombre() + " " + usuario.getApellidos());
                ((Stage) btnLoginUser.getScene().getWindow()).close();
                VENTANA.mainWindow(usuario);
            } else {
                ALERT.showWarning("Credenciales incorrectas.");
            }
        }
    }

    // Metodo para validar los campos de login
    private boolean validarCamposLogin(String dniEmail, String password) {
        if (dniEmail.isEmpty()) {
            ALERT.showWarning("El campo DNI o Email es obligatorio.");
            return false;
        }

        if (password.isEmpty()) {
            ALERT.showWarning("El campo Contraseña es obligatorio.");
            return false;
        }
        return true;
    }

    // Metodo para intentar iniciar sesión
    private Usuario iniciarSesion(String dniEmail, String password) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.login(dniEmail, password);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MC.maskPassword(loginPasswordMask, loginPassword, loginPasswordOpen);
    }
}
