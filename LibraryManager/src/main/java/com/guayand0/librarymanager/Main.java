package com.guayand0.librarymanager;

import com.guayand0.librarymanager.utils.Alertas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private final Alertas alertas = new Alertas();

    @Override
    public void start(Stage stage) {
        openAccessWindow();
    }

    public static void main(String[] args) {
        launch();
    }

    // Método para abrir la ventana principal
    private void openAccessWindow() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("acceso/main-view-access.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            Stage stage = new Stage();
            stage.setTitle("LibraryManager - Main Window");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            alertas.showError("Error al cargar la ventana principal.");
        }
    }
}
