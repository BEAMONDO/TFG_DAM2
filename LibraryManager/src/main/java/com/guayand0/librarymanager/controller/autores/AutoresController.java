package com.guayand0.librarymanager.controller.autores;

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

public class AutoresController {

    private final Ventanas VENTANA = new Ventanas();

    @FXML private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private StackPane containerData;
    @FXML private VBox registrar, modificar, eliminar;

    @FXML private void onRegisterClick() {
        resetSeleccion();
        registrar.getStyleClass().add("seleccionado");
        cargarVista("autores/admin/registrar-view.fxml");
    }

    @FXML private void onModifyClick() {
        resetSeleccion();
        modificar.getStyleClass().add("seleccionado");
        cargarVista("autores/admin/modificar-view.fxml");
    }

    @FXML private void onDeleteClick() {
        resetSeleccion();
        eliminar.getStyleClass().add("seleccionado");
        cargarVista("autores/admin/eliminar-view.fxml");
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
            cargarVista("autores/admin/registrar-view.fxml");
        }
    }

    private void resetSeleccion() {
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
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
