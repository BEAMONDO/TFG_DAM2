package com.guayand0.librarymanager.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import com.guayand0.librarymanager.model.Libro;
import com.guayand0.librarymanager.model.LibroDAO;

public class LibrosController {

    @FXML
    private ComboBox<String> comboLibros, comboBusqueda;
    @FXML
    private TextField campoBusquedaTitulo, isbnLibro, autorLibro, categoriaLibro, editorialLibro, paginasLibro, idiomaLibro, anyoLibro, estadoLibro;
    @FXML
    private Button btnLimpiarCampos;

    @FXML
    private TextField insertarTituloLibro, insertarIsbnLibro, insertarAutorLibro, insertarCategoriaLibro, insertarEditorialLibro, insertarPaginasLibro, insertarIdiomaLibro, insertarAnyoLibro;
    @FXML
    private ComboBox<String> insertarEstadoLibro;
    @FXML
    private Button btnInsertarLibro;


    @FXML
    private TextField eliminarIsbnLibro;
    @FXML
    private Button btnEliminarLibro;

    private final LibroDAO libroDAO = new LibroDAO();

    // Método para cargar los títulos de los libros en el ComboBox
    public void initialize() {
        cargarComboLibros();
        cargarComboBusqueda();
        cargarComboEstadoInsertar();
        configurarListeners();

        // Establecer el límite de caracteres en los TextFields
        insertarIsbnLibro.setTextFormatter(createTextFormatter(20));
        insertarTituloLibro.setTextFormatter(createTextFormatter(255));
        insertarAutorLibro.setTextFormatter(createTextFormatter(255));
        insertarCategoriaLibro.setTextFormatter(createTextFormatter(50));
        insertarEditorialLibro.setTextFormatter(createTextFormatter(100));
        insertarPaginasLibro.setTextFormatter(createTextFormatter(6));
        insertarIdiomaLibro.setTextFormatter(createTextFormatter(50));
        insertarAnyoLibro.setTextFormatter(createTextFormatter(4));
    }

