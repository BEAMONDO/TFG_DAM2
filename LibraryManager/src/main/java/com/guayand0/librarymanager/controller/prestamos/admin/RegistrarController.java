package com.guayand0.librarymanager.controller.prestamos.admin;

import com.guayand0.librarymanager.model.prestamo.Prestamo;
import com.guayand0.librarymanager.model.prestamo.PrestamoDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();

    private Usuario usuarioLogueado;

    @FXML private ComboBox<String> libroCombo, usuarioCombo;
    @FXML private TextField diasField;

    private final Map<String, String> usuarioMap = new HashMap<>();
    private final Map<String, String> libroMap = new HashMap<>();

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        cargarDatos();
    }

    private void cargarDatos() {
        new Thread(() -> {
            cargarUsuario();
            cargarLibro();
        }).start();
    }

    private void cargarUsuario() {
        Map<String, String> mapa = prestamoDAO.obtenerUsuarioDNINombreMap();
        usuarioMap.putAll(mapa);
        usuarioCombo.getItems().addAll(mapa.values());
    }

    private void cargarLibro() {
        Map<String, String> mapa = prestamoDAO.obtenerLibroISBNTituloDisponibleMap();
        libroMap.putAll(mapa);

        libroCombo.getItems().addAll(mapa.values());
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String usuario = usuarioCombo.getValue();
        String libro = libroCombo.getValue();
        String fechaPrestamo = obtenerFechaPrestamo(0);
        String fechaDevolucion = obtenerFechaPrestamo(Integer.parseInt(diasField.getText()));

        String usuarioDNI = getKeyByValue(usuarioMap, usuario);
        String libroISBN = getKeyByValue(libroMap, libro);

        Prestamo prestamo = new Prestamo(
                usuarioDNI, libroISBN, fechaPrestamo, fechaDevolucion, null, 0
        );

        boolean registrado = prestamoDAO.register(prestamo);

        if (registrado) {
            ALERT.showInformation("Préstamo registrado correctamente.");
            limpiarCampos();

            libroCombo.getItems().clear();
            new Thread(this::cargarLibro).start();
        } else {
            ALERT.showWarning("Error al registrar el préstamo.");
        }
    }

    private boolean validarCampos() {

        String dias = String.valueOf(diasField.getText());

        if (usuarioCombo.getValue() == null) {
            ALERT.showWarning("Usuario es obligatorio.");
            return false;
        }

        if (libroCombo.getValue() == null) {
            ALERT.showWarning("Libro es obligatorio.");
            return false;
        }

        if (dias.isEmpty()) {
            ALERT.showWarning("Dias es obligatorio.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        usuarioCombo.setValue(null);
        libroCombo.setValue(null);
        diasField.setText("");
    }

    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return "null";
    }

    private String obtenerFechaPrestamo(int dias) {
        LocalDateTime now = LocalDateTime.now().plusDays(dias);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

}
