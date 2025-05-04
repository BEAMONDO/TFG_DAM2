package com.guayand0.librarymanager.utils;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.controller.MainController;
import com.guayand0.librarymanager.controller.UsuariosController;
import com.guayand0.librarymanager.model.Usuario;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Ventanas {

    private final Alertas ALERT = new Alertas();

    // Ventana de inicio de sesion
    public void accessWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("acceso/main-view-access.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("usuarios/usuarios-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de inicio de sesion.");
        }
    }

    // Ventana principal de la aplicacion
    public void mainWindow(Usuario usuarioLogueado) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Obtener el controlador y pasar el usuario
            MainController controller = fxmlLoader.getController();
            controller.setUsuarioTexto(usuarioLogueado.getNombre() + " " + usuarioLogueado.getApellidos());
            controller.setPermisoTexto(usuarioLogueado.getPermiso());
            controller.setUsuarioLogueado(usuarioLogueado);

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana principal.");
        }
    }

    // Ventana de manejo de usuarios
    public void userWindow(Usuario usuarioLogueado) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("usuarios/usuarios-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            // Obtener el controlador y pasar el usuario
            UsuariosController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de usuarios.");
        }
    }
}
