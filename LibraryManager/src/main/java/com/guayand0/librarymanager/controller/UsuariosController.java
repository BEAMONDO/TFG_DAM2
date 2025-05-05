package com.guayand0.librarymanager.controller;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.model.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController {

    private final Ventanas VENTANA = new Ventanas();

    @FXML private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private StackPane containerData;
    @FXML private VBox registerForm, modifyForm, deleteForm;
    @FXML private VBox registrar, modificar, eliminar;

    // Menú izquierdo

    @FXML private void onRegisterClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro registrar
        registrar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(true);
        modifyForm.setVisible(false);
        deleteForm.setVisible(false);
    }


    @FXML private void onModifyClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro register
        modificar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(false);
        modifyForm.setVisible(true);
        deleteForm.setVisible(false);
    }

    @FXML private void onDeleteClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro register
        eliminar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(false);
        modifyForm.setVisible(false);
        deleteForm.setVisible(true);
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
        try {
            if (usuarioLogueado.getPermiso().equals("Administrador")) {
                registerForm = loadForm("usuarios/admin/registrar-view.fxml");
                modifyForm = loadForm("usuarios/admin/modificar-view.fxml");
                deleteForm = loadForm("usuarios/admin/borrar-view.fxml");

                containerData.getChildren().addAll(registerForm, modifyForm, deleteForm);

                registrar.getStyleClass().add("seleccionado");

                registerForm.setVisible(true);
                modifyForm.setVisible(false);
                deleteForm.setVisible(false);
            } else {
                modifyForm = loadForm("usuarios/user/modificar-view.fxml");
                containerData.getChildren().add(modifyForm);

                if (modificar != null) {
                    modificar.getStyleClass().add("seleccionado");
                }

                modifyForm.setVisible(true);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private VBox loadForm(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        VBox data = fxmlLoader.load();
        return data;
    }

}
