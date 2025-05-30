package com.guayand0.librarymanager.model.prestamo;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrestamoDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    public boolean register(Prestamo prestamo) {
        sql = "INSERT INTO Prestamos (usuario, libro, fecha_prestamo, " +
                "fecha_devolucion, fecha_devolucion_real, multa) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String updateLibroSQL = "UPDATE Libros SET estado = 'Prestado' WHERE ISBN = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            connection.setAutoCommit(false);

            // Insertar préstamo
            statement = connection.prepareStatement(sql);
            statement.setString(1, prestamo.getUsuario());
            statement.setString(2, prestamo.getLibro());
            statement.setString(3, prestamo.getFechaPrestamo());
            statement.setString(4, prestamo.getFechaDevolucion());
            statement.setString(5, prestamo.getFechaDevolucionReal());
            statement.setInt(6, prestamo.getMulta());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            // Actualizar estado del libro
            statement = connection.prepareStatement(updateLibroSQL);
            statement.setString(1, prestamo.getLibro());
            statement.executeUpdate();
            statement.close();

            connection.commit();
            return rowsInserted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (connection != null) connection.rollback();
            } catch (Exception rollbackEx) { rollbackEx.printStackTrace(); }
            return false;
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) {
                connection.setAutoCommit(true);
                ConnectionDatabase.closeConnection(connection);
            }} catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    public boolean delete(String[] datos) {
        sql = "DELETE FROM Prestamos WHERE usuario = ? AND libro = ?";

        String updateLibroSQL = "UPDATE Libros SET estado = 'Disponible' WHERE ISBN = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            connection.setAutoCommit(false);

            // Eliminar préstamo
            statement = connection.prepareStatement(sql);
            statement.setString(1, datos[0]);
            statement.setString(2, datos[1]);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            // Actualizar estado del libro
            statement = connection.prepareStatement(updateLibroSQL);
            statement.setString(1, datos[1]);
            statement.executeUpdate();
            statement.close();

            connection.commit();
            return rowsDeleted > 0;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (connection != null) connection.rollback();
            } catch (Exception rollbackEx) { rollbackEx.printStackTrace(); }
            return false;
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) {
                connection.setAutoCommit(true);
                ConnectionDatabase.closeConnection(connection);
            }} catch (Exception ex) { ex.printStackTrace(); }
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

    public Map<String, String> obtenerLibroISBNTituloDisponibleMap() {
        Map<String, String> mapa = new HashMap<>();
        String dsiponible = "Disponible";
        sql = "SELECT ISBN, titulo FROM Libros WHERE estado = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, dsiponible);
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

    public List<String> obtenerLibrosPrestados(String dni) {
        List<String> lista = new ArrayList<>();
        sql = "SELECT L.titulo FROM Prestamos P JOIN Libros L ON L.ISBN = P.libro " +
                "WHERE P.usuario = ? AND P.fecha_devolucion_real IS NULL";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String titulo = resultSet.getString("titulo");
                lista.add(titulo);
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (statement != null) statement.close(); } catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) ConnectionDatabase.closeConnection(connection); } catch (Exception ex) { ex.printStackTrace(); }
        }

        return lista;
    }

}