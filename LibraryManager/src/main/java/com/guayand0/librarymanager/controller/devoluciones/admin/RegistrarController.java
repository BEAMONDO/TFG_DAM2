package com.guayand0.librarymanager.controller.devoluciones.admin;

import com.guayand0.librarymanager.model.devolucion.DevolucionDAO;
import com.guayand0.librarymanager.model.prestamo.Prestamo;
import com.guayand0.librarymanager.model.prestamo.PrestamoDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final DevolucionDAO devolucionDAO = new DevolucionDAO();

    private Usuario usuarioLogueado;

    @FXML private ComboBox<String> libroCombo, usuarioCombo;

    private final Map<String, String> usuarioMap = new HashMap<>();
    private final Map<String, String> libroMap = new HashMap<>();


    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML
    public void initialize() {
        cargarDatos();

        usuarioCombo.setOnAction(event -> cargarLibros());
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
        Map<String, String> mapa = prestamoDAO.obtenerLibroISBNTituloMap();
        libroMap.putAll(mapa);
    }

    private void cargarLibros() {
        try {
            String usuarioSeleccionado = usuarioCombo.getValue();
            if (usuarioSeleccionado == null || usuarioSeleccionado.isEmpty()) return;

            libroCombo.getItems().clear();

            List<String> librosPrestados = prestamoDAO.obtenerLibrosPrestados(getKeyByValue(usuarioMap, usuarioSeleccionado), "SI");
            libroCombo.getItems().addAll(librosPrestados);

        } catch (Exception e) {
            ALERT.showError("Error al cargar los libros prestados.");
            e.printStackTrace();
        }
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String usuario = usuarioCombo.getValue();
        String libro = libroCombo.getValue();

        String usuarioDNI = getKeyByValue(usuarioMap, usuario);
        String libroISBN = getKeyByValue(libroMap, libro);

        String fechaPrestamo = prestamoDAO.obtenerFechaPrestamoBD(libroISBN, "SI");
        String diasPrestado = prestamoDAO.obtenerDias(libroISBN, "SI");
        String fechaDevolucion = obtenerFechaPrestamo(Integer.parseInt(diasPrestado));
        String fechaDevolucionReal = obtenerFechaPrestamo(0);
        int multa = (int) (
                ChronoUnit.DAYS.between(
                        LocalDateTime.parse(fechaPrestamo, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                        LocalDateTime.parse(fechaDevolucionReal, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                ) * 1
        );

        if (multa < 0) multa = 0;

        Prestamo devolucion = new Prestamo(
                usuarioDNI, libroISBN, fechaPrestamo, fechaDevolucion, fechaDevolucionReal, multa
        );

        boolean registrado = devolucionDAO.register(devolucion);

        if (registrado) {
            ALERT.showInformation("Devolución registrada correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar la devolución.");
        }
    }

    private boolean validarCampos() {
        if (usuarioCombo.getValue() == null) {
            ALERT.showWarning("Usuario es obligatorio.");
            return false;
        }

        if (libroCombo.getValue() == null) {
            ALERT.showWarning("Libro es obligatorio.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        usuarioCombo.setValue(null);
        libroCombo.setValue(null);
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
