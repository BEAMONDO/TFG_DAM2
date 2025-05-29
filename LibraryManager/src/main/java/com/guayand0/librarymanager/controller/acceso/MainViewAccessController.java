package com.guayand0.librarymanager.controller.acceso;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewAccessController implements Initializable {

    private final Alertas ALERT = new Alertas();

    @FXML private Button  btnLogin, btnRegister;
    @FXML private StackPane containerSessionData;
    @FXML private HBox loginForm, registerForm;

    @FXML protected void actionEvent(ActionEvent e) {
        Object evt = e.getSource();

        if (evt.equals(btnLogin)) {
            loginForm.setVisible(true);
            registerForm.setVisible(false);
            btnLogin.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
            btnRegister.setStyle("-fx-font-size: 14px; -fx-font-weight: normal;");
        } else if (evt.equals(btnRegister)) {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
            btnLogin.setStyle("-fx-font-size: 14px; -fx-font-weight: normal;");
            btnRegister.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loginForm = loadForm("acceso/login-view.fxml");
            registerForm = loadForm("acceso/register-view.fxml");

            containerSessionData.getChildren().addAll(loginForm, registerForm);

            loginForm.setVisible(true);
            registerForm.setVisible(false);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private HBox loadForm(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        HBox data = fxmlLoader.load();
        return data;
    }

    // Menú superior

    @FXML private void onSalir() {
        Platform.exit();
    }

    @FXML private void onAcercaDe() {
        ALERT.showInformation("LibraryManager v1.0.1\nDesarrollado por David Beamonde.");
    }
}
