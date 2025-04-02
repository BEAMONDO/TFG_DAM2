package com.guayand0.librarymanager.model;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAO {

    private Connection connection = null;
    private final ArrayList<Usuario> list = new ArrayList<>();
    private String sql;

    public ArrayList<Usuario> selectNombresUsuarios() {
        try {
            connection = ConnectionDatabase.getConnection();

            if (connection != null) {
                sql = "SELECT nombre,apellido FROM Usuarios ORDER BY nombre ASC";
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
                Usuario usuario = new Usuario();
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                list.add(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error X: " + e.getMessage());
        }
    }
}
