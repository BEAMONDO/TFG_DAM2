package com.guayand0.librarymanager;

import com.guayand0.librarymanager.utils.Ventanas;
import javafx.application.Application;
import javafx.stage.Stage;

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
