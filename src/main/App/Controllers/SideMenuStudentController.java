package Controllers;

import Models.Session;
import Utilities.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador para el menú lateral del estudiante.
 * Contiene solo las opciones básicas y cerrar sesión.
 */
public class SideMenuStudentController extends BaseController {

    @FXML
    private Label lblUserEmail;

    @FXML
    private Button btnHome;

    @FXML
    private Button btnMyFormatA;

    @FXML
    private Button btnLogout;

    private Session currentSession;

    /**
     * Inicializa el menú con los datos de la sesión.
     */
    public void initData(Session session) {
        this.currentSession = session;

        if (lblUserEmail != null && session != null) {
            lblUserEmail.setText(session.getEmail());
        }
    }

    /**
     * Maneja el clic en el botón de Inicio.
     */
    @FXML
    private void handleHome(ActionEvent event) {
        // Por ahora no hace nada, ya que estamos en la vista principal
        // Podrías agregar lógica para refrescar la vista o navegar a una página de inicio
        setActiveButton(btnHome);
    }

    /**
     * Maneja el clic en Mi Formato A.
     */
    @FXML
    private void handleMyFormatA(ActionEvent event) {
        // Por ahora no hace nada, ya que estamos en la vista del FormatA
        // Podrías agregar lógica para refrescar específicamente el FormatA
        setActiveButton(btnMyFormatA);
    }

    /**
     * Maneja el cierre de sesión.
     */
    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Limpiar cookie... IMPLEMENTAR


            // Navegar de vuelta al login
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el controller de Login y pasarle la fábrica de servicios
            LoginController loginController = loader.getController();
            loginController.setServiceFactory(this.serviceFactory);

            // Pasar mensaje de logout exitoso
            stage.setUserData("Sesión cerrada correctamente.");

            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Error al cerrar sesión: " + e.getMessage());
        }
    }

    /**
     * Establece el botón activo en el menú.
     */
    private void setActiveButton(Button activeButton) {
        // Remover clase activa de todos los botones
        btnHome.getStyleClass().remove("menu-button-active");
        btnMyFormatA.getStyleClass().remove("menu-button-active");

        // Agregar clase activa al botón seleccionado
        if (!activeButton.getStyleClass().contains("menu-button-active")) {
            activeButton.getStyleClass().add("menu-button-active");
        }
    }

    /**
     * Muestra una alerta de error.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Establecer Mi Formato A como activo por defecto
        if (btnMyFormatA != null) {
            setActiveButton(btnMyFormatA);
        }
    }
}