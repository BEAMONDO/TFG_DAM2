package com.guayand0.librarymanager.model.libro;

import com.guayand0.librarymanager.db.ConnectionDatabase;

import java.sql.*;
import java.util.ArrayList;

public class LibroDAO {

    private Connection connection = null;
    private String sql;

    public ArrayList<Libro> selectTitulos(String textoBusqueda, String campoBusqueda) {
        ArrayList<Libro> list = new ArrayList<>();
        try {
            connection = ConnectionDatabase.getConnection(); // Obtiene la conexión a la base de datos
            if (connection != null) {
                // Construcción de la consulta SQL según el campo de búsqueda
                switch (campoBusqueda.toLowerCase()) {
                    case "isbn":
                        sql = "SELECT titulo FROM Libros WHERE ISBN LIKE ? ORDER BY titulo ASC";
                        break;
                    case "autor":
                        sql = "SELECT L.titulo, A.nombre, A.apellido " +
                                "FROM Libros L " +
                                "JOIN Autores A ON L.autor = A.ID " +
                                "WHERE A.nombre LIKE ? OR A.apellido LIKE ? " +
                                "ORDER BY L.titulo ASC";
                        break;
                    case "categoría":
                        sql = "SELECT L.titulo, C.nombre AS categoria " +
                                "FROM Libros L " +
                                "JOIN Categorias C ON L.id_categoria = C.id_categoria " +
                                "WHERE C.nombre LIKE ? " +
                                "ORDER BY L.titulo ASC";
                        break;
                    case "editorial":
                        sql = "SELECT L.titulo, E.nombre AS editorial " +
                                "FROM Libros L " +
                                "JOIN Editoriales E ON L.id_editorial = E.id_editorial " +
                                "WHERE E.nombre LIKE ? " +
                                "ORDER BY L.titulo ASC";
                        break;
                    case "número de páginas":
                        // Lógica para manejar comparaciones de número de páginas
                        if (textoBusqueda.startsWith("<") || textoBusqueda.startsWith(">")) {
                            if (textoBusqueda.startsWith("<")) {
                                sql = "SELECT titulo FROM Libros WHERE numero_paginas <= ? ORDER BY titulo ASC";
                            } else if (textoBusqueda.startsWith(">")) {
                                sql = "SELECT titulo FROM Libros WHERE numero_paginas >= ? ORDER BY titulo ASC";
                            }
                        } else {
                            sql = "SELECT titulo FROM Libros WHERE numero_paginas LIKE ? ORDER BY titulo ASC";
                        }
                        break;
                    case "idioma":
                        sql = "SELECT L.titulo, I.nombre AS idioma " +
                                "FROM Libros L " +
                                "JOIN Idiomas I ON L.id_idioma = I.id_idioma " +
                                "WHERE I.nombre LIKE ? " +
                                "ORDER BY L.titulo ASC";
                        break;
                    case "año de publicación":
                        // Lógica para manejar comparaciones de año de publicación
                        if (textoBusqueda.startsWith("<") || textoBusqueda.startsWith(">")) {
                            if (textoBusqueda.startsWith("<")) {
                                sql = "SELECT titulo FROM Libros WHERE anio_publicacion <= ? ORDER BY titulo ASC";
                            } else if (textoBusqueda.startsWith(">")) {
                                sql = "SELECT titulo FROM Libros WHERE anio_publicacion >= ? ORDER BY titulo ASC";
                            }
                        } else {
                            sql = "SELECT titulo FROM Libros WHERE anio_publicacion LIKE ? ORDER BY titulo ASC";
                        }
                        break;
                    case "estado":
                        sql = "SELECT titulo FROM Libros WHERE estado LIKE ? ORDER BY titulo ASC";
                        break;
                    case "título": // Caso por defecto, buscar por título
                    default:
                        sql = "SELECT titulo FROM Libros WHERE titulo LIKE ? ORDER BY titulo ASC";
                        break;
                }

                // Preparar la consulta con los parámetros adecuados
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Configuración de parámetros según el tipo de búsqueda
                if (campoBusqueda.equals("autor")) {
                    preparedStatement.setString(1, "%" + textoBusqueda + "%");
                    preparedStatement.setString(2, "%" + textoBusqueda + "%");
                } else if (campoBusqueda.equals("número de páginas") || campoBusqueda.equals("año de publicación")) {
                    if (textoBusqueda.contains("<") || textoBusqueda.contains(">")) {
                        preparedStatement.setString(1, textoBusqueda.replaceAll("[^\\d]", "")); // Solo números
                    } else {
                        preparedStatement.setString(1, "%" + textoBusqueda + "%");
                    }
                } else {
                    preparedStatement.setString(1, "%" + textoBusqueda + "%");
                }

                // Ejecutar la consulta y procesar los resultados
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Libro libro = new Libro();
                    libro.setTitulo(resultSet.getString("titulo"));
                    list.add(libro); // Añadir el libro a la lista
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar libros por " + campoBusqueda + " con el texto '" + textoBusqueda + "': " + e.getMessage());
        } finally {
            // Cerrar la conexión
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return list;
    }

    public String[] selectDatos(String titulo) {
        String[] resultado = new String[8]; // [ISBN, autor, categoría, editorial, páginas, idioma, año, estado]
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "SELECT L.ISBN, CONCAT(A.nombre, ' ', A.apellido) AS autor, C.nombre AS categoria, " +
                        "E.nombre AS editorial, L.numero_paginas, I.nombre AS idioma, L.anio_publicacion, L.estado " +
                        "FROM Libros L " +
                        "JOIN Autores A ON L.autor = A.ID " +
                        "JOIN Categorias C ON L.id_categoria = C.id_categoria " +
                        "JOIN Editoriales E ON L.id_editorial = E.id_editorial " +
                        "JOIN Idiomas I ON L.id_idioma = I.id_idioma " +
                        "WHERE L.titulo = ?";

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, titulo);
                ResultSet resultSet = preparedStatement.executeQuery();

                // Obtener los resultados
                if (resultSet.next()) {
                    resultado[0] = resultSet.getString("ISBN");
                    resultado[1] = resultSet.getString("autor");
                    resultado[2] = resultSet.getString("categoria");
                    resultado[3] = resultSet.getString("editorial");
                    resultado[4] = String.valueOf(resultSet.getInt("numero_paginas"));
                    resultado[5] = resultSet.getString("idioma");
                    resultado[6] = String.valueOf(resultSet.getInt("anio_publicacion"));
                    resultado[7] = resultSet.getString("estado");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los datos del libro '" + titulo + "': " + e.getMessage());
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return resultado;
    }

    public void insertLibro(Libro libro) {
        try {
            connection = ConnectionDatabase.getConnection();
            if (connection != null) {
                sql = "INSERT INTO Libros (ISBN, titulo, autor, categoria, editorial, numero_paginas, idioma, anio_publicacion, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Asignar los valores a los parámetros de la consulta
                preparedStatement.setString(1, libro.getISBN());
                preparedStatement.setString(2, libro.getTitulo());
                preparedStatement.setInt(3, libro.getAutor());
                preparedStatement.setInt(4, libro.getCategoria());
                preparedStatement.setInt(5, libro.getEditorial());
                preparedStatement.setInt(6, libro.getNumeroPaginas());
                preparedStatement.setInt(7, libro.getIdioma());
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
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }
    }

    public void eliminarLibro(String isbn) {
        String query = "DELETE FROM libros WHERE isbn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setString(1, isbn);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Libro eliminado correctamente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }
    }


    public boolean existeIsbn(String isbn) {
        // Consulta SQL para verificar si el ISBN existe
        String sql = "SELECT COUNT(*) FROM Libros WHERE ISBN = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            // Si el resultado es mayor que 0, el ISBN existe
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                ConnectionDatabase.closeConnection(connection);
            }
        }

        return false;
    }

}
