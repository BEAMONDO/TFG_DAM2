package com.guayand0.librarymanager.utils;

public class ComprobarISBN {

    public boolean isValidISBN13(String isbn) {
        if (isbn == null) return false;

        // Eliminar guiones y espacios
        isbn = isbn.replaceAll("[-\\s]", "");

        if (!isbn.matches("\\d{13}")) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }
}
