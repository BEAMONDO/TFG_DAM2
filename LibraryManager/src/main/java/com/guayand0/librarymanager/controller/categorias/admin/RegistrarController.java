package com.guayand0.librarymanager.controller.categorias.admin;

import com.guayand0.librarymanager.model.categoria.Categoria;
import com.guayand0.librarymanager.model.categoria.CategoriaDAO;
import com.guayand0.librarymanager.model.usuario.Usuario;
import com.guayand0.librarymanager.utils.Alertas;
import com.guayand0.librarymanager.utils.LimiteCaracteres;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegistrarController {

    private final Alertas ALERT = new Alertas();
    CategoriaDAO categoriaDAO = new CategoriaDAO();
    private final LimiteCaracteres LC = new LimiteCaracteres();

    private Usuario usuarioLogueado;

    @FXML private TextField categoriaField;

    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @FXML public void initialize() {
        aplicarLimitesCaracteres();
    }

    private void aplicarLimitesCaracteres() {
        categoriaField.setTextFormatter(LC.createTextFormatter(50));
    }

    @FXML private void onRegisterClick() {
        if (!validarCampos()) return;

        String nombre = categoriaField.getText();

        Categoria categoria = new Categoria(
                nombre
        );

        boolean registrado = categoriaDAO.register(categoria);

        if (registrado) {
            ALERT.showInformation("Categoría registrada correctamente.");
            limpiarCampos();
        } else {
            ALERT.showWarning("Error al registrar la categoría.");
        }
    }

    private boolean validarCampos() {
        if (categoriaField.getText().isEmpty()) {
            ALERT.showWarning("Nombre de categoria es obligatoria.");
            return false;
        }

        return true;
    }

    private void limpiarCampos() {
        categoriaField.clear();
    }
}
