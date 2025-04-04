package com.guayand0.librarymanager.model;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {

    private Connection connection = null;
    private String sql;

    public ArrayList<Libro> selectTitulos(String textoBusqueda, String campoBusqueda) {
        ArrayList<Libro> list = new ArrayList<>();

        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                // Construir la consulta SQL según el campo de búsqueda
                switch (campoBusqueda.toLowerCase()) {
                    case "isbn":
                        sql = "SELECT titulo FROM Libros WHERE ISBN LIKE ? ORDER BY titulo ASC";
                        break;
                    case "autor":
                        sql = "SELECT titulo FROM Libros WHERE autor LIKE ? ORDER BY titulo ASC";
                        //sql = "SELECT L.titulo, A.nombre, A.apellido FROM Libros L JOIN Autores A ON L.autor = A.ID WHERE A.nombre LIKE ? OR A.apellido LIKE ? ORDER BY L.titulo ASC";
                        break;
                    case "categoría":
                        sql = "SELECT titulo FROM Libros WHERE categoria LIKE ? ORDER BY titulo ASC";
                        break;
                    case "editorial":
                        sql = "SELECT titulo FROM Libros WHERE editorial LIKE ? ORDER BY titulo ASC";
                        break;
                    case "número de páginas":
                        if (textoBusqueda.startsWith("<") || textoBusqueda.startsWith(">")) {
                            // Si la operación tiene < o >, se usa <= o >=
                            if (textoBusqueda.startsWith("<")) {
                                sql = "SELECT titulo FROM Libros WHERE numero_paginas <= ? ORDER BY titulo ASC";
                            } else if (textoBusqueda.startsWith(">")) {
                                sql = "SELECT titulo FROM Libros WHERE numero_paginas >= ? ORDER BY titulo ASC";
                            }
                        } else {
                            // Si no tiene < o >, se usa LIKE
                            sql = "SELECT titulo FROM Libros WHERE numero_paginas LIKE ? ORDER BY titulo ASC";
                        }
                        break;
                    case "idioma":
                        sql = "SELECT titulo FROM Libros WHERE idioma LIKE ? ORDER BY titulo ASC";
                        break;
                    case "año de publicación":
                        if (textoBusqueda.startsWith("<") || textoBusqueda.startsWith(">")) {
                            // Si la operación tiene < o >, se usa <= o >=
                            if (textoBusqueda.startsWith("<")) {
                                sql = "SELECT titulo FROM Libros WHERE anio_publicacion <= ? ORDER BY titulo ASC";
                            } else if (textoBusqueda.startsWith(">")) {
                                sql = "SELECT titulo FROM Libros WHERE anio_publicacion >= ? ORDER BY titulo ASC";
                            }
                        } else {
                            // Si no tiene < o >, se usa LIKE
                            sql = "SELECT titulo FROM Libros WHERE anio_publicacion LIKE ? ORDER BY titulo ASC";
                        }
                        break;
                    case "estado":
                        sql = "SELECT titulo FROM Libros WHERE estado LIKE ? ORDER BY titulo ASC";
                        break;
                    case "título": // Por defecto, buscar por título
                    default:
                        sql = "SELECT titulo FROM Libros WHERE titulo LIKE ? ORDER BY titulo ASC";
                        break;
                }

                // Ejecutar la consulta con los parámetros correspondientes
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                if (campoBusqueda.equals("Autor")) {
                    preparedStatement.setString(1, "%" + textoBusqueda + "%");
                    preparedStatement.setString(2, "%" + textoBusqueda + "%");

                } else if (campoBusqueda.equals("Número de Páginas") || campoBusqueda.equals("Año de Publicación")) {
                    if (textoBusqueda.contains("<") || textoBusqueda.contains(">")) {
                        preparedStatement.setString(1, textoBusqueda.replaceAll("[^\\d]", ""));
                    } else {
                        preparedStatement.setString(1, "%" + textoBusqueda + "%");
                    }
                } else {
                    preparedStatement.setString(1, "%" + textoBusqueda + "%");
                }

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setTitulo(resultSet.getString("titulo"));
                    list.add(libro);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libros por " + campoBusqueda + " con el texto '" + textoBusqueda + "': " + e.getMessage());
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return list;
    }

    /*public String selectISBNPorTitulo(String titulo) {
        String ISBN = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT ISBN FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setISBN(resultSet.getString("ISBN"));
                    ISBN = libro.getISBN();
                }
            }
        } catch (Exception e) {
            System.out.println("Error cargar los datos del libro " + titulo + ": " + e.getMessage());
        }
        return ISBN;
    }

    public String selectAutorPorTitulo(String titulo) {
        String autor = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                // Consulta modificada para buscar por el título
                sql = "SELECT A.nombre, A.apellido FROM Libros L JOIN Autores A ON L.autor = A.ID WHERE L.titulo = ? ORDER BY L.titulo ASC";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo); // Se usa el parámetro correctamente
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    // Obtener nombre y apellido del autor
                    String nombre = resultSet.getString("nombre");
                    String apellido = resultSet.getString("apellido");
                    autor = nombre + " " + apellido; // Concatenar nombre completo del autor
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return autor;
    }

    public String selectCategoriaPorTitulo(String titulo) {
        String categoria = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT categoria FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setCategoria(resultSet.getString("categoria"));
                    categoria = libro.getCategoria();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return categoria;
    }

    public String selectEditorialPorTitulo(String titulo) {
        String editorial = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT editorial FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setEditorial(resultSet.getString("editorial"));
                    editorial = libro.getEditorial();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return editorial;
    }

    public int selectPaginasPorTitulo(String titulo) {
        int paginas = 0;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT numero_paginas FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setNumeroPaginas(resultSet.getInt("numero_paginas"));
                    paginas = libro.getNumeroPaginas();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return paginas;
    }

    public String selectIdiomaPorTitulo(String titulo) {
        String idioma = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT idioma FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setIdioma(resultSet.getString("idioma"));
                    idioma = libro.getIdioma();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return idioma;
    }

    public int selectAnyoPorTitulo(String titulo) {
        int anyo = 0;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT anio_publicacion FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setAnioPublicacion(resultSet.getInt("anio_publicacion"));
                    anyo = libro.getAnioPublicacion();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return anyo;
    }

    public String selectEstadoPorTitulo(String titulo) {
        String estado = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT estado FROM Libros WHERE titulo = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setEstado(resultSet.getString("estado"));
                    estado = libro.getEstado();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        }
        return estado;
    }*/

    public String selectDatos(String titulo, String dato) {
        String resultado = null;
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                // Definir la consulta SQL según el tipo de dato solicitado
                switch (dato.toLowerCase()) {
                    case "isbn":
                        sql = "SELECT ISBN FROM Libros WHERE titulo = ?";
                        break;
                    case "autor":
                        sql = "SELECT autor FROM Libros WHERE titulo = ?";
                        //sql = "SELECT CONCAT(A.nombre, ' ', A.apellido) AS autor FROM Libros L JOIN Autores A ON L.autor = A.ID WHERE L.titulo = ?";
                        break;
                    case "categoría":
                        sql = "SELECT categoria FROM Libros WHERE titulo = ?";
                        break;
                    case "editorial":
                        sql = "SELECT editorial FROM Libros WHERE titulo = ?";
                        break;
                    case "número de páginas":
                        sql = "SELECT numero_paginas FROM Libros WHERE titulo = ?";
                        break;
                    case "idioma":
                        sql = "SELECT idioma FROM Libros WHERE titulo = ?";
                        break;
                    case "año de publicación":
                        sql = "SELECT anio_publicacion FROM Libros WHERE titulo = ?";
                        break;
                    case "estado":
                        sql = "SELECT estado FROM Libros WHERE titulo = ?";
                        break;
                    default:
                        throw new IllegalArgumentException("Dato solicitado no válido: " + dato);
                }
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Obtener el resultado según el tipo de dato solicitado
                if (resultSet.next()) {
                    if (dato.equals("paginas") || dato.equals("anyo")) {
                        resultado = String.valueOf(resultSet.getInt(1));
                    } else {
                        resultado = resultSet.getString(1);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "' para el dato '" + dato + "': " + e.getMessage());
        }
        return resultado;
    }

    /*public ArrayList<Libro> selectTitulosLibros() {
        try {
            connection = ConnectionDatabase.getConnection();

            if (connection != null) {
                sql = "SELECT titulo FROM Libros ORDER BY titulo ASC";
                saveTitles(0);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return list;
    }

    private void saveTitles(Object parametro) {
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            if (parametro instanceof Integer) {
                if ((Integer) parametro != 0) {
                    pst.setInt(1, (Integer) parametro); // Usamos el parámetro como parámetro
                }
            } else if (parametro instanceof String) {
                pst.setString(1, (String) parametro); // Usamos el parámetro como parámetro
            } else {
                throw new IllegalArgumentException("El parámetro debe ser de tipo Integer o String");
            }
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setTitulo(rs.getString("titulo"));
                list.add(libro);
            }
        } catch (Exception e) {
            System.out.println("Error X: " + e.getMessage());
        }
    }*/

    public void insertLibro(Libro libro) {
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "INSERT INTO Libros (ISBN, titulo, autor, categoria, editorial, numero_paginas, idioma, anio_publicacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Asignar los valores a los parámetros de la consulta
                preparedStatement.setString(1, libro.getISBN());
                preparedStatement.setString(2, libro.getTitulo());
                preparedStatement.setString(3, libro.getAutor());
                preparedStatement.setString(4, libro.getCategoria());
                preparedStatement.setString(5, libro.getEditorial());
                preparedStatement.setInt(6, libro.getNumeroPaginas());
                preparedStatement.setString(7, libro.getIdioma());
                preparedStatement.setInt(8, libro.getAnioPublicacion());
                preparedStatement.setString(9, libro.getEstado());

                // Ejecutar la consulta
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Libro insertado con éxito");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al insertar el libro: " + e.getMessage());
        }
    }

    public void eliminarLibro(String isbn) {
        String query = "DELETE FROM libros WHERE isbn = ?";
        try (Connection conn = ConnectionDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, isbn);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Libro eliminado correctamente.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public boolean existeIsbn(String isbn) {
        // Consulta SQL para verificar si el ISBN existe
        String sql = "SELECT COUNT(*) FROM libros WHERE ISBN = ?";

        try (Connection conn = ConnectionDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            // Si el resultado es mayor que 0, el ISBN existe
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
