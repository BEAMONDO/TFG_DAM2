package com.guayand0.librarymanager.model.autor;

public class Autor {

    private String nombre;
    private String apellido;
    private String pais;
    private String fechaDeNacimiento;

    public Autor(String nombre, String apellido, String pais, String fechaDeNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Autor() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }
}