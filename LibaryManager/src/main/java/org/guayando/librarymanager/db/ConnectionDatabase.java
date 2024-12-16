package org.guayando.librarymanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root", PASSWORD = "";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}