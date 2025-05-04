package com.guayand0.librarymanager;

import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private final Ventanas VENTANA = new Ventanas();

    @Override
    public void start(Stage stage) {
        VENTANA.accessWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}
