package com.guayand0.librarymanager.controller;

import com.guayand0.librarymanager.utils.Alertas;
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
    private TextField insertarTituloLibro, insertarIsbnLibro, insertarPaginasLibro, insertarAnyoLibro;
    @FXML
    private ComboBox<String> insertarEstadoLibro, insertarAutorLibro, insertarCategoriaLibro, insertarEditorialLibro, insertarIdiomaLibro;
    @FXML
    private Button btnInsertarLibro;


    @FXML
    private TextField eliminarIsbnLibro;
    @FXML
    private Button btnEliminarLibro;

    private final LibroDAO libroDAO = new LibroDAO();
    private final Alertas alertas = new Alertas();

    // Método para cargar los títulos de los libros en el ComboBox
    public void initialize() {
        cargarComboLibros();
        cargarComboBusqueda();
        configurarListeners();

        configurarComboInsertar();

        // Establecer el límite de caracteres en los TextFields
        insertarIsbnLibro.setTextFormatter(createTextFormatter(20));
        insertarTituloLibro.setTextFormatter(createTextFormatter(255));
        insertarPaginasLibro.setTextFormatter(createTextFormatter(6));
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
            // Obtener todos los datos del libro seleccionado con una única consulta
            String[] datosLibro = libroDAO.selectDatos(tituloSeleccionado);

            // Asignar los valores a los campos correspondientes
            isbnLibro.setText(datosLibro[0]); // ISBN
            autorLibro.setText(datosLibro[1]); // Autor
            categoriaLibro.setText(datosLibro[2]); // Categoría
            editorialLibro.setText(datosLibro[3]); // Editorial
            paginasLibro.setText(datosLibro[4]); // Número de páginas
            idiomaLibro.setText(datosLibro[5]); // Idioma
            anyoLibro.setText(datosLibro[6]); // Año de publicación
            estadoLibro.setText(datosLibro[7]); // Estado
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










    private void configurarComboInsertar() {
        ObservableList<String> autores = FXCollections.observableArrayList(
                "", "qwe", "asd", "zxc"
        );
        insertarAutorLibro.setItems(autores);
        insertarAutorLibro.getSelectionModel().select(0);

        ObservableList<String> estados = FXCollections.observableArrayList(
                "disponible", "prestado", "deteriorado", "bloqueado"
        );
        insertarEstadoLibro.setItems(estados);
        insertarEstadoLibro.getSelectionModel().select(0);
    }

    @FXML
    public void actionEvent2(ActionEvent e) {
        // Validar que los campos no estén vacíos
        if (insertarIsbnLibro.getText().isEmpty()) {
            alertas.showWarning("El campo ISBN no tiene un valor permitido");
            return;
        }

        // Validar el formato del ISBN
        String isbn = insertarIsbnLibro.getText();
        if (!isbn.matches("\\d{3}-\\d-\\d{5}-\\d{3}-\\d")) {
            alertas.showWarning("El formato del ISBN no es válido. Debe tener este formato: 000-0-00000-000-0");
            return;
        }

        // Verificar si el ISBN ya existe en la base de datos
        if (libroDAO.existeIsbn(isbn)) {
            alertas.showWarning("El ISBN ya existe en la base de datos.");
            return;
        }

        // Validar que otros campos no estén vacíos
        if (insertarTituloLibro.getText().isEmpty()) {
            alertas.showWarning("El campo Título no tiene un valor permitido");
            return;
        }
        if (insertarPaginasLibro.getText().isEmpty()) {
            alertas.showWarning("El campo Número de Páginas no tiene un valor permitido");
            return;
        }
        if (insertarAnyoLibro.getText().isEmpty()) {
            alertas.showWarning("El campo Año de Publicación no tiene un valor permitido");
            return;
        }

        // Crear un objeto Libro con los valores de los campos de texto
        Libro libro = new Libro();
        libro.setISBN(insertarIsbnLibro.getText());
        libro.setTitulo(insertarTituloLibro.getText());
        libro.setAutor(Integer.parseInt(insertarAutorLibro.getValue()));
        libro.setCategoria(Integer.parseInt(insertarCategoriaLibro.getValue()));
        libro.setEditorial(Integer.parseInt(insertarEditorialLibro.getValue()));

        try {
            libro.setNumeroPaginas(Integer.parseInt(insertarPaginasLibro.getText()));
        } catch (NumberFormatException ex) {
            alertas.showWarning("El campo Número de Páginas debe ser un número válido.");
            return;
        }

        libro.setIdioma(Integer.parseInt(insertarIdiomaLibro.getValue()));

        try {
            libro.setAnioPublicacion(Integer.parseInt(insertarAnyoLibro.getText()));
        } catch (NumberFormatException ex) {
            alertas.showWarning("El campo Año de Publicación debe ser un número válido.");
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
        insertarAutorLibro.getSelectionModel().select(0);
        insertarCategoriaLibro.getSelectionModel().select(0);
        insertarEditorialLibro.getSelectionModel().select(0);
        insertarPaginasLibro.setText("");
        insertarIdiomaLibro.getSelectionModel().select(0);
        insertarAnyoLibro.setText("");
        insertarEstadoLibro.getSelectionModel().select(0);
    }

    @FXML
    public void actionEvent3(ActionEvent e) {
        // Validar que el campo ISBN no esté vacío
        String isbn = eliminarIsbnLibro.getText();
        if (isbn.isEmpty()) {
            alertas.showWarning("El campo ISBN no puede estar vacío.");
            return;
        }

        // Validar el formato del ISBN
        if (!isbn.matches("\\d{3}-\\d-\\d{5}-\\d{3}-\\d")) {
            alertas.showWarning("El formato del ISBN no es válido. Debe tener este formato: 000-0-00000-000-0");
            return;
        }

        // Verificar si el libro con el ISBN existe en la base de datos
        if (!libroDAO.existeIsbn(isbn)) {
            alertas.showWarning("El libro con el ISBN proporcionado no existe en la base de datos.");
            return;
        }

        // Mostrar una ventana de confirmación antes de eliminar el libro
        boolean confirmar = alertas.showConfirmation("¿Está seguro de que desea eliminar el libro con ISBN: " + isbn + "?");
        if (confirmar) {
            // Eliminar el libro
            libroDAO.eliminarLibro(isbn);

            // Mostrar un mensaje de confirmación
            alertas.showWarning("El libro con ISBN " + isbn + " ha sido eliminado correctamente.");

            // Limpiar los campos de eliminación
            clearDeleteFields();
        }
    }

    private void clearDeleteFields() {
        eliminarIsbnLibro.setText("");
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
