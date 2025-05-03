package com.guayand0.librarymanager;

import com.guayand0.librarymanager.utils.Alertas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private final Alertas alertas = new Alertas();

    @Override
    public void start(Stage stage) {
        openAccessWindow();
    }

    public static void main(String[] args) {
        launch();
    }

    // Metodo para abrir la ventana principal
    public void openAccessWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("acceso/main-view-access.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            //Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("LibraryManager");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/com/guayand0/librarymanager/imagenes/logo.png"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertas.showError("Error al cargar la ventana principal.");
        }
    }
}
