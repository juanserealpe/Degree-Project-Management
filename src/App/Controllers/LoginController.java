package Controllers;

import Interfaces.IAuthService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Label lblMessage;
    @FXML
    private Label successBox;


    private IAuthService authService;

    @FXML
    public void initialize() {
        // Obtiene el servicio al cargar el FXML
        this.authService = ServiceFactory.getAuthService();

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
            authService.isLoginValid(email, password);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            WindowManager.changeScene(stage, "/fxml/dashboard.fxml", "");


        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.changeScene(stage, "/fxml/register.fxml", "");
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