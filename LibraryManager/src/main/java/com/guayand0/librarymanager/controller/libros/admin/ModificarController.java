package com.guayand0.librarymanager.controller.libros.admin;

import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.model.editorial.EditorialDAO;
import com.guayand0.librarymanager.model.idioma.IdiomaDAO;
import com.guayand0.librarymanager.model.libro.Libro;
import com.guayand0.librarymanager.model.libro.LibroDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    LibroDAO libroDAO = new LibroDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField tituloField, paginasField, anioField;
    @FXML private ComboBox<String> isbnCombo, autorCombo, categoriaCombo, editorialCombo, idiomaCombo, estadoCombo;

    private final Map<Integer, String> autorMap = new HashMap<>();
    private final Map<Integer, String> categoriaMap = new HashMap<>();
    private final Map<Integer, String> editorialMap = new HashMap<>();
    private final Map<Integer, String> idiomaMap = new HashMap<>();

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
        cargarDatos();

        isbnCombo.setOnAction(event -> cargarDatosLibros());
    }

    private void aplicarLimitesCaracteres() {
        tituloField.setTextFormatter(LC.createTextFormatter(255));
        paginasField.setTextFormatter(LC.createTextFormatter(5));
        anioField.setTextFormatter(LC.createTextFormatter(4));
    }

    private void cargarDatosLibros() {
        try {
            String isbnSeleccionado = isbnCombo.getValue();
            if (isbnSeleccionado == null || isbnSeleccionado.isEmpty()) return;

            List<Libro> libros = libroDAO.obtenerLibros();
            for (Libro libro : libros) {
                if (libro.getISBN().equals(isbnSeleccionado)) {
                    tituloField.setText(libro.getTitulo());
                    autorCombo.setValue(autorMap.get(libro.getAutor()));
                    categoriaCombo.setValue(categoriaMap.get(libro.getCategoria()));
                    editorialCombo.setValue(editorialMap.get(libro.getEditorial()));
                    idiomaCombo.setValue(idiomaMap.get(libro.getIdioma()));
                    paginasField.setText(String.valueOf(libro.getNumeroPaginas()));
                    anioField.setText(String.valueOf(libro.getAnioPublicacion()));
                    estadoCombo.setValue(libro.getEstado());
                    break;
                }
            }
        } catch (Exception e) {
            ALERT.showError("Error al cargar los datos del libro.");
        }
    }

    private void cargarDatos() {
        cargarISBN();
        cargarAutor();
        cargarCatetgoria();
        cargarEditorial();
        cargarIdioma();
    }

    private void cargarISBN() {
        List<String> isbns = libroDAO.obtenerISBN();
        isbnCombo.getItems().addAll(isbns);
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

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String isbn = isbnCombo.getValue();
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

        String[] datosString = {
                isbn, titulo, estado
        };

        Integer[] datosInt = {
                autorId, categoriaId, editorialId,
                paginas, idiomaId, anio
        };

        boolean modificado = libroDAO.modify(datosString, datosInt);

        if (modificado) {
            ALERT.showInformation("Libro modificado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al modificar el libro.");
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
        isbnCombo.setValue(null);
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
        String titulo = tituloField.getText();
        String paginas = paginasField.getText();
        String anio = anioField.getText();

        if (isbnCombo.getValue() == null) {
            ALERT.showWarning("El campo ISBN es obligatorio.");
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
