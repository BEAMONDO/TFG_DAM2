package com.guayand0.librarymanager.controller;

import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EditorialesController {

    private final Ventanas VENTANA = new Ventanas();

    @FXML
    private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private void onBackClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();

        VENTANA.mainWindow(usuarioLogueado);
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

}