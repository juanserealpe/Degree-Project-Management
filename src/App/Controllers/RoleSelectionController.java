package Controllers;

import Models.Role;
import Models.Session;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RoleSelectionController implements Initializable {

    @FXML private Label lblUserEmail;
    @FXML private ComboBox<Role> cmbRoles;
    @FXML private Label lblMessage;
    @FXML private Button btnContinue;

    private Role selectedRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupRoleComboBox();
        loadUserSession();
        loadRoles();
        clearMessage();
    }

    /**
     * Configura el ComboBox de roles
     */
    private void setupRoleComboBox() {
        // Configurar cómo se muestra cada rol en el ComboBox
        cmbRoles.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return role != null ? role.getName() : "";
            }

            @Override
            public Role fromString(String string) {
                return null; // No necesario para este caso
            }
        });

        // Listener para cuando se selecciona un rol
        cmbRoles.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    selectedRole = newValue;
                    updateContinueButton();
                    clearMessage();
                }
        );
    }

    /**
     * Carga la información del usuario desde la sesión
     */
    private void loadUserSession() {
        String userEmail = Session.getEmail();
        if (userEmail != null && !userEmail.isEmpty()) {
            lblUserEmail.setText(userEmail);
        } else {
            lblUserEmail.setText("Usuario no identificado");
        }
    }

    /**
     * Carga los roles disponibles desde la sesión
     */
    private void loadRoles() {
        List<Role> roles = Session.getRoles();

        if (roles == null || roles.isEmpty()) {
            showErrorMessage("No se encontraron roles asignados");
            btnContinue.setDisable(true);
            return;
        }

        // Cargar roles en el ComboBox
        cmbRoles.setItems(FXCollections.observableArrayList(roles));

        // Seleccionar el primer rol por defecto
        cmbRoles.getSelectionModel().selectFirst();
        selectedRole = roles.get(0);

        showInfoMessage("Roles cargados: " + roles.size() + " disponible(s)");
    }

    /**
     * Maneja el evento de continuar
     */
    @FXML
    private void handleContinue(ActionEvent event) {
        if (selectedRole == null) {
            showErrorMessage("Por favor seleccione un rol");
            return;
        }

        try {
            // Aquí puedes agregar lógica adicional antes de continuar
            // Por ejemplo: guardar el rol seleccionado en algún lugar,
            // hacer validaciones adicionales, etc.

            showSuccessMessage("Accediendo con rol: " + selectedRole.getName());

            // Simular un breve delay para mostrar el mensaje
            new Thread(() -> {
                try {
                    Thread.sleep(1500);

                    javafx.application.Platform.runLater(() -> {
                        try {
                            // Cambiar a la ventana principal o dashboard
                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                            // TODO: Cambiar por tu ventana principal
                            // WindowManager.changeScene(stage, "/fxml/dashboard.fxml", "Dashboard - " + selectedRole.getRoleName());

                            // Por ahora, mostrar un mensaje
                            showSuccessMessage("¡Redirigiendo al sistema principal!");

                        } catch (Exception e) {
                            showErrorMessage("Error al acceder al sistema: " + e.getMessage());
                        }
                    });

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();

        } catch (Exception e) {
            showErrorMessage("Error al procesar la selección: " + e.getMessage());
        }
    }

    /**
     * Actualiza el estado del botón continuar
     */
    private void updateContinueButton() {
        btnContinue.setDisable(selectedRole == null);
    }

    /**
     * Muestra mensaje de error
     */
    private void showErrorMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().removeAll("success", "info");
        if (!lblMessage.getStyleClass().contains("message-label")) {
            lblMessage.getStyleClass().add("message-label");
        }
    }

    /**
     * Muestra mensaje de éxito
     */
    private void showSuccessMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().removeAll("message-label");
        lblMessage.getStyleClass().addAll("message-label", "success");
    }

    /**
     * Muestra mensaje informativo
     */
    private void showInfoMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().removeAll("success");
        lblMessage.getStyleClass().addAll("message-label", "info");
    }

    /**
     * Limpia el mensaje
     */
    private void clearMessage() {
        lblMessage.setText("");
    }

    /**
     * Obtiene el rol seleccionado actualmente
     */
    public Role getSelectedRole() {
        return selectedRole;
    }
}