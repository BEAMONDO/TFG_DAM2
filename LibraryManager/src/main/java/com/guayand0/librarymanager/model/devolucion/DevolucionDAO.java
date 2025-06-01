package com.guayand0.librarymanager.model.devolucion;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.model.prestamo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DevolucionDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    public boolean register(Prestamo prestamo) {
        sql = "UPDATE Prestamos SET fecha_prestamo = ?, fecha_devolucion = ?, " +
                "fecha_devolucion_real = ?, multa = ? WHERE usuario = ? AND libro = ?";

        String updateLibroSQL = "UPDATE Libros SET estado = 'Disponible' WHERE ISBN = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            connection.setAutoCommit(false);

            // Registrar devolucion
            statement = connection.prepareStatement(sql);
            statement.setString(1, prestamo.getFechaPrestamo());
            statement.setString(2, prestamo.getFechaDevolucion());
            statement.setString(3, prestamo.getFechaDevolucionReal());
            statement.setInt(4, prestamo.getMulta());
            statement.setString(5, prestamo.getUsuario());
            statement.setString(6, prestamo.getLibro());

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

    public boolean modify(String DNI, String ISBN, String fechaDevolucionReal, Integer multa) {
        sql = "UPDATE Prestamos SET fecha_devolucion_real = ?, multa = ? WHERE usuario = ? AND libro = ?";

        try {
            connection = ConnectionDatabase.getConnection();

            // Actualizar datos
            statement = connection.prepareStatement(sql);
            statement.setString(1, fechaDevolucionReal);
            statement.setInt(2, multa);
            statement.setString(3, DNI);
            statement.setString(4, ISBN);

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
}
