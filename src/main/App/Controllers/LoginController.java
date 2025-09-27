package Controllers;

import Interfaces.IAuthService;
import Services.ServiceFactory;
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

public class LoginController extends BaseController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMessage;
    @FXML
    private Label successBox;

    private IAuthService authService;
    // Removemos la declaración duplicada de serviceFactory ya que está en BaseController

    @Override
    public void setServiceFactory(ServiceFactory serviceFactory) {
        super.setServiceFactory(serviceFactory);
        this.authService = serviceFactory.getAuthService();
    }

    @FXML
    public void initialize() {
        successBox.setVisible(false);
        successBox.setManaged(false);

        Platform.runLater(() -> {
            Stage stage = (Stage) successBox.getScene().getWindow();
            Object data = stage.getUserData();
            if (data instanceof String) {
                showSuccessBox((String) data);
                stage.setUserData(null); // limpiar para que no se repita
            }
        });
    }

    private void showSuccessBox(String message) {
        successBox.setText(message);
        successBox.setVisible(true);
        successBox.setManaged(true);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            successBox.setVisible(false);
            successBox.setManaged(false);
        });
        pause.play();
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        try {
            //authService.isLoginValid(email, password);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            CoordinatorViewController controller =
                    (CoordinatorViewController) WindowManager.changeScene(stage, "/views/UserViews/CoordinatorView.fxml", "");

        } catch (Exception ex) {
            // Mostrar mensaje de error en caso de fallo en la autenticación
            showErrorMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/RegisterView.fxml"));
            Scene scene = new Scene(loader.load());

            // Obtener el controller y pasarle la misma instancia de ServiceFactory
            RegisterController controller = loader.getController();
            controller.setServiceFactory(this.serviceFactory); // <- usar serviceFactory heredado de BaseController

            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            showInfoMessage("Error al abrir el formulario de registro");
        }
    }

    private void showErrorMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("error");
    }

    private void showSuccessMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("success");
    }

    private void showInfoMessage(String message) {
        lblMessage.setText(message);
        lblMessage.getStyleClass().add("info");
    }
}