package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.model.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {

    @FXML
    private Button btnLoginUser;

    @FXML
    private TextField loginDNI, loginEmail, loginPassword;

    private final Alertas alertas = new Alertas();

    @FXML
    protected void actionEvent(ActionEvent e) {
        if (e.getSource().equals(btnLoginUser)) {
            // Obtener los datos de los campos
            String dni = loginDNI.getText();
            String email = loginEmail.getText();
            String password = loginPassword.getText();

            // Comprobar si todos los campos tienen datos
            if (dni.isEmpty()) {
                alertas.showWarning("El campo DNI es obligatorio.");
                return;
            }

            if (email.isEmpty()) {
                alertas.showWarning("El campo Email es obligatorio.");
                return;
            }

            if (password.isEmpty()) {
                alertas.showWarning("El campo Contraseña es obligatorio.");
                return;
            }

            // Intentar hacer login
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.login(dni, email, password);

            if (usuario != null) {
                alertas.showInformation("Login Exitoso");

                // Cerrar la ventana de login
                Stage stage = (Stage) btnLoginUser.getScene().getWindow();
                stage.close();

                // Abrir la nueva ventana principal
                openMainWindow();
            } else {
                // Login fallido
                alertas.showWarning("DNI, Email o Contraseña no son correctos.");
            }
        }
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
