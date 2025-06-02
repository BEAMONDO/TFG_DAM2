package com.guayand0.librarymanager.db;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
            throw new RuntimeException("Error cargando archivo de configuración", e);
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
}

/*
    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root";
     static final String PASSWORD = "qwewe";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;
*/
