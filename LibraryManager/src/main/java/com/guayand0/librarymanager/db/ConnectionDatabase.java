package com.guayand0.librarymanager.db;

import java.sql.*;

public class ConnectionDatabase {

    static final String USER = "uffejpqe95fnbppa";
    static final String PASSWORD = "Yhwb5UkztDlyOn2CAEDy";
    static final String URL = "jdbc:mysql://uffejpqe95fnbppa:Yhwb5UkztDlyOn2CAEDy@bwexlexwx9kllx98zihv-mysql.services.clever-cloud.com:3306/bwexlexwx9kllx98zihv?useUnicode=true&characterEncoding=UTF-8";

    public static Connection getConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        if (conn != null && !conn.isClosed()) {
            System.out.println("Conexión abierta");
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexion cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error al cerrar conexion");
            }
        }
    }
}

/*
    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root", PASSWORD = "qwewe";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;
*/
