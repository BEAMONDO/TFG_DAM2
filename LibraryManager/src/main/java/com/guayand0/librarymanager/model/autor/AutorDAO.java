package com.guayand0.librarymanager.model.autor;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutorDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public boolean register(Autor autor) {
        sql = "INSERT INTO Autores (nombre, apellido, pais, fecha_de_nacimiento) " +
                "VALUES (?, ?, ?, ?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(autor.getNombre()));
            statement.setString(2, CP.capitalizar(autor.getApellido()));
            statement.setString(3, CP.capitalizar(autor.getPais()));
            statement.setString(4, autor.getFechaDeNacimiento());

            int rowsInserted = statement.executeUpdate();
            statement.close();
            return rowsInserted > 0;
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

    public boolean modify(String[] arrayDatos) {
        sql = "UPDATE Autores SET nombre = ?, apellido = ?, pais = ?, fecha_de_nacimiento = ? WHERE nombre = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(arrayDatos[1]));
            statement.setString(2, CP.capitalizar(arrayDatos[2]));
            statement.setString(3, CP.capitalizar(arrayDatos[3]));
            statement.setString(4, arrayDatos[4]);
            statement.setString(5, arrayDatos[0]);

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

    public boolean delete(String nombre, String apellido) {
        sql = "DELETE FROM Autores WHERE nombre = ? AND apellido = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, apellido);

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
            try {if (statement != null) statement.close();
            } catch (Exception ex) {ex.printStackTrace();}
            try {if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) {ex.printStackTrace();}
        }

        return autores;
    }

    public Map<Integer, String> obtenerAutorIdNombreMap() {
        Map<Integer, String> mapa = new HashMap<>();
        String sql = "SELECT ID, nombre, apellido FROM Autores";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String nombreCompleto = resultSet.getString("nombre") + " " + resultSet.getString("apellido");
                mapa.put(id, nombreCompleto);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) ConnectionDatabase.closeConnection(connection); } catch (Exception ex) { ex.printStackTrace(); }
        }

        return mapa;
    }

}
