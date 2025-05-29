package com.guayand0.librarymanager.model.prestamo;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class PrestamoDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    public boolean register(Prestamo prestamo) {
        sql = "INSERT INTO Prestamos (usuario, libro, fecha_prestamo, " +
                "fecha_devolucion, fecha_devolucion_real, multa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, prestamo.getUsuario());
            statement.setString(2,prestamo.getLibro());
            statement.setString(3, prestamo.getFechaPrestamo());
            statement.setString(4, prestamo.getFechaDevolucion());
            statement.setString(5, prestamo.getFechaDevolucionReal());
            statement.setInt(6, prestamo.getMulta());

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

    public Map<String, String> obtenerUsuarioDNINombreMap() {
        Map<String, String> mapa = new HashMap<>();
        sql = "SELECT DNI, nombre, apellidos FROM Usuarios";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String DNI = resultSet.getString("DNI");
                String nombreCompleto = resultSet.getString("nombre") + " " + resultSet.getString("apellidos");
                mapa.put(DNI, nombreCompleto);
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

    public Map<String, String> obtenerLibroISBNTituloMap() {
        Map<String, String> mapa = new HashMap<>();
        sql = "SELECT ISBN, titulo FROM Libros";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("ISBN");
                String titulo = resultSet.getString("titulo");
                mapa.put(id, titulo);
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