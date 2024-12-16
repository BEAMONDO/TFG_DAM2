package org.guayando.librarymanager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.guayando.librarymanager.Main;

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

        if (evt.equals(btnLibros)) {
            librosForm.setVisible(true);
            lblLibros.setStyle("-fx-font-weight: bold;");
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
        } else if (evt.equals(btnUsuarios)) {
            librosForm.setVisible(false);
            lblLibros.setStyle("-fx-font-weight: normal;");
            usuariosForm.setVisible(true);
            lblUsuarios.setStyle("-fx-font-weight: bold;");
            prestamosForm.setVisible(false);
            lblPrestamos.setStyle("-fx-font-weight: normal;");
            devolucionesForm.setVisible(false);
            lblDevoluciones.setStyle("-fx-font-weight: normal;");
            informesForm.setVisible(false);
            lblInformes.setStyle("-fx-font-weight: normal;");
            ayudaForm.setVisible(false);
            lblAyuda.setStyle("-fx-font-weight: normal;");
        } else if (evt.equals(btnPrestamos)) {
            librosForm.setVisible(false);
            lblLibros.setStyle("-fx-font-weight: normal;");
            usuariosForm.setVisible(false);
            lblUsuarios.setStyle("-fx-font-weight: normal;");
            prestamosForm.setVisible(true);
            lblPrestamos.setStyle("-fx-font-weight: bold;");
            devolucionesForm.setVisible(false);
            lblDevoluciones.setStyle("-fx-font-weight: normal;");
            informesForm.setVisible(false);
            lblInformes.setStyle("-fx-font-weight: normal;");
            ayudaForm.setVisible(false);
            lblAyuda.setStyle("-fx-font-weight: normal;");
        } else if (evt.equals(btnDevoluciones)) {
            librosForm.setVisible(false);
            lblLibros.setStyle("-fx-font-weight: normal;");
            usuariosForm.setVisible(false);
            lblUsuarios.setStyle("-fx-font-weight: normal;");
            prestamosForm.setVisible(false);
            lblPrestamos.setStyle("-fx-font-weight: normal;");
            devolucionesForm.setVisible(true);
            lblDevoluciones.setStyle("-fx-font-weight: bold;");
            informesForm.setVisible(false);
            lblInformes.setStyle("-fx-font-weight: normal;");
            ayudaForm.setVisible(false);
            lblAyuda.setStyle("-fx-font-weight: normal;");
        } else if (evt.equals(btnInformes)) {
            librosForm.setVisible(false);
            lblLibros.setStyle("-fx-font-weight: normal;");
            usuariosForm.setVisible(false);
            lblUsuarios.setStyle("-fx-font-weight: normal;");
            prestamosForm.setVisible(false);
            lblPrestamos.setStyle("-fx-font-weight: normal;");
            devolucionesForm.setVisible(false);
            lblDevoluciones.setStyle("-fx-font-weight: normal;");
            informesForm.setVisible(true);
            lblInformes.setStyle("-fx-font-weight: bold;");
            ayudaForm.setVisible(false);
            lblAyuda.setStyle("-fx-font-weight: normal;");
        } else if (evt.equals(btnAyuda)) {
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
            ayudaForm.setVisible(true);
            lblAyuda.setStyle("-fx-font-weight: bold;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            librosForm = loadForm("libros-view.fxml");
            usuariosForm = loadForm("usuarios-view.fxml");
            prestamosForm = loadForm("prestamos-view.fxml");
            devolucionesForm = loadForm("devoluciones-view.fxml");
            informesForm = loadForm("informes-view.fxml");
            ayudaForm = loadForm("ayuda-view.fxml");

            containerData.getChildren().addAll(librosForm, usuariosForm, prestamosForm, devolucionesForm, informesForm, ayudaForm);

            librosForm.setVisible(true);
            lblLibros.setStyle("-fx-font-weight: bold;");
            usuariosForm.setVisible(false);
            prestamosForm.setVisible(false);
            devolucionesForm.setVisible(false);
            informesForm.setVisible(false);
            ayudaForm.setVisible(false);
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