package com.guayand0.librarymanager.model;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;
import com.guayand0.librarymanager.utils.EncriptarContrasena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    private Connection connection = null;
    private String sql;

    private final EncriptarContrasena EC = new EncriptarContrasena();
    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public Usuario login(String dniOrEmail, String password) {
        Usuario usuario = null;
        sql = "SELECT * FROM Usuarios WHERE (DNI = ? OR email = ?) AND contrasena = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dniOrEmail);
            statement.setString(2, dniOrEmail);
            statement.setString(3, EC.encriptar(password));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usuario = new Usuario(
                        resultSet.getString("DNI"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("email"),
                        resultSet.getString("contrasena"),
                        resultSet.getString("telefono"),
                        resultSet.getString("direccion"),
                        resultSet.getString("fecha_de_nacimiento"),
                        resultSet.getString("fecha_de_registro")
                );
            }

            resultSet.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return usuario;
    }

    public boolean register(Usuario usuario) {
        sql = "INSERT INTO Usuarios (DNI, nombre, apellidos, email, contrasena," +
                "telefono, direccion, fecha_de_nacimiento, fecha_de_registro) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ConnectionDatabase.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getDNI());
            statement.setString(2, CP.capitalizar(usuario.getNombre()));
            statement.setString(3, CP.capitalizar(usuario.getApellidos()));
            statement.setString(4, usuario.getEmail());
            statement.setString(5, EC.encriptar(usuario.getContrasena()));
            statement.setString(6, usuario.getTelefono());
            statement.setString(7, CP.capitalizar(usuario.getDireccion()));
            statement.setString(8, usuario.getFechaDeNacimiento());
            statement.setString(9, usuario.getFechaDeRegistro());

            int rowsInserted = statement.executeUpdate();
            statement.close();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }
    }

}
