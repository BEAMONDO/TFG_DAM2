package com.guayand0.librarymanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.guayand0.librarymanager.model.Libro;
import com.guayand0.librarymanager.model.LibroDAO;

public class LibrosController {

    @FXML
    private TextField campoBusquedaTitulo;
    @FXML
    private ComboBox<String> comboLibros, comboBusqueda;
    @FXML
    private TextField isbnLibro, autorLibro, categoriaLibro, editorialLibro, paginasLibro, idiomaLibro, anyoLibro, estadoLibro;
    @FXML
    private Button btnLimpiarCampos;

    private final LibroDAO libroDAO = new LibroDAO();

    // Metodo para cargar los títulos de los libros en el ComboBox
    public void initialize() {
        // Cargar los títulos de los libros en el ComboBox comboLibros
        ObservableList<String> titulos = FXCollections.observableArrayList();
        for (Libro libro : libroDAO.selectTitulos("", "")) {
            titulos.add(libro.getTitulo());
        }
        comboLibros.setItems(titulos);

        // Cargar las opciones de búsqueda en comboBusqueda
        ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "ISBN", "Título", "Autor", "Categoría", "Editorial", "Número de Páginas", "Idioma", "Año de Publicación", "Estado"
        );
        comboBusqueda.setItems(opcionesBusqueda);
        comboBusqueda.getSelectionModel().select(1);

        // Agregar un listener al campo de búsqueda para actualizar el ComboBox de libros
        campoBusquedaTitulo.textProperty().addListener((observable, oldValue, newValue) -> actualizarComboBoxLibrosBusqueda(newValue));

        // Agregar un listener al comboBusqueda para actualizar el ComboBox de libros cuando cambie la opción
        comboBusqueda.valueProperty().addListener((observable, oldValue, newValue) -> actualizarComboBoxLibrosBusqueda(campoBusquedaTitulo.getText()));

        // Agregar listener al comboLibros para manejar selección
        comboLibros.valueProperty().addListener((observable, oldValue, newValue) -> manejarSeleccionDeComboLibros(newValue));
    }

    private void actualizarComboBoxLibrosBusqueda(String textoBusqueda) {
        ObservableList<String> titulos = FXCollections.observableArrayList();
        String campoBusquedaSeleccionado = comboBusqueda.getValue(); // Obtener el valor del ComboBox de búsqueda

        // Llamar al DAO para obtener los libros filtrados por el texto de búsqueda y el campo seleccionado
        for (Libro libro : libroDAO.selectTitulos(textoBusqueda, campoBusquedaSeleccionado)) {
            titulos.add(libro.getTitulo());
        }

        // Actualizar los elementos del ComboBox de libros
        comboLibros.setItems(titulos);
    }

    private void manejarSeleccionDeComboLibros(String tituloSeleccionado) {
        if (tituloSeleccionado != null) {
            // Obtener datos del libro seleccionado
            isbnLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "isbn"));
            autorLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "autor"));
            categoriaLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "categoría"));
            editorialLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "editorial"));
            paginasLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "número de páginas"));
            idiomaLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "idioma"));
            anyoLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "año de publicación"));
            estadoLibro.setText(libroDAO.selectDatos(tituloSeleccionado, "estado"));

        } else {
            // Limpiar los campos de texto
            clearTextFields();
        }
    }

    private void clearTextFields() {
        isbnLibro.setText("");
        autorLibro.setText("");
        categoriaLibro.setText("");
        editorialLibro.setText("");
        paginasLibro.setText("");
        idiomaLibro.setText("");
        anyoLibro.setText("");
        estadoLibro.setText("");
    }

    @FXML
    private void actionEvent(ActionEvent e) {
        Object evt = e.getSource();

        if (evt.equals(btnLimpiarCampos)) {
            comboBusqueda.getSelectionModel().select(1);
            comboLibros.getSelectionModel().clearSelection();
            campoBusquedaTitulo.setText("");
            clearTextFields();
        }
    }
}
