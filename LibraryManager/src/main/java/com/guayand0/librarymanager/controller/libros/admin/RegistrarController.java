package com.guayand0.librarymanager.controller.libros.admin;

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

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    LibroDAO libroDAO = new LibroDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();
    private final ComprobarISBN CISBN = new ComprobarISBN();

    private Usuario usuarioLogueado;

    @FXML private TextField isbnField, tituloField, paginasField, anioField;
    @FXML private ComboBox<String> autorCombo, categoriaCombo, editorialCombo, idiomaCombo, estadoCombo;

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
        cargarAutor();
        cargarCatetgoria();
        cargarEditorial();
        cargarIdioma();
    }

    private void cargarAutor() {

    }

    private void cargarCatetgoria() {

    }

    private void cargarEditorial() {

    }

    private void cargarIdioma() {

    }

    @FXML private void onRegisterClick() {
        /*if (!validarCampos()) return;

        String isbn = isbnField.getText();
        String titulo = tituloField.getText();
        int autor = autorCombo.getValue();
        int categoria = categoriaCombo.getValue();
        int editorial = editorialCombo.getValue();
        int paginas = Integer.parseInt(paginasField.getText());
        int idioma = idiomaCombo.getValue();
        int anio = Integer.parseInt(anioField.getText());
        String estado = estadoCombo.getValue();

        Libro libro = new Libro(
                isbn, titulo, autor, categoria, editorial,
                paginas, idioma, anio, estado
        );

        boolean registrado = libroDAO.register(libro);

        if (registrado) {
            ALERT.showInformation("Usuario registrado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar el usuario.");
        }*/
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
