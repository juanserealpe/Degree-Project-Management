package Controllers;

import Interfaces.IAuthService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.event.ActionEvent; // ✔ correcto
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField txtPass;
    @FXML
    private Label lblMessage;
    @FXML
    private Button loginButton;

    private IAuthService authService;

    @FXML
    public void initialize() {
        // Obtiene el servicio al cargar el FXML
        this.authService = ServiceFactory.getAuthService();
    }


    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String email = txtUser.getText();
        String password = txtPass.getText();

        try {
            authService.isLoginValid(email, password);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            WindowManager.changeScene(stage, "/fxml/dashboard.fxml", "Dashboard");

        } catch (Exception ex) {
            showErrorMessage(ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }



    @FXML
    private void handleRegister() {
        showInfoMessage("Redirigiendo a registro...");
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