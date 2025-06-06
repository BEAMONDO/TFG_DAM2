package com.guayand0.librarymanager.controller.devoluciones;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DevolucionesController {

    private final Ventanas VENTANA = new Ventanas();

    @FXML private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private StackPane containerData;
    @FXML private VBox registrar, modificar;

    @FXML private void onRegisterClick() {
        if (!registrar.getStyleClass().contains("seleccionado")) {
            resetSeleccion();
            registrar.getStyleClass().add("seleccionado");
            cargarVista("devoluciones/admin/registrar-view.fxml");
        }
    }

    @FXML private void onModifyClick() {
        if (!modificar.getStyleClass().contains("seleccionado")) {
            resetSeleccion();
            modificar.getStyleClass().add("seleccionado");
            cargarVista("devoluciones/admin/modificar-view.fxml");
        }
    }

    @FXML private void onBackClick() {
        Stage stage = (Stage) usuario.getScene().getWindow();
        stage.close();
        VENTANA.mainWindow(usuarioLogueado);
    }

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public void initData() {
        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            registrar.getStyleClass().add("seleccionado");
            cargarVista("devoluciones/admin/registrar-view.fxml");
        }
    }

    private void resetSeleccion() {
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
    }

    private void cargarVista(String url) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
            VBox vista = fxmlLoader.load();
            containerData.getChildren().setAll(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
