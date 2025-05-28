package com.guayand0.librarymanager.model.libro;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.model.autor.Autor;
import com.guayand0.librarymanager.model.categoria.Categoria;
import com.guayand0.librarymanager.model.editorial.Editorial;
import com.guayand0.librarymanager.model.idioma.Idioma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultarDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;

    public List<List<String>> obtenerDatosLibros(StringBuilder query) {
        List<List<String>> libros = new ArrayList<>();

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(String.valueOf(query));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                List<String> fila = new ArrayList<>();
                fila.add(resultSet.getString("ISBN"));
                fila.add(resultSet.getString("titulo"));
                fila.add(resultSet.getString("autor")); // nombre completo concatenado
                fila.add(resultSet.getString("categoria"));
                fila.add(resultSet.getString("editorial"));
                fila.add(resultSet.getString("numero_paginas"));
                fila.add(resultSet.getString("idioma"));
                fila.add(resultSet.getString("anio_publicacion"));
                fila.add(resultSet.getString("estado"));

                libros.add(fila);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }

        return libros;
    }

    public List<Autor> obtenerAutores() {
        List<Autor> autores = new ArrayList<>();
        String sql = "SELECT * FROM Autores";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Autor autor = new Autor(
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("pais"),
                        resultSet.getString("fecha_de_nacimiento")
                );
                autores.add(autor);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); }
            catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) ConnectionDatabase.closeConnection(connection); }
            catch (Exception ex) { ex.printStackTrace(); }
        }

        return autores;
    }

    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Categoria categoria = new Categoria(
                        resultSet.getString("nombre")
                );
                categorias.add(categoria);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }

        return categorias;
    }

    public List<Editorial> obtenerEditoriales() {
        List<Editorial> editoriales = new ArrayList<>();
        String sql = "SELECT * FROM Editoriales";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Editorial editorial = new Editorial(
                        resultSet.getString("nombre")
                );
                editoriales.add(editorial);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }

        return editoriales;
    }

    public List<Idioma> obtenerIdiomas() {
        List<Idioma> idiomas = new ArrayList<>();
        String sql = "SELECT * FROM Idiomas";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Idioma idioma = new Idioma(
                        resultSet.getString("nombre")
                );
                idiomas.add(idioma);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }

        return idiomas;
    }

}
