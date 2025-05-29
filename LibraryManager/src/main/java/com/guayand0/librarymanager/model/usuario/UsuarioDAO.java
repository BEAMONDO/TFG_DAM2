package com.guayand0.librarymanager.model.usuario;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;
import com.guayand0.librarymanager.utils.EncriptarContrasena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;
    private String sql;

    private final Alertas ALERT = new Alertas();
    private final EncriptarContrasena EC = new EncriptarContrasena();
    private final CapitalizarPalabra CP = new CapitalizarPalabra();

    public Usuario login(String dniOrEmail, String password) {
        Usuario usuario = null;
        sql = "SELECT * FROM Usuarios WHERE (DNI = ? OR email = ?) AND contrasena = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
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
                        resultSet.getString("fecha_de_registro"),
                        resultSet.getString("permiso"),
                        resultSet.getString("sexo")
                );
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

        return usuario;
    }

    public boolean register(Usuario usuario) {
        sql = "INSERT INTO Usuarios (DNI, nombre, apellidos, email, contrasena," +
                "telefono, direccion, fecha_de_nacimiento, fecha_de_registro, permiso, sexo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, usuario.getDNI());
            statement.setString(2, CP.capitalizar(usuario.getNombre()));
            statement.setString(3, CP.capitalizar(usuario.getApellidos()));
            statement.setString(4, usuario.getEmail());
            statement.setString(5, EC.encriptar(usuario.getContrasena()));
            statement.setString(6, usuario.getTelefono());
            statement.setString(7, CP.capitalizar(usuario.getDireccion()));
            statement.setString(8, usuario.getFechaDeNacimiento());
            statement.setString(9, usuario.getFechaDeRegistro());
            statement.setString(10, usuario.getPermiso());
            statement.setString(11, usuario.getSexo());

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

    public boolean modify(String[] arrayDatos, String contrasenaActualEncriptada, String permiso) {
        sql = "SELECT contrasena FROM Usuarios WHERE DNI = ?";

        try {
            connection = ConnectionDatabase.getConnection();

            // Verificar contraseña actual
            try (PreparedStatement checkStmt = connection.prepareStatement(sql)) {
                checkStmt.setString(1, arrayDatos[0]);
                ResultSet resultSet = checkStmt.executeQuery();

                if (resultSet.next()) {
                    if (!permiso.equalsIgnoreCase("Administrador")) {
                        String contrasenaEnBD = resultSet.getString("contrasena");
                        if (!contrasenaEnBD.equals(contrasenaActualEncriptada)) {
                            ALERT.showError("La contraseña actual es incorrecta.\n" + contrasenaEnBD + "\n" + contrasenaActualEncriptada);
                            return false;
                        }
                    }
                } else {
                    ALERT.showError("No se encontró el usuario.");
                    return false;
                }
            }

            sql = "UPDATE Usuarios SET nombre = ?, apellidos = ?, email = ?, contrasena = ?, telefono = ?, " +
                    "direccion = ?, fecha_de_nacimiento = ?, sexo = ?, permiso = ? WHERE DNI = ?";

            statement = connection.prepareStatement(sql);
            statement.setString(1, CP.capitalizar(arrayDatos[1]));
            statement.setString(2, CP.capitalizar(arrayDatos[2]));
            statement.setString(3, arrayDatos[3]);
            statement.setString(4, EC.encriptar(arrayDatos[5]));
            statement.setString(5, arrayDatos[6]);
            statement.setString(6, CP.capitalizar(arrayDatos[7]));
            statement.setString(7, arrayDatos[8]);
            statement.setString(8, arrayDatos[9]);
            statement.setString(9, arrayDatos[10]);
            statement.setString(10, arrayDatos[0]);

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

    public boolean delete(String dni) {
        sql = "DELETE FROM Usuarios WHERE DNI = ?";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, dni);

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

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios";

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Usuario usuario = new Usuario(
                        resultSet.getString("DNI"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("email"),
                        resultSet.getString("contrasena"),
                        resultSet.getString("telefono"),
                        resultSet.getString("direccion"),
                        resultSet.getString("fecha_de_nacimiento"),
                        resultSet.getString("fecha_de_registro"),
                        resultSet.getString("permiso"),
                        resultSet.getString("sexo")
                );
                usuarios.add(usuario);
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

        return usuarios;
    }

}
