package com.guayand0.librarymanager.utils;

import javafx.scene.control.TextFormatter;

public class LimiteCaracteres {

    public TextFormatter<String> createTextFormatter(int maxLength) {
        // Crear un filtro de texto para restringir la longitud máxima de caracteres
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().length() > maxLength) {
                change.setText("");  // Ignorar el cambio si el texto excede el límite
                change.setCaretPosition(change.getCaretPosition() - 1); // Ajustar la posición del cursor
            }
            return change;
        });
    }
}
