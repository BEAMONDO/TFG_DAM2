package com.guayand0.librarymanager.db;

import com.guayand0.librarymanager.utils.Alertas;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConnectionDatabase {

    private static String USER;
    private static String PASSWORD;
    private static String URL;

    static {
        try {
            Properties props = new Properties();
            InputStream input = new FileInputStream("database.properties");
            props.load(input);

            USER = props.getProperty("USER");
            PASSWORD = props.getProperty("PASSWORD");
            String database = props.getProperty("DATABASE");
            String host = props.getProperty("HOST");
            String port = props.getProperty("PORT");
            String params = props.getProperty("CONNECTION_PARAMS", "");

            String rawUrl = props.getProperty("URL").replace("%USER%", USER).replace("%PASSWORD%", PASSWORD)
                    .replace("%DATABASE%", database).replace("%HOST%", host).replace("%PORT%", port).replace("%CONNECTION_PARAMS%", params);

            URL = "jdbc:mysql://" + rawUrl;

        } catch (Exception e) {
            Alertas ALERT = new Alertas();
            ALERT.showError("Error cargando archivo de configuración de la base de datos.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crearTablas() {
        String[] sqlStatements = new String[] {
                "CREATE TABLE IF NOT EXISTS Autores (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "nombre varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "apellido varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "pais varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "fecha_de_nacimiento date NOT NULL," +
                        "PRIMARY KEY (ID)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Categorias (" +
                        "id_categoria int NOT NULL AUTO_INCREMENT," +
                        "nombre varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "PRIMARY KEY (id_categoria)," +
                        "UNIQUE KEY nombre (nombre)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Editoriales (" +
                        "id_editorial int NOT NULL AUTO_INCREMENT," +
                        "nombre varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "PRIMARY KEY (id_editorial)," +
                        "UNIQUE KEY nombre (nombre)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Idiomas (" +
                        "id_idioma int NOT NULL AUTO_INCREMENT," +
                        "nombre varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "PRIMARY KEY (id_idioma)," +
                        "UNIQUE KEY nombre (nombre)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Usuarios (" +
                        "DNI varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "nombre varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "apellidos varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "email varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "contrasena varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "telefono varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "direccion varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "fecha_de_nacimiento date NOT NULL," +
                        "fecha_de_registro datetime DEFAULT CURRENT_TIMESTAMP," +
                        "permiso varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Usuario'," +
                        "sexo varchar(25) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "PRIMARY KEY (DNI)," +
                        "UNIQUE KEY email (email)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Libros (" +
                        "ISBN varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "titulo varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "autor int DEFAULT NULL," +
                        "id_categoria int DEFAULT NULL," +
                        "id_editorial int DEFAULT NULL," +
                        "numero_paginas int NOT NULL," +
                        "id_idioma int DEFAULT NULL," +
                        "anio_publicacion int NOT NULL," +
                        "estado enum('disponible','prestado','deteriorado','bloqueado') COLLATE utf8mb4_unicode_ci DEFAULT 'disponible'," +
                        "PRIMARY KEY (ISBN)," +
                        "KEY autor (autor)," +
                        "KEY id_categoria (id_categoria)," +
                        "KEY id_editorial (id_editorial)," +
                        "KEY id_idioma (id_idioma)," +
                        "FOREIGN KEY (autor) REFERENCES Autores(ID) ON DELETE SET NULL," +
                        "FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria) ON DELETE SET NULL," +
                        "FOREIGN KEY (id_editorial) REFERENCES Editoriales(id_editorial) ON DELETE SET NULL," +
                        "FOREIGN KEY (id_idioma) REFERENCES Idiomas(id_idioma) ON DELETE SET NULL" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

                "CREATE TABLE IF NOT EXISTS Prestamos (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "usuario varchar(9) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "libro varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL," +
                        "fecha_prestamo timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                        "fecha_devolucion timestamp NOT NULL," +
                        "fecha_devolucion_real timestamp NULL DEFAULT NULL," +
                        "multa decimal(10,2) DEFAULT '0.00'," +
                        "PRIMARY KEY (ID)," +
                        "KEY prestamos_ibfk_1 (usuario)," +
                        "KEY prestamos_ibfk_2 (libro)," +
                        "FOREIGN KEY (usuario) REFERENCES Usuarios(DNI)," +
                        "FOREIGN KEY (libro) REFERENCES Libros(ISBN)" +
                        ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;",

        "INSERT IGNORE INTO `Usuarios` (`DNI`, `nombre`, `apellidos`, `email`, `contrasena`, `telefono`, `direccion`, `fecha_de_nacimiento`, `fecha_de_registro`, `permiso`, `sexo`)" +
                        "VALUES ('12345678Z', 'Admin', 'Admin', 'admin@admin.es', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', '000000000', 'Admin', '2000-01-01', '2000-01-01 00:00:00', 'Administrador', 'Otro');"
        };

        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            statement = connection.createStatement();
            for (String sql : sqlStatements) {
                statement.execute(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Alertas ALERT = new Alertas();
            ALERT.showError("Error creando tablas: " + e.getMessage());
        } finally {
            try { if (statement != null) statement.close();
            } catch (Exception ex) { ex.printStackTrace(); }
            try { if (connection != null) ConnectionDatabase.closeConnection(connection);
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

}

/*
    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root";
     static final String PASSWORD = "qwewe";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;
*/
