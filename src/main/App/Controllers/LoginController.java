package Controllers;

import Interfaces.IAuthService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

/**
 * Controlador para la vista de inicio de sesión (Login).
 *
 * Esta clase gestiona la interfaz de usuario para el proceso de autenticación,
 * incluyendo la validación de credenciales, navegación hacia el registro,
 * y manejo de mensajes de retroalimentación para el usuario.
 *
 * Características principales:
 * - Autenticación de usuarios mediante email y contraseña
 * - Navegación fluida entre vistas de login y registro
 * - Sistema de mensajes temporales con animaciones
 * - Manejo de mensajes de éxito pasados entre vistas
 * - Integración con servicios de autenticación
 *
 * @author juanseralpe
 */
public class LoginController {

    // ==================== CAMPOS FXML ====================

    /** Campo de texto para ingresar el email del usuario */
    @FXML
    private TextField txtEmail;

    /** Campo de contraseña para ingresar la contraseña del usuario */
    @FXML
    private PasswordField txtPassword;

    /** Label para mostrar mensajes de error, éxito o información general */
    @FXML
    private Label lblMessage;

    /** Label específico para mostrar mensajes de éxito con animación temporal */
    @FXML
    private Label successBox;

    // ==================== SERVICIOS ====================

    /** Servicio de autenticación para validar credenciales de usuario */
    private IAuthService authService;

    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    private ServiceFactory serviceFactory;

    /**
     * Establece la fábrica de servicios y configura el servicio de autenticación.
     * Este método debe ser llamado después de la creación del controlador.
     *
     * @param serviceFactory La fábrica de servicios de la aplicación
     */
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.authService = serviceFactory.getAuthService();
    }

    /**
     * Método de inicialización de JavaFX llamado automáticamente después de cargar el FXML.
     * Configura el estado inicial de los componentes y maneja mensajes de éxito
     * que puedan haber sido pasados desde otras vistas.
     */
    @FXML
    public void initialize() {
        // Ocultar el successBox inicialmente
        successBox.setVisible(false);
        successBox.setManaged(false);

        // Verificar si hay mensajes de éxito pendientes desde otras vistas
        Platform.runLater(() -> {
            Stage stage = (Stage) successBox.getScene().getWindow();
            Object data = stage.getUserData();
            if (data instanceof String) {
                showSuccessBox((String) data);
                stage.setUserData(null); // Limpiar el dato después de usarlo
            }
        });
    }

    /**
     * Muestra un mensaje de éxito temporal con animación.
     * El mensaje se muestra durante 3 segundos y luego se oculta automáticamente.
     *
     * @param message El mensaje de éxito a mostrar
     */
    private void showSuccessBox(String message) {
        successBox.setText(message);
        successBox.setVisible(true);
        successBox.setManaged(true);

        // Crear una transición de pausa para ocultar el mensaje después de 3 segundos
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            successBox.setVisible(false);
            successBox.setManaged(false);
        });
        pause.play();
    }

    /**
     * Maneja el evento de inicio de sesión.
     * Valida las credenciales del usuario y, si son correctas, procede con la autenticación.
     * En caso de error, muestra un mensaje de error apropiado.
     *
     * @param event El evento de acción del botón de login
     * @throws Exception Si ocurre un error durante el proceso de autenticación
     */
    @FXML
    private void handleLogin(ActionEvent event) throws Exception {

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        try {
            // Validar credenciales através del servicio de autenticación
            authService.isLoginValid(email, password);

            // Obtener el Stage actual y cambiar a la vista principal
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.changeScene(stage, "/views/AuthViews/LoginView.fxml", "");

        } catch (Exception ex) {
            // Mostrar mensaje de error en caso de fallo en la autenticación
            showErrorMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Maneja el evento de navegación hacia el registro.
     * Carga la vista de registro y transfiere la instancia de ServiceFactory
     * al controlador de registro para mantener la continuidad de servicios.
     *
     * @param event El evento de acción del botón de registro
     */
    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            // Obtener el Stage actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cargar la vista de registro
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/RegisterView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el controlador de registro y pasarle la fábrica de servicios
            RegisterController controller = loader.getController();
            controller.setServiceFactory(this.serviceFactory);

            // Cambiar la escena
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
            showInfoMessage("Error al abrir el formulario de registro");
        }
    }

    /**
     * Muestra un mensaje de error en la interfaz.
     * Aplica la clase CSS 'error' para el estilo visual correspondiente.
     *
     * @param message El mensaje de error a mostrar
     */
    private void showErrorMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("error");
    }

    /**
     * Muestra un mensaje de éxito en la interfaz.
     * Aplica la clase CSS 'success' para el estilo visual correspondiente.
     *
     * @param message El mensaje de éxito a mostrar
     */
    private void showSuccessMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("success");
    }

    /**
     * Muestra un mensaje informativo en la interfaz.
     * Aplica la clase CSS 'info' para el estilo visual correspondiente.
     *
     * @param message El mensaje informativo a mostrar
     */
    private void showInfoMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("info");
    }
}