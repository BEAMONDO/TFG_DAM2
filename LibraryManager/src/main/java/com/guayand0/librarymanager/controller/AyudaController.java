package com.guayand0.librarymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.Desktop;
import java.net.URI;
import java.io.IOException;

public class AyudaController {

    private static final String URL_SOPORTE = "https://github.com/BEAMONDO/TFG_DAM2/issues/new";

    @FXML
    private void abrirPaginaSoporte(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI(URL_SOPORTE));
        } catch (IOException | java.net.URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
