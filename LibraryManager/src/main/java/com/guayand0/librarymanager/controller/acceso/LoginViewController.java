package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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

    private final Alertas alertas = new Alertas();

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
                alertas.showInformation("Has iniciado sesión como: " + usuario.getNombre() + " " + usuario.getApellidos());
                ((Stage) btnLoginUser.getScene().getWindow()).close();
                openMainWindow();
            } else {
                alertas.showWarning("Credenciales incorrectas.");
            }
        }
    }

    // Método para validar los campos de login
    private boolean validarCamposLogin(String dniEmail, String password) {
        if (dniEmail.isEmpty()) {
            alertas.showWarning("El campo DNI o Email es obligatorio.");
            return false;
        }
        if (password.isEmpty()) {
            alertas.showWarning("El campo Contraseña es obligatorio.");
            return false;
        }
        return true;
    }

    // Método para intentar iniciar sesión
    private Usuario iniciarSesion(String dniEmail, String password) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.login(dniEmail, password);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        maskPassword(loginPasswordMask, loginPassword, loginPasswordOpen);
    }

    public void maskPassword(PasswordField pass, TextField text, CheckBox check) {

        text.setVisible(false);
        text.setManaged(false);

        text.managedProperty().bind(check.selectedProperty());
        text.visibleProperty().bind(check.selectedProperty());

        text.textProperty().bindBidirectional(pass.textProperty());
    }


    // Método para abrir la ventana principal
    public void openMainWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/guayand0/librarymanager/main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
            Stage stage = new Stage();
            stage.setTitle("LibraryManager - Main Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertas.showError("Error al cargar la ventana principal.");
        }
    }
}
