package com.guayand0.librarymanager.controller.main;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MainController {

    private final Alertas ALERT = new Alertas();
    private final Ventanas VENTANA = new Ventanas();

    @FXML private Label usuario, permiso;
    private Usuario usuarioLogueado;

    // Menú central

    @FXML private void onLibrosClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.librosWindow(usuarioLogueado);
    }

    @FXML private void onAutoresClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.autoresWindow(usuarioLogueado);
    }

    @FXML private void onCategoriasClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.categoriasWindow(usuarioLogueado);
    }

    @FXML private void onEditorialesClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.editorialesWindow(usuarioLogueado);
    }

    @FXML private void onIdiomasClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.idiomasWindow(usuarioLogueado);
    }

    @FXML private void onUsuariosClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.usuariosWindow(usuarioLogueado);
    }

    @FXML private void onPrestamosClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.prestamosWindow(usuarioLogueado);
    }

    @FXML private void onDevolucionesClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.devolucionesWindow(usuarioLogueado);
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    // Menu izquierdo

    @FXML private void onSessionClose() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.accessWindow();
    }

    public void setUsuarioTexto(String texto) {
        usuario.setText("Nombre: " + texto);
    }
    public void setPermisoTexto(String texto) {
        permiso.setText("Permiso: " + texto);
    }

    // Menú superior

    @FXML private void onSalir() {
        Platform.exit();
    }

    @FXML private void onAcercaDe() {
        ALERT.showInformation("Library Manager v1.0.1\nDesarrollado por David Beamonde.");
    }

    @FXML private void wiki() {
        boolean confirmar = ALERT.showConfirmation("¿Desea abrir la página de la wiki con información sobre el funcionamiento de la aplicación?");
        if (confirmar) {
            //ALERT.showInformation("La página web todavia no está creada.");
            try {
                Desktop.getDesktop().browse(new URI("https://www.spaincraft.xyz/web/guia_de_uso"));
            } catch (IOException | java.net.URISyntaxException e) {
                e.printStackTrace();
            }
            ALERT.showInformation("Se ha abierto la página de la wiki en su navegador predeterminado.");
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
