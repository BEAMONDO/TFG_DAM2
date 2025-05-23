package com.guayand0.librarymanager.model.libro;

public class Libro {

    private String ISBN;
    private String titulo;
    private int autor;
    private int categoria;
    private int editorial;
    private int numeroPaginas;
    private int idioma;
    private int anioPublicacion;
    private String estado;

    public Libro(String ISBN, String titulo, int autor, int categoria, int editorial, int numeroPaginas, int idioma, int anioPublicacion, String estado) {
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

    public Libro() {}

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

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getEditorial() {
        return editorial;
    }

    public void setEditorial(int editorial) {
        this.editorial = editorial;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public int getIdioma() {
        return idioma;
    }

    public void setIdioma(int idioma) {
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

}
