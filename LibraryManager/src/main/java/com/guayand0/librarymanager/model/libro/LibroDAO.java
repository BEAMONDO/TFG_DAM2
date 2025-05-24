package com.guayand0.librarymanager.model.libro;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.model.idioma.Idioma;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public boolean register(Libro libro) {
        sql = "INSERT INTO Libros (ISBN, titulo, autor, id_categoria, id_editorial," +
                "numero_paginas, id_idioma, anio_publicacion, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, libro.getISBN());
            statement.setString(2, CP.capitalizar(libro.getTitulo()));
            statement.setInt(3, libro.getAutor());
            statement.setInt(4, libro.getCategoria());
            statement.setInt(5, libro.getEditorial());
            statement.setInt(6, libro.getNumeroPaginas());
            statement.setInt(7, libro.getIdioma());
            statement.setInt(8, libro.getAnioPublicacion());
            statement.setString(9, CP.capitalizar(libro.getEstado()));

            int rowsInserted = statement.executeUpdate();
            statement.close();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public boolean modify(String[] arrayDatosString, Integer[] arrayDatosInt) {
        sql = "UPDATE Libros SET titulo = ?, autor = ?, id_categoria = ?, id_editorial = ?," +
                "numero_paginas = ?, id_idioma = ?, anio_publicacion = ?, estado = ? WHERE ISBN = ?";

        try {
            connection = ConnectionDatabase.getConnection();

            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(arrayDatosString[1]));
            statement.setInt(2, arrayDatosInt[0]);
            statement.setInt(3, arrayDatosInt[1]);
            statement.setInt(4, arrayDatosInt[2]);
            statement.setInt(5, arrayDatosInt[3]);
            statement.setInt(6, arrayDatosInt[4]);
            statement.setInt(7, arrayDatosInt[5]);
            statement.setString(8, CP.capitalizar(arrayDatosString[2]));
            statement.setString(9, arrayDatosString[0]);

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try { if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public boolean delete(String titulo) {
        sql = "DELETE FROM Libros WHERE titulo = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, titulo);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try { if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }
    }

    public List<String> obtenerTitulos() {
        List<String> nombreLibros = new ArrayList<>();
        String sql = "SELECT titulo FROM Libros";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String libro = resultSet.getString("titulo");
                nombreLibros.add(libro);
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

        return nombreLibros;
    }

    public List<String> obtenerISBN() {
        List<String> nombreLibros = new ArrayList<>();
        String sql = "SELECT ISBN FROM Libros";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String libro = resultSet.getString("ISBN");
                nombreLibros.add(libro);
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

        return nombreLibros;
    }

    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Libro libro = new Libro(
                        resultSet.getString("ISBN"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("autor"),
                        resultSet.getInt("id_categoria"),
                        resultSet.getInt("id_editorial"),
                        resultSet.getInt("numero_paginas"),
                        resultSet.getInt("id_idioma"),
                        resultSet.getInt("anio_publicacion"),
                        resultSet.getString("estado")
                );
                libros.add(libro);
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
}
