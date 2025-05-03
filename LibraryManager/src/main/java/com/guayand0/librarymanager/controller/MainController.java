package com.guayand0.librarymanager.controller;

import com.guayand0.librarymanager.utils.Alertas;
import javafx.application.Platform;
import javafx.fxml.FXML;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MainController {

    Alertas alertas = new Alertas();

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

    // Menú derecho

    @FXML private void onMenuClick() {}

    // Menú superior

    @FXML
    private void onSalir() {
        Platform.exit();
    }

    @FXML
    private void onAcercaDe() {
        alertas.showInformation("LibraryManager v1.0.1\nDesarrollado por David Beamonde.");
    }

    @FXML
    private void wiki() {
        boolean confirmar = alertas.showConfirmation("¿Desea abrir la página de la wiki con información sobre el funcionamiento de la aplicación?");
        if (confirmar) {
            alertas.showInformation("La página web todavia no está creada.");
            /*try {
                Desktop.getDesktop().browse(new URI(""));
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
            }
            alertas.showInformation("Se ha abierto la página de la wiki en su navegador predeterminado.");*/
        }
    }

    @FXML
    private void reportarProblema() {
        boolean confirmar = alertas.showConfirmation("¿Desea abrir la página web para reportar un problema o fallo de la aplicación?");
        if (confirmar) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/BEAMONDO/TFG_DAM2/issues/new"));
                alertas.showInformation("Se ha abierto la página para reportar problemas en su navegador.");
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
                alertas.showError("Ha ocurrido un error al intentar abrir la página.");
            }
        }
    }
}