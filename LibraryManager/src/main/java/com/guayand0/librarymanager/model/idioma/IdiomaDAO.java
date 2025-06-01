package com.guayand0.librarymanager.model.idioma;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdiomaDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public boolean register(Idioma idioma) {
        sql = "INSERT INTO Idiomas (nombre) " + "VALUES (?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(idioma.getNombre()));

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
        sql = "UPDATE Idiomas SET nombre = ? WHERE nombre = ?";

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
        sql = "DELETE FROM Idiomas WHERE nombre = ?";

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

    public Map<Integer, String> obtenerIdiomaIdNombreMap() {
        Map<Integer, String> mapa = new HashMap<>();
        String sql = "SELECT id_idioma, nombre FROM Idiomas";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_idioma");
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
