package com.guayand0.librarymanager.controller.prestamos.admin;

import com.guayand0.librarymanager.model.prestamo.PrestamoDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarController {

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

    @FXML
    public void initialize() {
        cargarDatos();

        usuarioCombo.setOnAction(event -> cargarLibros());
        libroCombo.setOnAction(event -> cargarDias());
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

    private void cargarDias() {
        try {
            String libroSeleccionado = libroCombo.getValue();
            if (libroSeleccionado == null || libroSeleccionado.isEmpty()) return;

            diasField.setText("");

            String diasPrestado = prestamoDAO.obtenerDias(getKeyByValue(libroMap, libroSeleccionado), "SI");
            diasField.setText(diasPrestado);

        } catch (Exception e) {
            ALERT.showError("Error al cargar el numero de dias.");
            e.printStackTrace();
        }
    }

    @FXML private void onModifyClick() {
        if (!validarCampos()) return;

        String usuario = usuarioCombo.getValue();
        String libro = libroCombo.getValue();

        String usuarioDNI = getKeyByValue(usuarioMap, usuario);
        String libroISBN = getKeyByValue(libroMap, libro);

        int dias = Integer.parseInt(diasField.getText());

        String fechaPrestamo = prestamoDAO.obtenerFechaPrestamoBD(libroISBN, "SI");

        LocalDateTime fecha = LocalDateTime.parse(fechaPrestamo, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime devolucion = fecha.plusDays(dias);
        String fechaDevolucion = devolucion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        boolean modificado = prestamoDAO.modify(usuarioDNI, libroISBN, fechaDevolucion);


        if (modificado) {
            ALERT.showInformation("Préstamo modificado correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al modificar el préstamo.");
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

}
