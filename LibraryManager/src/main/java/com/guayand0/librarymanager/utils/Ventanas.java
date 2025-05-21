package com.guayand0.librarymanager.utils;

import com.guayand0.librarymanager.Main;
import com.guayand0.librarymanager.controller.autores.AutoresController;
import com.guayand0.librarymanager.controller.ayuda.AyudaController;
import com.guayand0.librarymanager.controller.categorias.CategoriasController;
import com.guayand0.librarymanager.controller.devoluciones.DevolucionesController;
import com.guayand0.librarymanager.controller.editoriales.EditorialesController;
import com.guayand0.librarymanager.controller.idiomas.IdiomasController;
import com.guayand0.librarymanager.controller.informes.InformesController;
import com.guayand0.librarymanager.controller.libros.LibrosController;
import com.guayand0.librarymanager.controller.main.MainController;
import com.guayand0.librarymanager.controller.prestamos.PrestamosController;
import com.guayand0.librarymanager.controller.usuarios.UsuariosController;
import com.guayand0.librarymanager.model.usuario.Usuario;
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
            FXMLLoader fxmlLoader;

            if (usuarioLogueado.getPermiso().equals("Administrador")) {
                fxmlLoader = new FXMLLoader(Main.class.getResource("main/admin/main-view.fxml"));
            } else {
                fxmlLoader = new FXMLLoader(Main.class.getResource("main/user/main-view.fxml"));
            }
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

    public void librosWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("libros/admin/libros-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("libros/user/libros-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            LibrosController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de libros.");
        }
    }

    public void autoresWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("autores/admin/autores-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("autores/user/autores-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            AutoresController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de autores.");
        }
    }

    public void categoriasWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("categorias/admin/categorias-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("categorias/user/categorias-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            CategoriasController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de categorías.");
        }
    }

    public void editorialesWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("editoriales/admin/editoriales-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("editoriales/admin/editoriales-view.fxml/user/editoriales-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            EditorialesController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de editoriales.");
        }
    }

    public void idiomasWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("idiomas/admin/idiomas-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("idiomas/user/idiomas-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            IdiomasController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de idiomas.");
        }
    }

    public void usuariosWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("usuarios/admin/usuarios-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("usuarios/user/usuarios-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            UsuariosController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            controller.initData(); // Se llama aquí para evitar el NullPointer

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

    public void devolucionesWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("devoluciones/admin/devoluciones-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("devoluciones/user/devoluciones-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            DevolucionesController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de devoluciones.");
        }
    }

    public void prestamosWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("prestamos/admin/prestamos-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("prestamos/user/prestamos-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            PrestamosController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de préstamos.");
        }
    }

    public void informesWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("informes/admin/informes-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("informes/user/informes-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            InformesController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de informes.");
        }
    }

    public void ayudaWindow(Usuario usuarioLogueado) {
        FXMLLoader fxmlLoader;

        if (usuarioLogueado.getPermiso().equals("Administrador")) {
            fxmlLoader = new FXMLLoader(Main.class.getResource("ayuda/admin/ayuda-view.fxml"));
        } else {
            fxmlLoader = new FXMLLoader(Main.class.getResource("ayuda/user/ayuda-view.fxml"));
        }

        try {
            Scene scene = new Scene(fxmlLoader.load());

            AyudaController controller = fxmlLoader.getController();
            controller.setUsuarioLogueado(usuarioLogueado);
            //controller.initData(); // Se llama aquí para evitar el NullPointer

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            ALERT.showError("Error al cargar la ventana de ayuda.");
        }
    }
}
