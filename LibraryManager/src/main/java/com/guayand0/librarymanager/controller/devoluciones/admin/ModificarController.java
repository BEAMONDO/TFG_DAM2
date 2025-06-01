package com.guayand0.librarymanager.controller.devoluciones.admin;

import com.guayand0.librarymanager.model.devolucion.DevolucionDAO;
import com.guayand0.librarymanager.model.prestamo.Prestamo;
import com.guayand0.librarymanager.model.prestamo.PrestamoDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarController {
// Seleccionar la nueva fecha de devolucion y recalcular la multa

    private final Alertas ALERT = new Alertas();
    private final PrestamoDAO prestamoDAO = new PrestamoDAO();
    private final DevolucionDAO devolucionDAO = new DevolucionDAO();

    private Usuario usuarioLogueado;

    @FXML private ComboBox<String> libroCombo, usuarioCombo;
    @FXML private DatePicker fechaPicker;

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

            List<String> librosPrestados = prestamoDAO.obtenerLibrosPrestados(getKeyByValue(usuarioMap, usuarioSeleccionado), "NO");
            libroCombo.getItems().addAll(librosPrestados);

        } catch (Exception e) {
            ALERT.showError("Error al cargar los libros prestados.");
            e.printStackTrace();
        }
    }

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String usuario = usuarioCombo.getValue();
        String libro = libroCombo.getValue();

        String usuarioDNI = getKeyByValue(usuarioMap, usuario);
        String libroISBN = getKeyByValue(libroMap, libro);

        LocalDate fecha = fechaPicker.getValue();
        LocalTime horaActual = LocalTime.now();
        LocalDateTime fechaHoraCompleta = LocalDateTime.of(fecha, horaActual);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaDevolucionReal = fechaHoraCompleta.format(formatter);

        String diasPrestado = prestamoDAO.obtenerDias(libroISBN, "NO");
        String fechaPrestamo = prestamoDAO.obtenerFechaPrestamoBD(libroISBN, "NO");

        //String fechaDevolucion = obtenerFechaPrestamo(fechaPrestamo, Integer.parseInt(diasPrestado));
        String fechaDevolucion = prestamoDAO.obtenerFechaDevolucionBD(libroISBN, "NO");


        int multa = (int) (
                ChronoUnit.DAYS.between(
                        LocalDateTime.parse(fechaDevolucion, formatter),
                        LocalDateTime.parse(fechaDevolucionReal, formatter)
                ) * 1
        );

        if (multa < 0) multa = 0;

        System.out.println(fechaPrestamo);
        System.out.println(fechaDevolucion);
        System.out.println(diasPrestado);
        System.out.println(fechaDevolucionReal);
        System.out.println(multa);

        boolean modificado = devolucionDAO.modify(usuarioDNI, libroISBN, fechaDevolucionReal, multa);

        if (modificado) {
            ALERT.showInformation("Devolución modificada correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al modificar la devolución.");
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
        fechaPicker.setValue(null);
    }

    private String getKeyByValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return "null";
    }

    private String obtenerFechaPrestamo(String fechaBase, int dias) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime base = LocalDateTime.parse(fechaBase, formatter);
        LocalDateTime resultado = base.plusDays(dias);
        return resultado.format(formatter);
    }

}
