package com.guayand0.librarymanager.model;

public class Autor {

    private int ID;
    private String nombre;
    private String apellido;
    private String pais;
    private String fechaDeNacimiento;

    public Autor(int ID, String nombre, String apellido, String pais, String fechaDeNacimiento) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
        this.pais = pais;
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Autor() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

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