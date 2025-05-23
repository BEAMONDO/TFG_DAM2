package com.guayand0.librarymanager.utils;

public class ComprobarEmail {

    public boolean validarEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
