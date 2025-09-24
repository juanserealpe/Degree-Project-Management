package Controllers;


import Enums.EnumRole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class RoleSelectionController implements Initializable {

    @FXML private Label lblUserEmail;
    @FXML private ComboBox<EnumRole> cmbRoles;
    @FXML private Label lblMessage;
    @FXML private Button btnContinue;

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

    }

    /**
     * Carga la información del usuario desde la sesión
     */
    private void loadUserSession() {

    }

    /**
     * Carga los roles disponibles desde la sesión
     */
    private void loadRoles() {

    }

    /**
     * Maneja el evento de continuar
     */
    @FXML
    private void handleContinue(ActionEvent event) {

    }

    /**
     * Actualiza el estado del botón continuar
     */
    private void updateContinueButton() {

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
}