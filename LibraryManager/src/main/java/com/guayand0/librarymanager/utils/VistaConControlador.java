package com.guayand0.librarymanager.utils;

import javafx.scene.layout.VBox;

public class VistaConControlador<T> {
    private final VBox vista;
    private final T controlador;

    public VistaConControlador(VBox vista, T controlador) {
        this.vista = vista;
        this.controlador = controlador;
    }

    public VBox getVista() {
        return vista;
    }

    public T getControlador() {
        return controlador;
    }
}
