package com.guayand0.librarymanager.controller.libros;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LibrosController {

    private final Ventanas VENTANA = new Ventanas();

    @FXML private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private StackPane containerData;
    @FXML private VBox registrar, modificar, eliminar, consultar;

    private VBox currentForm;

    @FXML private void onRegisterClick() {
        resetSeleccion();
        registrar.getStyleClass().add("seleccionado");
        cargarVistaAdmin("libros/admin/registrar-view.fxml");
    }

    @FXML private void onModifyClick() {
        resetSeleccion();
        modificar.getStyleClass().add("seleccionado");
        cargarVistaAdmin("libros/admin/modificar-view.fxml");
    }

    @FXML private void onDeleteClick() {
        resetSeleccion();
        eliminar.getStyleClass().add("seleccionado");
        cargarVistaAdmin("libros/admin/eliminar-view.fxml");
    }

    @FXML private void onConsultarClick() {
        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            resetSeleccion();
            consultar.getStyleClass().add("seleccionado");
            cargarVistaAdmin("libros/admin/consultar-view.fxml");
        } else {
            consultar.getStyleClass().add("seleccionado");
            cargarVistaUser("libros/user/consultar-view.fxml");
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
            consultar.getStyleClass().add("seleccionado");
            cargarVistaAdmin("libros/admin/consultar-view.fxml");
        } else {
            consultar.getStyleClass().add("seleccionado");
            cargarVistaUser("libros/user/consultar-view.fxml");
        }
    }

    private void resetSeleccion() {
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
        consultar.getStyleClass().remove("seleccionado");
    }

    private void cargarVistaAdmin(String url) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
            VBox vista = fxmlLoader.load();

            Object controller = fxmlLoader.getController();
            if (controller instanceof com.guayand0.librarymanager.controller.libros.admin.ConsultarController) {
                ((com.guayand0.librarymanager.controller.libros.admin.ConsultarController) controller).setUsuarioLogueado(usuarioLogueado);
                ((com.guayand0.librarymanager.controller.libros.admin.ConsultarController) controller).initData();
            }

            containerData.getChildren().setAll(vista);
            currentForm = vista;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarVistaUser(String url) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
            VBox vista = fxmlLoader.load();

            Object controller = fxmlLoader.getController();

            if (controller instanceof com.guayand0.librarymanager.controller.usuarios.user.ModificarController) {
                ((com.guayand0.librarymanager.controller.usuarios.user.ModificarController) controller).setUsuarioLogueado(usuarioLogueado);
            }

            if (controller instanceof com.guayand0.librarymanager.controller.libros.user.ConsultarController) {
                ((com.guayand0.librarymanager.controller.libros.user.ConsultarController) controller).setUsuarioLogueado(usuarioLogueado);
                ((com.guayand0.librarymanager.controller.libros.user.ConsultarController) controller).initData();
            }

            containerData.getChildren().setAll(vista);
            currentForm = vista;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
