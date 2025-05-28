package com.guayand0.librarymanager.controller.libros.user;

import com.guayand0.librarymanager.model.libro.ConsultarDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Ventanas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Map;

public class TablaConsultaController {

    private final ConsultarDAO consultarDAO = new ConsultarDAO();

    @FXML private Label usuario;
    private Usuario usuarioLogueado;

    @FXML private TableView<List<String>> librosTable;
    @FXML private TableColumn<List<String>, String> isbnCol;
    @FXML private TableColumn<List<String>, String> tituloCol;
    @FXML private TableColumn<List<String>, String> autorCol;
    @FXML private TableColumn<List<String>, String> categoriaCol;
    @FXML private TableColumn<List<String>, String> editorialCol;
    @FXML private TableColumn<List<String>, Integer> paginasCol;
    @FXML private TableColumn<List<String>, String> idiomaCol;
    @FXML private TableColumn<List<String>, Integer> anioCol;
    @FXML private TableColumn<List<String>, String> estadoCol;

    private String[] datosString;
    private Integer[] datosInt;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public void setDatosString(String[] datos) {
        this.datosString = datos;
    }

    public void setDatosInt(Integer[] datos) {
        this.datosInt = datos;
    }

    public void initData() {

        String isbn = datosString[0];
        String titulo = datosString[1];
        String autor = datosString[2]; int autorID = datosInt[0];
        String categoria = datosString[3]; int categoriaID = datosInt[1];
        String editorial = datosString[4]; int editorialID = datosInt[2];
        String paginas = datosString[5]; String paginasVariable = datosString[9];
        String idioma = datosString[6]; int idiomaID = datosInt[3];
        String anio = datosString[7]; String anioVariable = datosString[10];
        String estado = datosString[8];

        if (paginas.trim().isEmpty()) paginas = "-1";
        if (anio.trim().isEmpty()) anio = "-1";

        StringBuilder query = new StringBuilder(
                "SELECT L.ISBN, L.titulo, CONCAT(A.nombre, ' ', A.apellido) AS autor, " +
                "C.nombre AS categoria, E.nombre AS editorial, L.numero_paginas, " +
                "I.nombre AS idioma, L.anio_publicacion, L.estado FROM Libros L " +
                "LEFT JOIN Autores A ON L.autor = A.ID " +
                "LEFT JOIN Categorias C ON L.id_categoria = C.id_categoria " +
                "LEFT JOIN Editoriales E ON L.id_editorial = E.id_editorial " +
                "LEFT JOIN Idiomas I ON L.id_idioma = I.id_idioma " +
                "WHERE 1=1");

        if (isbn != null && !isbn.equals("-1")) {
            query.append(" AND L.ISBN = '").append(isbn).append("'");
        }
        if (titulo != null && !titulo.equals("-1")) {
            query.append(" AND L.titulo = '").append(titulo).append("'");
        }
        if (autor != null && !autor.equals("-1")) {
            query.append(" AND L.autor = ").append(autorID);
        }
        if (categoria != null && !categoria.equals("-1")) {
            query.append(" AND L.id_categoria = ").append(categoriaID);
        }
        if (editorial != null && !editorial.equals("-1")) {
            query.append(" AND L.id_editorial = ").append(editorialID);
        }
        if (paginasVariable != null && !paginas.equals("-1")) {
            if (paginasVariable.contains("Más")) {
                query.append(" AND L.numero_paginas > ").append(paginas);
            } else if (paginasVariable.contains("Menos")) {
                query.append(" AND L.numero_paginas < ").append(paginas);
            } else if (paginasVariable.contains("Exactamente")) {
                query.append(" AND L.numero_paginas = ").append(paginas);
            }
        }
        if (idioma != null && !idioma.equals("-1")) {
            query.append(" AND L.id_idioma = ").append(idiomaID);
        }
        if (anioVariable != null && !anio.equals("-1")) {
            if (anioVariable.contains("Antes")) {
                query.append(" AND L.anio_publicacion < ").append(anio);
            } else if (anioVariable.contains("Después")) {
                query.append(" AND L.anio_publicacion > ").append(anio);
            } else if (anioVariable.contains("Exactamente")) {
                query.append(" AND L.anio_publicacion = ").append(anio);
            }
        }
        if (estado != null && !estado.equals("-1")) {
            query.append(" AND L.estado = '").append(estado).append("'");
        }

        // librosTable debe ser TableView<List<String>>
        List<List<String>> libros = consultarDAO.obtenerDatosLibros(query);

        // Para cada columna, define el cellValueFactory extrayendo el índice correcto de la lista

        isbnCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(0)));
        tituloCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(1)));
        autorCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(2)));
        categoriaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(3)));
        editorialCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(4)));

        // paginasCol es Integer, si quieres usar String hazlo así:
        paginasCol.setCellValueFactory(cellData -> {
            String val = cellData.getValue().get(5);
            Integer intVal;
            try {
                intVal = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                intVal = 0; // o null, según convenga
            }
            return new javafx.beans.property.SimpleObjectProperty<>(intVal);
        });

        idiomaCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(6)));

        anioCol.setCellValueFactory(cellData -> {
            String val = cellData.getValue().get(7);
            Integer intVal;
            try {
                intVal = Integer.parseInt(val);
            } catch (NumberFormatException e) {
                intVal = 0;
            }
            return new javafx.beans.property.SimpleObjectProperty<>(intVal);
        });

        estadoCol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().get(8)));

        // Finalmente, carga la tabla
        librosTable.getItems().setAll(libros);

    }
}
