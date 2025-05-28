package com.guayand0.librarymanager.controller.libros.user;

import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.model.libro.Libro;
import com.guayand0.librarymanager.model.libro.LibroDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsultarController {

    private final Alertas ALERT = new Alertas();
    LibroDAO libroDAO = new LibroDAO();
    private final Ventanas VENTANA = new Ventanas();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField paginasField, anioField;
    @FXML private ComboBox<String> isbnCombo, tituloCombo, autorCombo, categoriaCombo, editorialCombo, idiomaCombo, paginasCombo, anioCombo, estadoCombo;

    private final Map<Integer, String> autorMap = new HashMap<>();
    private final Map<Integer, String> categoriaMap = new HashMap<>();
    private final Map<Integer, String> editorialMap = new HashMap<>();
    private final Map<Integer, String> idiomaMap = new HashMap<>();

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public void initData() {}

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
        cargarDatos();

        isbnCombo.setOnAction(event -> cargarDatosLibros("ISBN"));
        tituloCombo.setOnAction(event -> cargarDatosLibros("TITULO"));
    }

    private void aplicarLimitesCaracteres() {
        paginasField.setTextFormatter(LC.createTextFormatter(5));
        anioField.setTextFormatter(LC.createTextFormatter(4));
    }

    private void cargarDatosLibros(String dato) {
        try {
            List<Libro> libros = libroDAO.obtenerLibros();

            if (dato.equals("ISBN")) {
                String isbnSeleccionado = isbnCombo.getValue();
                if (isbnSeleccionado == null || isbnSeleccionado.isEmpty()) return;

                for (Libro libro : libros) {
                    if (libro.getISBN().equals(isbnSeleccionado)) {
                        tituloCombo.setValue(libro.getTitulo());
                        autorCombo.setValue(autorMap.get(libro.getAutor()));
                        categoriaCombo.setValue(categoriaMap.get(libro.getCategoria()));
                        editorialCombo.setValue(editorialMap.get(libro.getEditorial()));
                        idiomaCombo.setValue(idiomaMap.get(libro.getIdioma()));
                        paginasCombo.setValue("Exactamente");
                        paginasField.setText(String.valueOf(libro.getNumeroPaginas()));
                        anioCombo.setValue("Exactamente");
                        anioField.setText(String.valueOf(libro.getAnioPublicacion()));
                        estadoCombo.setValue(libro.getEstado());
                        break;
                    }
                }
            } else if (dato.equals("TITULO")) {
                String tituloSeleccionado = tituloCombo.getValue();
                if (tituloSeleccionado == null || tituloSeleccionado.isEmpty()) return;

                for (Libro libro : libros) {
                    if (libro.getTitulo().equals(tituloSeleccionado)) {
                        isbnCombo.setValue(libro.getISBN());
                        autorCombo.setValue(autorMap.get(libro.getAutor()));
                        categoriaCombo.setValue(categoriaMap.get(libro.getCategoria()));
                        editorialCombo.setValue(editorialMap.get(libro.getEditorial()));
                        idiomaCombo.setValue(idiomaMap.get(libro.getIdioma()));
                        paginasCombo.setValue("Exactamente");
                        paginasField.setText(String.valueOf(libro.getNumeroPaginas()));
                        anioCombo.setValue("Exactamente");
                        anioField.setText(String.valueOf(libro.getAnioPublicacion()));
                        estadoCombo.setValue(libro.getEstado());
                        break;
                    }
                }
            }

        } catch (Exception e) {
            ALERT.showError("Error al cargar los datos del libro.");
        }
    }

    private void cargarDatos() {
        new Thread(() -> {
            cargarISBN_Titulo();
            cargarAutor();
            cargarCatetgoria();
            cargarEditorial();
            cargarIdioma();
        }).start();
    }

    private void cargarISBN_Titulo() {
        List<Libro> libros = libroDAO.obtenerLibros();
        List<String> isbns = new ArrayList<>();
        List<String> titulos = new ArrayList<>();

        for (Libro libro : libros) {
            isbns.add(libro.getISBN());
            titulos.add(libro.getTitulo());
        }

        isbnCombo.getItems().addAll(isbns);
        tituloCombo.getItems().addAll(titulos);
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

    @FXML private void onConsultarClick() {
        String isbn = isbnCombo.getValue();
        String titulo = tituloCombo.getValue();
        String autor = autorCombo.getValue();
        String categoria = categoriaCombo.getValue();
        String editorial = editorialCombo.getValue();
        String paginas = paginasField.getText();
        String idioma = idiomaCombo.getValue();
        String anio = anioField.getText();
        String estado = estadoCombo.getValue();

        int autorId = getKeyByValue(autorMap, autor);
        int categoriaId = getKeyByValue(categoriaMap, categoria);
        int editorialId = getKeyByValue(editorialMap, editorial);
        int idiomaId = getKeyByValue(idiomaMap, idioma);

        String paginasC = paginasCombo.getValue();
        String anioC = anioCombo.getValue();

        String[] datosString = {
                isbn, titulo, autor, categoria, editorial, paginas, idioma, anio, estado, paginasC, anioC
        };

        Integer[] datosInt = {
                autorId, categoriaId, editorialId, idiomaId
        };

        VENTANA.consultasWindow(usuarioLogueado, datosString, datosInt);
    }

    @FXML private void onLimpiarClick() {
        limpiarCampos();
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
        isbnCombo.setValue(null);
        tituloCombo.setValue(null);
        autorCombo.setValue(null);
        categoriaCombo.setValue(null);
        editorialCombo.setValue(null);
        paginasCombo.setValue(null);
        paginasField.setText("");
        idiomaCombo.setValue(null);
        anioCombo.setValue(null);
        anioField.setText("");
        estadoCombo.setValue(null);
    }

}
