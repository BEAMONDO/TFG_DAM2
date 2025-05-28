package com.guayand0.librarymanager.controller.autores.admin;

import com.guayand0.librarymanager.model.autor.Autor;
import com.guayand0.librarymanager.model.autor.AutorDAO;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModificarController {

    private final Alertas ALERT = new Alertas();
    private final AutorDAO autorDAO = new AutorDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    @FXML private ComboBox<String> nombreCompletoCombo;
    @FXML private TextField nombreField, apellidoField, paisField;
    @FXML private DatePicker fechaField;

    private final Map<String, Autor> autorMap = new HashMap<>();
    private Autor autorSeleccionado;

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
        new Thread(this::cargarNombreAutores).start();

        nombreCompletoCombo.setOnAction(event -> cargarDatosAutor());
    }

    private void aplicarLimitesCaracteres() {
        nombreField.setTextFormatter(LC.createTextFormatter(50));
        apellidoField.setTextFormatter(LC.createTextFormatter(50));
        paisField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onModifyClick() {
        if (autorSeleccionado == null) {
            ALERT.showError("Selecciona un autor para modificar.");
            return;
        }

        if (!validarCampos()) return;

        String nombre = nombreField.getText();
        String apellido = apellidoField.getText();
        String pais = paisField.getText();
        String fechaNacimiento = fechaField.getValue().toString();

        String[] datos = {
                autorSeleccionado.getNombre(), nombre, apellido, pais, fechaNacimiento
        };

        boolean actualizado = autorDAO.modify(datos);

        if (actualizado) {
            ALERT.showInformation("Autor modificado correctamente.");
            limpiarCampos();
            cargarNombreAutores();
        } else {
            ALERT.showError("Error al modificar el autor.");
        }
    }

    private void cargarNombreAutores() {
        nombreCompletoCombo.getItems().clear();
        autorMap.clear();

        List<Autor> autores = autorDAO.obtenerAutores();
        for (Autor autor : autores) {
            String nombreCompleto = autor.getNombre() + " " + autor.getApellido();
            nombreCompletoCombo.getItems().add(nombreCompleto);
            autorMap.put(nombreCompleto, autor);
        }
    }

    private void cargarDatosAutor() {
        String seleccion = nombreCompletoCombo.getValue();
        if (seleccion == null) return;

        autorSeleccionado = autorMap.get(seleccion);
        if (autorSeleccionado == null) return;

        nombreField.setText(autorSeleccionado.getNombre());
        apellidoField.setText(autorSeleccionado.getApellido());
        paisField.setText(autorSeleccionado.getPais());
        try {
            fechaField.setValue(LocalDate.parse(autorSeleccionado.getFechaDeNacimiento()));
        } catch (Exception e) {
            fechaField.setValue(null);
        }
    }

    private boolean validarCampos() {
        if (nombreField.getText().isEmpty()) {
            ALERT.showWarning("Nombre de autor es obligatorio.");
            return false;
        }

        if (apellidoField.getText().isEmpty()) {
            ALERT.showWarning("Apellido de autor es obligatorio.");
            return false;
        }

        if (paisField.getText().isEmpty()) {
            ALERT.showWarning("País de autor es obligatorio.");
            return false;
        }

        if (fechaField.getValue() == null) {
            ALERT.showWarning("Fecha de nacimiento es obligatoria.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        nombreField.clear();
        apellidoField.clear();
        paisField.clear();
        fechaField.setValue(null);
        nombreCompletoCombo.setValue(null);
        autorSeleccionado = null;
    }
}
