package com.guayand0.librarymanager.controller.libros;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import com.guayand0.librarymanager.utils.VistaConControlador;
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
    @FXML private VBox registerForm, modifyForm, deleteForm, consultarForm;
    @FXML private VBox registrar, modificar, eliminar, consultar;

    // Menú izquierdo

    @FXML private void onRegisterClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
        consultar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro registrar
        registrar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(true);
        modifyForm.setVisible(false);
        deleteForm.setVisible(false);
        consultarForm.setVisible(false);
    }


    @FXML private void onModifyClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
        consultar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro modificar
        modificar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(false);
        modifyForm.setVisible(true);
        deleteForm.setVisible(false);
        consultarForm.setVisible(false);
    }

    @FXML private void onDeleteClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
        consultar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro eliminar
        eliminar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(false);
        modifyForm.setVisible(false);
        deleteForm.setVisible(true);
        consultarForm.setVisible(false);
    }

    @FXML private void onConsultarClick() {
        // Elimina la clase "seleccionado" de otros cuadros
        registrar.getStyleClass().remove("seleccionado");
        modificar.getStyleClass().remove("seleccionado");
        eliminar.getStyleClass().remove("seleccionado");
        consultar.getStyleClass().remove("seleccionado");

        // Agrega la clase "seleccionado" al cuadro consultar
        consultar.getStyleClass().add("seleccionado");

        // Muestra el formulario de inserción y oculta los demás formularios
        registerForm.setVisible(false);
        modifyForm.setVisible(false);
        deleteForm.setVisible(false);
        consultarForm.setVisible(true);
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
                registerForm = loadFormAdmin("libros/admin/registrar-view.fxml");
                modifyForm = loadFormAdmin("libros/admin/modificar-view.fxml");
                deleteForm = loadFormAdmin("libros/admin/eliminar-view.fxml");
                consultarForm = loadFormAdmin("libros/admin/consultar-view.fxml");

                containerData.getChildren().addAll(registerForm, modifyForm, deleteForm, consultarForm);

                registrar.getStyleClass().add("seleccionado");
                registerForm.setVisible(true);
                modifyForm.setVisible(false);
                deleteForm.setVisible(false);
                consultarForm.setVisible(false);

            }/* else {
                VistaConControlador<?> vistaControlador = loadForm("libros/user/modificar-view.fxml");
                modifyForm = vistaControlador.getVista();
                containerData.getChildren().add(modifyForm);

                if (vistaControlador.getControlador() instanceof com.guayand0.librarymanager.controller.libros.user.ModificarController) {
                    ((com.guayand0.librarymanager.controller.libros.user.ModificarController) vistaControlador.getControlador()).setUsuarioLogueado(usuarioLogueado);
                }

                if (modificar != null) modificar.getStyleClass().add("seleccionado");

                modifyForm.setVisible(true);
            }*/

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private VBox loadFormAdmin(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        VBox data = fxmlLoader.load();
        return data;
    }

    private VistaConControlador<?> loadForm(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        VBox data = fxmlLoader.load();
        Object controller = fxmlLoader.getController();
        return new VistaConControlador<>(data, controller);
    }
}