    private TextFormatter<String> createTextFormatter(int maxLength) {
        // Crear un filtro de texto para restringir la longitud máxima de caracteres
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                change.setText("");  // Ignorar el cambio si el texto excede el límite
                change.setCaretPosition(change.getCaretPosition() - 1); // Ajustar la posición del cursor
            }
            return change;
        });
    }

    private void cargarComboLibros() {
        ObservableList<String> titulos = FXCollections.observableArrayList();
        for (Libro libro : libroDAO.selectTitulos("", "")) {
            titulos.add(libro.getTitulo());
        }
        comboLibros.setItems(titulos);
    }

    private void cargarComboBusqueda() {
        ObservableList<String> opcionesBusqueda = FXCollections.observableArrayList(
                "ISBN", "Título", "Autor", "Categoría", "Editorial", "Número de Páginas", "Idioma", "Año de Publicación", "Estado"
        );
        comboBusqueda.setItems(opcionesBusqueda);
        comboBusqueda.getSelectionModel().select(1);
    }

    private void cargarComboEstadoInsertar() {
        ObservableList<String> estados = FXCollections.observableArrayList(
                "disponible", "prestado", "deteriorado", "bloqueado"
        );
        insertarEstadoLibro.setItems(estados);
        insertarEstadoLibro.getSelectionModel().select(0);
    }

    private void configurarListeners() {
        // Listener para el campo de búsqueda
        campoBusquedaTitulo.textProperty().addListener((observable, oldValue, newValue) -> actualizarComboBoxLibrosBusqueda(newValue));

        // Listener para el combo de búsqueda
        comboBusqueda.valueProperty().addListener((observable, oldValue, newValue) -> actualizarComboBoxLibrosBusqueda(campoBusquedaTitulo.getText()));

        // Listener para el ComboBox de libros
        comboLibros.valueProperty().addListener((observable, oldValue, newValue) -> manejarSeleccionDeComboLibros(newValue));
    }

    private void actualizarComboBoxLibrosBusqueda(String textoBusqueda) {
        ObservableList<String> titulos = FXCollections.observableArrayList();
        String campoBusquedaSeleccionado = comboBusqueda.getValue();

        for (Libro libro : libroDAO.selectTitulos(textoBusqueda, campoBusquedaSeleccionado)) {
            titulos.add(libro.getTitulo());
        }

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
        comboBusqueda.getSelectionModel().select(1);
        comboLibros.getSelectionModel().clearSelection();
        campoBusquedaTitulo.setText("");
        clearTextFields();
    }

    @FXML
    public void actionEvent2(ActionEvent e) {
        // Validar que los campos no estén vacíos
        if (insertarIsbnLibro.getText().isEmpty()) {
            showAlert("El campo ISBN no tiene un valor permitido");
            return;
        }

        // Validar el formato del ISBN
        String isbn = insertarIsbnLibro.getText();
        if (!isbn.matches("\\d{3}-\\d-\\d{5}-\\d{3}-\\d")) {
            showAlert("El formato del ISBN no es válido. Debe tener este formato: 000-0-00000-000-0");
            return;
        }

        // Verificar si el ISBN ya existe en la base de datos
        if (libroDAO.existeIsbn(isbn)) {
            showAlert("El ISBN ya existe en la base de datos.");
            return;
        }

        // Validar que otros campos no estén vacíos
        if (insertarTituloLibro.getText().isEmpty()) {
            showAlert("El campo Título no tiene un valor permitido");
            return;
        }
        if (insertarAutorLibro.getText().isEmpty()) {
            showAlert("El campo Autor no tiene un valor permitido");
            return;
        }
        if (insertarCategoriaLibro.getText().isEmpty()) {
            showAlert("El campo Categoría no tiene un valor permitido");
            return;
        }
        if (insertarEditorialLibro.getText().isEmpty()) {
            showAlert("El campo Editorial no tiene un valor permitido");
            return;
        }
        if (insertarPaginasLibro.getText().isEmpty()) {
            showAlert("El campo Número de Páginas no tiene un valor permitido");
            return;
        }
        if (insertarIdiomaLibro.getText().isEmpty()) {
            showAlert("El campo Idioma no tiene un valor permitido");
            return;
        }
        if (insertarAnyoLibro.getText().isEmpty()) {
            showAlert("El campo Año de Publicación no tiene un valor permitido");
            return;
        }

        // Crear un objeto Libro con los valores de los campos de texto
        Libro libro = new Libro();
        libro.setISBN(insertarIsbnLibro.getText());
        libro.setTitulo(insertarTituloLibro.getText());
        libro.setAutor(insertarAutorLibro.getText());
        libro.setCategoria(insertarCategoriaLibro.getText());
        libro.setEditorial(insertarEditorialLibro.getText());

        try {
            libro.setNumeroPaginas(Integer.parseInt(insertarPaginasLibro.getText()));
        } catch (NumberFormatException ex) {
            showAlert("El campo Número de Páginas debe ser un número válido.");
            return;
        }

        libro.setIdioma(insertarIdiomaLibro.getText());

        try {
            libro.setAnioPublicacion(Integer.parseInt(insertarAnyoLibro.getText()));
        } catch (NumberFormatException ex) {
            showAlert("El campo Año de Publicación debe ser un número válido.");
            return;
        }

        libro.setEstado(insertarEstadoLibro.getValue());

        // Llamar al DAO para insertar el libro
        libroDAO.insertLibro(libro);

        // Mostrar un mensaje de confirmación con los detalles del libro
        showBookDetailsAlert(libro);

        // Limpiar los campos de inserción después de insertar
        clearInsertFields();
    }


    private void showBookDetailsAlert(Libro libro) {
        String mensaje = "Se ha añadido un libro con los siguientes valores:\n" +
                "Título: " + libro.getTitulo() + "\n" +
                "ISBN: " + libro.getISBN() + "\n" +
                "Autor: " + libro.getAutor() + "\n" +
                "Categoría: " + libro.getCategoria() + "\n" +
                "Editorial: " + libro.getEditorial() + "\n" +
                "Número de Páginas: " + libro.getNumeroPaginas() + "\n" +
                "Idioma: " + libro.getIdioma() + "\n" +
                "Año de Publicación: " + libro.getAnioPublicacion() + "\n" +
                "Estado: " + libro.getEstado();

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Libro Añadido");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void clearInsertFields() {
        insertarIsbnLibro.setText("");
        insertarTituloLibro.setText("");
        insertarAutorLibro.setText("");
        insertarCategoriaLibro.setText("");
        insertarEditorialLibro.setText("");
        insertarPaginasLibro.setText("");
        insertarIdiomaLibro.setText("");
        insertarAnyoLibro.setText("");
        insertarEstadoLibro.getSelectionModel().select(0);
    }

    @FXML
    public void actionEvent3(ActionEvent e) {
        // Validar que el campo ISBN no esté vacío
        String isbn = eliminarIsbnLibro.getText();
        if (isbn.isEmpty()) {
            showAlert("El campo ISBN no puede estar vacío.");
            return;
        }

        // Validar el formato del ISBN
        if (!isbn.matches("\\d{3}-\\d-\\d{5}-\\d{3}-\\d")) {
            showAlert("El formato del ISBN no es válido. Debe tener este formato: 000-0-00000-000-0");
            return;
        }

        // Verificar si el libro con el ISBN existe en la base de datos
        if (!libroDAO.existeIsbn(isbn)) {
            showAlert("El libro con el ISBN proporcionado no existe en la base de datos.");
            return;
        }

        // Mostrar una ventana de confirmación antes de eliminar el libro
        boolean confirmar = showConfirmationAlert("¿Está seguro de que desea eliminar el libro con ISBN: " + isbn + "?");
        if (confirmar) {
            // Eliminar el libro
            libroDAO.eliminarLibro(isbn);

            // Mostrar un mensaje de confirmación
            showAlert("El libro con ISBN " + isbn + " ha sido eliminado correctamente.");

            // Limpiar los campos de eliminación
            clearDeleteFields();
        }
    }

    private void clearDeleteFields() {
        eliminarIsbnLibro.setText("");
    }

    // Método para mostrar alertas
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Advertencia");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método para mostrar la ventana de confirmación
    private boolean showConfirmationAlert(String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(message);

        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
