package com.guayand0.librarymanager.model.libro;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultarDAO {

    private Connection connection = null;
    private PreparedStatement statement = null;

    public List<List<String>> obtenerDatosLibros(StringBuilder query) {
        List<List<String>> libros = new ArrayList<>();

        try {
            connection = ConnectionDatabase.getConnection();
            statement = connection.prepareStatement(String.valueOf(query));
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                List<String> fila = new ArrayList<>();
                fila.add(resultSet.getString("ISBN"));
                fila.add(resultSet.getString("titulo"));
                fila.add(resultSet.getString("autor"));
                fila.add(resultSet.getString("categoria"));
                fila.add(resultSet.getString("editorial"));
                fila.add(resultSet.getString("numero_paginas"));
                fila.add(resultSet.getString("idioma"));
                fila.add(resultSet.getString("anio_publicacion"));
                fila.add(resultSet.getString("estado"));

                libros.add(fila);
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

        return libros;
    }

}
