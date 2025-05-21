package com.guayand0.librarymanager.model.editorial;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EditorialDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public boolean register(Editorial editorial) {
        sql = "INSERT INTO Editoriales (nombre) " + "VALUES (?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(editorial.getNombre()));

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

    public boolean modify(String[] arrayDatos) {
        sql = "UPDATE Editoriales SET nombre = ? WHERE nombre = ?";

        try {
            connection = ConnectionDatabase.getConnection();

            // Actualizar datos
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(arrayDatos[1]));
            statement.setString(2, CP.capitalizar(arrayDatos[0]));

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

    public boolean delete(String nombre) {
        sql = "DELETE FROM Editoriales WHERE nombre = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, nombre);

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
}
