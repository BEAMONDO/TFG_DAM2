package com.guayand0.librarymanager.controller.usuarios.admin;

import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.model.usuario.UsuarioDAO;
import com.guayand0.librarymanager.utils.Alertas;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class EliminarController {

    private final Alertas ALERT = new Alertas();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML private ComboBox<String> dniCombo;

    @FXML private void initialize() {
        new Thread(this::cargarDNIUsuarios).start();
    }

    @FXML private void onDeleteClick() {
        String dniSeleccionado = dniCombo.getValue();

        if (dniSeleccionado == null || dniSeleccionado.isEmpty()) {
            ALERT.showError("Por favor, selecciona un DNI válido.");
            return;
        }

        boolean eliminado = usuarioDAO.delete(dniSeleccionado);

        if (eliminado) {
            ALERT.showInformation("Usuario eliminado correctamente.");
            dniCombo.getItems().remove(dniSeleccionado);
            dniCombo.setValue(null);
        } else {
            ALERT.showError("Error al eliminar el usuario.");
        }
    }

    private void cargarDNIUsuarios() {
        dniCombo.getItems().clear();

        List<Usuario> usuarios = usuarioDAO.obtenerUsuarios();
        for (Usuario usuario : usuarios) {
            dniCombo.getItems().add(usuario.getDNI());
        }
    }
}
