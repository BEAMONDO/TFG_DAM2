package org.guayando.librarymanager.model;

import org.guayando.librarymanager.db.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;

public class AutorDAO {

    private Connection connection = null;
    private final ArrayList<Autor> list = new ArrayList<>();
    private String sql;

    public ArrayList<Autor> selectNombresAutores() {
        try {
            connection = ConnectionDatabase.getConnection();

            if (connection != null) {
                sql = "SELECT nombre,apellido FROM Autores ORDER BY nombre ASC";
                saveNames(0);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return list;
    }

    private void saveNames(Object parametro) {
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            if (parametro instanceof Integer) {
                if ((Integer) parametro != 0) {
                    pst.setInt(1, (Integer) parametro); // Usamos el parámetro como parámetro
                }
            } else if (parametro instanceof String) {
                pst.setString(1, (String) parametro); // Usamos el parámetro como parámetro
            } else {
                throw new IllegalArgumentException("El parámetro debe ser de tipo Integer o String");
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Autor autor = new Autor();
                autor.setNombre(rs.getString("nombre"));
                autor.setApellido(rs.getString("apellido"));
                list.add(autor);
            }
        } catch (Exception e) {
            System.out.println("Error X: " + e.getMessage());
        }
    }
}