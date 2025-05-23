package com.guayand0.librarymanager.utils;

public class ComprobarDNI {

    public boolean validarDNI(String dni) {
        return dni.matches("^[0-9]{8}[A-Za-z]$") && validarLetraDNI(dni);
    }

    private boolean validarLetraDNI(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        char letraCorrecta = letras.charAt(numero % 23);
        char letraIngresada = Character.toUpperCase(dni.charAt(8));
        return letraCorrecta == letraIngresada;
    }
}
