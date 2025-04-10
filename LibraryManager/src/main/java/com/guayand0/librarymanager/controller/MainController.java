package com.guayand0.librarymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import com.guayand0.librarymanager.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private Button btnLibros, btnUsuarios, btnPrestamos, btnDevoluciones, btnInformes, btnAyuda;

    @FXML
    private StackPane containerData;

    @FXML
    private HBox librosForm, usuariosForm, prestamosForm, devolucionesForm, informesForm, ayudaForm;

    @FXML
    private Label lblLibros, lblUsuarios, lblPrestamos, lblDevoluciones, lblInformes, lblAyuda;

    @FXML
    protected void actionEvent(ActionEvent e) {
        Object evt = e.getSource();

        resetFormVisibility();

        if (evt.equals(btnLibros)) {
            librosForm.setVisible(true);
            lblLibros.setStyle("-fx-font-weight: bold;");
        } else if (evt.equals(btnUsuarios)) {
            usuariosForm.setVisible(true);
            lblUsuarios.setStyle("-fx-font-weight: bold;");
        } else if (evt.equals(btnPrestamos)) {
            prestamosForm.setVisible(true);
            lblPrestamos.setStyle("-fx-font-weight: bold;");
        } else if (evt.equals(btnDevoluciones)) {
            devolucionesForm.setVisible(true);
            lblDevoluciones.setStyle("-fx-font-weight: bold;");
        } else if (evt.equals(btnInformes)) {
            informesForm.setVisible(true);
            lblInformes.setStyle("-fx-font-weight: bold;");
        } else if (evt.equals(btnAyuda)) {
            ayudaForm.setVisible(true);
            lblAyuda.setStyle("-fx-font-weight: bold;");
        }
    }

    // Método que oculta todas las secciones y restablece los estilos
    private void resetFormVisibility() {
        librosForm.setVisible(false);
        lblLibros.setStyle("-fx-font-weight: normal;");
        usuariosForm.setVisible(false);
        lblUsuarios.setStyle("-fx-font-weight: normal;");
        prestamosForm.setVisible(false);
        lblPrestamos.setStyle("-fx-font-weight: normal;");
        devolucionesForm.setVisible(false);
        lblDevoluciones.setStyle("-fx-font-weight: normal;");
        informesForm.setVisible(false);
        lblInformes.setStyle("-fx-font-weight: normal;");
        ayudaForm.setVisible(false);
        lblAyuda.setStyle("-fx-font-weight: normal;");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            librosForm = loadForm("libros/libros-view.fxml");
            usuariosForm = loadForm("usuarios/usuarios-view.fxml");
            prestamosForm = loadForm("prestamos/prestamos-view.fxml");
            devolucionesForm = loadForm("devoluciones/devoluciones-view.fxml");
            informesForm = loadForm("informes/informes-view.fxml");
            ayudaForm = loadForm("ayuda/ayuda-view.fxml");

            containerData.getChildren().addAll(librosForm, usuariosForm, prestamosForm, devolucionesForm, informesForm, ayudaForm);

            resetFormVisibility();

            librosForm.setVisible(true);
            lblLibros.setStyle("-fx-font-weight: bold;");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private HBox loadForm(String url) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(url));
        HBox data = fxmlLoader.load();
        return data;
    }
}