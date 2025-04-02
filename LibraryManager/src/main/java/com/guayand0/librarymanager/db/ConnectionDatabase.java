package com.guayand0.librarymanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionDatabase {

    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root", PASSWORD = "";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;

    // Para usar la base de datos MySQL
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Para usar la base de datos SQLite
    /*public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + nombreBaseDatos + ".db");
    }*/

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createDatabaseSQLite() {
        try {
            Connection connection = getConnection();
            Statement statement = connection.createStatement();

            // Crear tabla Usuarios
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "DNI TEXT PRIMARY KEY, " +
                    "nombre TEXT NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "email TEXT NOT NULL UNIQUE, " +
                    "telefono TEXT NOT NULL, " +
                    "direccion TEXT NOT NULL, " +
                    "fecha_de_nacimiento DATE NOT NULL, " +
                    "fecha_de_registro DATE DEFAULT CURRENT_DATE" +
                    ");");

            // Crear tabla Autores
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Autores (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "nombre TEXT NOT NULL, " +
                    "apellido TEXT NOT NULL, " +
                    "pais TEXT NOT NULL, " +
                    "fecha_de_nacimiento DATE NOT NULL" +
                    ");");

            // Crear tabla Libros
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Libros (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "ISBN TEXT UNIQUE NOT NULL, " +
                    "titulo TEXT NOT NULL, " +
                    "autor INTEGER NOT NULL, " +
                    "categoria TEXT NOT NULL, " +
                    "editorial TEXT NOT NULL, " +
                    "numero_paginas INTEGER NOT NULL, " +
                    "idioma TEXT NOT NULL, " +
                    "anio_publicacion INTEGER NOT NULL, " +
                    "estado TEXT CHECK(estado IN ('disponible', 'prestado', 'deteriorado', 'bloqueado')) DEFAULT 'disponible', " +
                    "FOREIGN KEY (autor) REFERENCES Autores(ID) ON DELETE CASCADE" +
                    ");");

            // Crear tabla Prestamos
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Prestamos (" +
                    "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
                    "usuario TEXT NOT NULL, " +
                    "libro INTEGER NOT NULL, " +
                    "fecha_prestamo DATE NOT NULL, " +
                    "fecha_devolucion DATE NOT NULL, " +
                    "fecha_devolucion_real DATE, " +
                    "multa DECIMAL(10, 2) DEFAULT 0.00, " +
                    "FOREIGN KEY (usuario) REFERENCES Usuarios(DNI) ON DELETE CASCADE, " +
                    "FOREIGN KEY (libro) REFERENCES Libros(ID) ON DELETE CASCADE" +
                    ");");

/*            // Insertar datos en Usuarios
            statement.executeUpdate("INSERT INTO Usuarios (DNI, nombre, apellido, email, telefono, direccion, fecha_de_nacimiento) VALUES " +
                    "('12345678A', 'Juan', 'Pérez', 'juan.perez@example.com', '555-1234', 'Calle Falsa 123', '1990-01-01'), " +
                    "('87654321B', 'Ana', 'López', 'ana.lopez@example.com', '555-5678', 'Av. Siempreviva 742', '1985-05-15'), " +
                    "('45678912C', 'Carlos', 'Gómez', 'carlos.gomez@example.com', '555-8765', 'Paseo del Sol 45', '1992-03-22'), " +
                    "('78912345D', 'María', 'Hernández', 'maria.hernandez@example.com', '555-4321', 'Calle Luna 78', '1988-07-10'), " +
                    "('32165498E', 'Lucía', 'Martínez', 'lucia.martinez@example.com', '555-9876', 'Calle Estrella 90', '1995-11-30');");

            // Insertar datos en Autores
            statement.executeUpdate("INSERT INTO Autores (nombre, apellido, pais, fecha_de_nacimiento) VALUES " +
                    "('Gabriel', 'García Márquez', 'Colombia', '1927-03-06'), " +
                    "('Isabel', 'Allende', 'Chile', '1942-08-02'), " +
                    "('Mario', 'Vargas Llosa', 'Perú', '1936-03-28'), " +
                    "('Jorge Luis', 'Borges', 'Argentina', '1899-08-24'), " +
                    "('Julio', 'Cortázar', 'Argentina', '1914-08-26');");

            // Insertar datos en Libros
            statement.executeUpdate("INSERT INTO Libros (ISBN, titulo, autor, categoria, editorial, numero_paginas, idioma, anio_publicacion) VALUES " +
                    "('978-1-56619-909-4', 'Cien años de soledad', 1, 'Novela', 'Sudamericana', 471, 'Español', 1967), " +
                    "('978-84-204-8216-3', 'La casa de los espíritus', 2, 'Novela', 'Plaza & Janés', 368, 'Inglés', 1982), " +
                    "('978-84-376-0494-7', 'La ciudad y los perros', 3, 'Novela', 'Seix Barral', 408, 'Español', 1963), " +
                    "('978-84-339-0793-8', 'Ficciones', 4, 'Relato', 'Emecé Editores', 174, 'Español', 1944), " +
                    "('978-84-339-7147-2', 'Rayuela', 5, 'Novela', 'Sudamericana', 736, 'Español', 1963);");

            // Insertar datos en Prestamos
            statement.executeUpdate("INSERT INTO Prestamos (usuario, libro, fecha_prestamo, fecha_devolucion) VALUES " +
                    "('12345678A', 1, '2024-11-01', '2024-11-15'), " +
                    "('87654321B', 2, '2024-11-05', '2024-11-20'), " +
                    "('45678912C', 3, '2024-11-10', '2024-11-25'), " +
                    "('78912345D', 4, '2024-11-12', '2024-11-26'), " +
                    "('32165498E', 5, '2024-11-15', '2024-11-30');");
*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}