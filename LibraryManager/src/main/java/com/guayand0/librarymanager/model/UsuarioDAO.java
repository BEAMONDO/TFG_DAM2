package com.guayand0.librarymanager.model;

import com.guayand0.librarymanager.db.ConnectionDatabase;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.CapitalizarPalabra;
import com.guayand0.librarymanager.utils.EncriptarContrasena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    private Connection connection = null;
    private String sql;

    private final Alertas ALERT = new Alertas();
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
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return usuario;
    }

    public boolean register(Usuario usuario) {
        sql = "INSERT INTO Usuarios (DNI, nombre, apellidos, email, contrasena," +
                "telefono, direccion, fecha_de_nacimiento, fecha_de_registro, permiso, sexo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            statement.setString(10, usuario.getPermiso());
            statement.setString(11, usuario.getSexo());

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

    public boolean modify(Usuario usuario, String contrasenaActual) {
        sql = "SELECT contrasena FROM Usuarios WHERE DNI = ?";

        // Mostrar valores en consola
        System.out.println("DEBUG UsuarioDAO -- "
            + "DNI: " + usuario.getDNI()
            + ", Nombre: " + usuario.getNombre()
            + ", Apellidos: " + usuario.getApellidos()
            + ", Email: " + usuario.getEmail()
            + ", NuevaContraseña: " + usuario.getContrasena()
            + ", Teléfono: " + usuario.getTelefono()
            + ", Dirección: " + usuario.getDireccion()
            + ", FechaNacimiento: " + usuario.getFechaDeNacimiento()
            + ", Sexo: " + usuario.getSexo()
            + ", ContraseñaActual: " + contrasenaActual
        );

        try {
            connection = ConnectionDatabase.getConnection();

            // Verificar contraseña actual
            PreparedStatement checkStmt = connection.prepareStatement(sql);
            checkStmt.setString(1, usuario.getDNI());
            ResultSet resultSet = checkStmt.executeQuery();

            String contrasenaEnBD = resultSet.getString("contrasena");
            String contrasenaActualEncriptada = EC.encriptar(contrasenaActual);

            if (!contrasenaEnBD.equals(contrasenaActualEncriptada)) {
                resultSet.close();
                checkStmt.close();
                ALERT.showError("La contraseña actual es incorrecta.");
                return false; // Contraseña actual incorrecta
            }

            resultSet.close();
            checkStmt.close();

            // Actualizar datos
            sql = "UPDATE Usuarios SET nombre = ?, apellidos = ?, email = ?, contrasena = ?, telefono = ?, " +
                    "direccion = ?, fecha_de_nacimiento = ?, sexo = ? WHERE DNI = ?";

            PreparedStatement updateStmt = connection.prepareStatement(sql);
            updateStmt.setString(1, CP.capitalizar(usuario.getNombre()));
            updateStmt.setString(2, CP.capitalizar(usuario.getApellidos()));
            updateStmt.setString(3, usuario.getEmail());
            updateStmt.setString(4, EC.encriptar(usuario.getContrasena()));
            updateStmt.setString(5, usuario.getTelefono());
            updateStmt.setString(6, CP.capitalizar(usuario.getDireccion()));
            updateStmt.setString(7, usuario.getFechaDeNacimiento());
            updateStmt.setString(8, usuario.getSexo());
            updateStmt.setString(9, usuario.getDNI());

            int rowsUpdated = updateStmt.executeUpdate();
            updateStmt.close();

            return rowsUpdated > 0;
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
