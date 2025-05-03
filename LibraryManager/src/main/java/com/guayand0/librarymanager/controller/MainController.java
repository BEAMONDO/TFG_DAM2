package com.guayand0.librarymanager.controller;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MainController {

    private final Alertas ALERT = new Alertas();
    private final Main main = new Main();

    @FXML private Label usuario, permiso;

    // Menú central

    @FXML private void onLibrosClick() {}
    @FXML private void onAutoresClick() {}
    @FXML private void onCategoriasClick() {}
    @FXML private void onEditorialesClick() {}
    @FXML private void onIdiomasClick() {}
    @FXML private void onUsuariosClick() {}
    @FXML private void onPrestamosClick() {}
    @FXML private void onDevolucionesClick() {}
    @FXML private void onInformesClick() {}

    // Menu izquierdo

    @FXML private void onSessionClose() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        main.openAccessWindow();
    }

    public void setUsuarioTexto(String texto) {
        usuario.setText("Nombre: " + texto);
    }
    public void setPermisoTexto(String texto) {
        permiso.setText("Permiso: " + texto);
    }

    // Menú derecho

    @FXML private void onMenuClick() {}

    // Menú superior

    @FXML private void onSalir() {
        Platform.exit();
    }

    @FXML private void onAcercaDe() {
        ALERT.showInformation("LibraryManager v1.0.1\nDesarrollado por David Beamonde.");
    }

    @FXML private void wiki() {
        boolean confirmar = ALERT.showConfirmation("¿Desea abrir la página de la wiki con información sobre el funcionamiento de la aplicación?");
        if (confirmar) {
            ALERT.showInformation("La página web todavia no está creada.");
            /*try {
                Desktop.getDesktop().browse(new URI(""));
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
            }
            ALERT.showInformation("Se ha abierto la página de la wiki en su navegador predeterminado.");*/
        }
    }

    @FXML private void reportarProblema() {
        boolean confirmar = ALERT.showConfirmation("¿Desea abrir la página web para reportar un problema o fallo de la aplicación?");
        if (confirmar) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/BEAMONDO/TFG_DAM2/issues/new"));
                ALERT.showInformation("Se ha abierto la página para reportar problemas en su navegador.");
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
                ALERT.showError("Ha ocurrido un error al intentar abrir la página.");
            }
        }
    }

}