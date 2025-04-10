package com.guayand0.librarymanager.db;

import java.sql.*;
import java.util.Enumeration;

public class ConnectionDatabase {

    static String nombreBaseDatos = "bwexlexwx9kllx98zihv";
    static final String USER = "uffejpqe95fnbppa";
    static final String PASSWORD = "Yhwb5UkztDlyOn2CAEDy";
    static final String URL = "jdbc:mysql://uffejpqe95fnbppa:Yhwb5UkztDlyOn2CAEDy@bwexlexwx9kllx98zihv-mysql.services.clever-cloud.com:3306/bwexlexwx9kllx98zihv";

    // Para usar la base de datos MySQL
    public static Connection getConnection() throws SQLException {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            System.out.println("Loaded driver: " + drivers.nextElement().getClass().getName());
        }

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

/*
    static String nombreBaseDatos = "biblioteca";
    static final String USER = "root", PASSWORD = "qwewe";
    static final String URL = "jdbc:mysql://localhost/" + nombreBaseDatos;
*/
