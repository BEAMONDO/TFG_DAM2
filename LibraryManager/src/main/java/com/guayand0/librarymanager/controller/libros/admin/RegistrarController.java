package com.guayand0.librarymanager.controller.libros.admin;

import com.guayand0.librarymanager.model.autor.Autor;
import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.model.categoria.Categoria;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.model.editorial.Editorial;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.model.idioma.Idioma;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.model.libro.Libro;
import com.guayand0.librarymanager.model.libro.LibroDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.ComprobarISBN;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import com.guayand0.librarymanager.utils.MostrarContrasena;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    LibroDAO libroDAO = new LibroDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();
    private final ComprobarISBN CISBN = new ComprobarISBN();

    private Usuario usuarioLogueado;

    @FXML private TextField isbnField, tituloField, paginasField, anioField;
    @FXML private ComboBox<String> autorCombo, categoriaCombo, editorialCombo, idiomaCombo, estadoCombo;

    private final Map<Integer, String> autorMap = new HashMap<>();
    private final Map<Integer, String> categoriaMap = new HashMap<>();
    private final Map<Integer, String> editorialMap = new HashMap<>();
    private final Map<Integer, String> idiomaMap = new HashMap<>();

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
        cargarDatosLibros();
    }

    private void aplicarLimitesCaracteres() {
        tituloField.setTextFormatter(LC.createTextFormatter(255));
        paginasField.setTextFormatter(LC.createTextFormatter(5));
        anioField.setTextFormatter(LC.createTextFormatter(4));
    }

    private void cargarDatosLibros() {
        new Thread(() -> {
            cargarAutor();
            cargarCatetgoria();
            cargarEditorial();
            cargarIdioma();
        }).start();
    }

    private void cargarAutor() {
        AutorDAO autorDAO = new AutorDAO();
        Map<Integer, String> mapa = autorDAO.obtenerAutorIdNombreMap();
        autorMap.putAll(mapa);
        autorCombo.getItems().addAll(mapa.values());
    }

    private void cargarCatetgoria() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Map<Integer, String> mapa = categoriaDAO.obtenerCategoriaIdNombreMap();
        categoriaMap.putAll(mapa);
        categoriaCombo.getItems().addAll(mapa.values());
    }

    private void cargarEditorial() {
        EditorialDAO editorialDAO = new EditorialDAO();
        Map<Integer, String> mapa = editorialDAO.obtenerEditorialIdNombreMap();
        editorialMap.putAll(mapa);
        editorialCombo.getItems().addAll(mapa.values());
    }

    private void cargarIdioma() {
        IdiomaDAO idiomaDAO = new IdiomaDAO();
        Map<Integer, String> mapa = idiomaDAO.obtenerIdiomaIdNombreMap();
        idiomaMap.putAll(mapa);
        idiomaCombo.getItems().addAll(mapa.values());
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String isbn = isbnField.getText();
        String titulo = tituloField.getText();
        String autor = autorCombo.getValue();
        String categoria = categoriaCombo.getValue();
        String editorial = editorialCombo.getValue();
        int paginas = Integer.parseInt(paginasField.getText());
        String idioma = idiomaCombo.getValue();
        int anio = Integer.parseInt(anioField.getText());
        String estado = estadoCombo.getValue();

        int autorId = getKeyByValue(autorMap, autor);
        int categoriaId = getKeyByValue(categoriaMap, categoria);
        int editorialId = getKeyByValue(editorialMap, editorial);
        int idiomaId = getKeyByValue(idiomaMap, idioma);

        Libro libro = new Libro(
                isbn, titulo, autorId, categoriaId, editorialId,
                paginas, idiomaId, anio, estado
        );

        boolean registrado = libroDAO.register(libro);

        if (registrado) {
            ALERT.showInformation("Libro registrado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar el libro.");
        }
    }

    private int getKeyByValue(Map<Integer, String> map, String value) {
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return -1; // o lanzar excepción si es necesario
    }

    private void limpiarCampos() {
        isbnField.clear();
        tituloField.clear();
        autorCombo.setValue(null);
        categoriaCombo.setValue(null);
        editorialCombo.setValue(null);
        paginasField.clear();
        idiomaCombo.setValue(null);
        anioField.clear();
        estadoCombo.setValue(null);
    }

    private boolean validarCampos() {
        String isbn = isbnField.getText();
        String titulo = tituloField.getText();
        String paginas = paginasField.getText();
        String anio = anioField.getText();

        if (isbn.isEmpty()) {
            ALERT.showWarning("El campo ISBN es obligatorio.");
            return false;
        }

        if (!CISBN.isValidISBN13(isbn)) {
            ALERT.showWarning("El ISBN está mal formado.");
            return false;
        }

        if (titulo.isEmpty()) {
            ALERT.showWarning("El campo Titulo es obligatorio.");
            return false;
        }

        if (paginas.isEmpty()) {
            ALERT.showWarning("El campo Número páginas es obligatorio.");
            return false;
        }

        if (autorCombo.getValue() == null) {
            ALERT.showWarning("El campo Autor es obligatorio.");
            return false;
        }

        if (categoriaCombo.getValue() == null) {
            ALERT.showWarning("El campo Catgoría es obligatorio.");
            return false;
        }

        if (editorialCombo.getValue() == null) {
            ALERT.showWarning("El campo Editorial es obligatorio.");
            return false;
        }
        if (idiomaCombo.getValue() == null) {
            ALERT.showWarning("El campo Idioma es obligatorio.");
            return false;
        }

        if (anio.isEmpty()) {
            ALERT.showWarning("El campo Año publicación es obligatorio.");
            return false;
        }

        if (estadoCombo.getValue() == null) {
            ALERT.showWarning("El campo Estado es obligatorio.");
            return false;
        }

        return true;
    }

}
