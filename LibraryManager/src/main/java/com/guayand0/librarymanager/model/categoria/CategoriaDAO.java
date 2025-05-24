package com.guayand0.librarymanager.model.categoria;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public boolean register(Categoria categoria) {
        sql = "INSERT INTO Categorias (nombre) " + "VALUES (?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(categoria.getNombre()));

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
        sql = "UPDATE Categorias SET nombre = ? WHERE nombre = ?";

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
        sql = "DELETE FROM Categorias WHERE nombre = ?";

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

    public Map<Integer, String> obtenerCategoriaIdNombreMap() {
        Map<Integer, String> mapa = new HashMap<>();
        String sql = "SELECT id_categoria, nombre FROM Categorias";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_categoria");
                String nombre = resultSet.getString("nombre");
                mapa.put(id, nombre);
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
