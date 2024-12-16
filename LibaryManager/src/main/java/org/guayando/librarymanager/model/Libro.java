package org.guayando.librarymanager.model;

public class Libro {

    private int ID;
    private String ISBN;
    private String titulo;
    private int autor;
    private String categoria;
    private String editorial;
    private int numeroPaginas;
    private String idioma;
    private int anioPublicacion;
    private String estado;

    public Libro(int ID, String ISBN, String titulo, int autor, String categoria, String editorial, int numeroPaginas, String idioma, int anioPublicacion, String estado) {
        this.ID = ID;
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.idioma = idioma;
        this.anioPublicacion = anioPublicacion;
        this.estado = estado;
    }

    public Libro() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // ---------- -- ---------- //
    /*public boolean registrarLibroValido(String ISBN, String titulo, int autor, String categoria, String editorial, int numeroPaginas, String idioma, int anioPublicacion, String estado) {
        return ISBN != null && !ISBN.isEmpty() &&
                titulo != null && !titulo.isEmpty() &&
                autor > 0 &&
                categoria != null && !categoria.isEmpty() &&
                editorial != null && !editorial.isEmpty() &&
                numeroPaginas > 0 &&
                idioma != null && !idioma.isEmpty() &&
                anioPublicacion > 0 && anioPublicacion < LocalDate.now().getYear() &&
                estado != null && !estado.isEmpty();
    }

    // Comparación de libros (por nombre)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Libro libro = (Libro) obj;
        return titulo.equals(libro.titulo);
    }

    @Override
    public int hashCode() {
        return titulo.hashCode();
    }

    // Representación en String para depuración y uso
    @Override
    public String toString() {
        return String.format("Libro{ID=%d, ISBN='%s', titulo='%s', autor='%d', categoria='%s', editorial='%s', numeroPaginas=%d, idioma='%s', anioPublicacion=%d, estado='%s}",
                ID, ISBN, titulo, autor, categoria, editorial, numeroPaginas, idioma, anioPublicacion, estado);
    }*/
}
