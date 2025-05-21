package com.guayand0.librarymanager.model.editorial;

public class Editorial {

    //private int id_editorial;
    private String nombre;

    public Editorial(String nombre) {
        this.nombre = nombre;
    }

    public Editorial() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
