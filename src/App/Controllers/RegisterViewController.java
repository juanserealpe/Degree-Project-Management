package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RegisterViewController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField apellidoField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleCombo;

    @FXML
    private ComboBox<String> programCombo;

    @FXML
    private Button registerButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox passwordTooltip;

    @FXML
    private Label lengthCheck;

    @FXML
    private Label uppercaseCheck;

    @FXML
    private Label lowercaseCheck;

    @FXML
    private Label digitCheck;

    @FXML
    private Label specialCheck;

    @FXML
    public void initialize() {
        // Configurar el comportamiento del tooltip de contraseña
        setupPasswordValidation();

        // Configurar acción del botón de registro
        registerButton.setOnAction(event -> handleRegister());

        // Configurar acción del botón de volver
        backButton.setOnAction(event -> handleBack());
    }

    private void setupPasswordValidation() {
        // Mostrar/ocultar tooltip al enfocar/desenfocar el campo de contraseña
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                passwordTooltip.setVisible(true);
                passwordTooltip.toFront(); // Asegurar que está sobre otros elementos
            } else {
                passwordTooltip.setVisible(false);
            }
        });

        // Validar contraseña en tiempo real
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            validatePassword(newValue);
        });
    }

    private void validatePassword(String password) {
        // Validar longitud mínima
        if (password.length() >= 8) {
            lengthCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            lengthCheck.setTextFill(javafx.scene.paint.Color.GRAY);
        }

        // Validar mayúscula
        if (password.matches(".*[A-Z].*")) {
            uppercaseCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            uppercaseCheck.setTextFill(javafx.scene.paint.Color.GRAY);
        }

        // Validar minúscula
        if (password.matches(".*[a-z].*")) {
            lowercaseCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            lowercaseCheck.setTextFill(javafx.scene.paint.Color.GRAY);
        }

        // Validar dígito
        if (password.matches(".*\\d.*")) {
            digitCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            digitCheck.setTextFill(javafx.scene.paint.Color.GRAY);
        }

        // Validar carácter especial
        if (password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            specialCheck.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            specialCheck.setTextFill(javafx.scene.paint.Color.GRAY);
        }
    }

    private void handleRegister() {
        // Validar todos los campos
        if (validateAllFields()) {
            // Aquí iría la lógica para registrar al usuario
            System.out.println("Registro exitoso");
            showAlert(Alert.AlertType.INFORMATION, "Registro exitoso", "Usuario registrado correctamente");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error de validación", "Por favor complete todos los campos correctamente");
        }
    }

    private boolean validateAllFields() {
        boolean isValid = true;

        // Validar nombre
        if (nombreField.getText().isEmpty()) {
            nombreField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            nombreField.setStyle("");
        }

        // Validar apellido
        if (apellidoField.getText().isEmpty()) {
            apellidoField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            apellidoField.setStyle("");
        }

        // Validar email
        if (emailField.getText().isEmpty() || !emailField.getText().contains("@")) {
            emailField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            emailField.setStyle("");
        }

        // Validar contraseña
        if (passwordField.getText().isEmpty() || !isPasswordValid(passwordField.getText())) {
            passwordField.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            passwordField.setStyle("");
        }

        // Validar rol
        if (roleCombo.getValue() == null) {
            roleCombo.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            roleCombo.setStyle("");
        }

        // Validar programa
        if (programCombo.getValue() == null) {
            programCombo.setStyle("-fx-border-color: red;");
            isValid = false;
        } else {
            programCombo.setStyle("");
        }

        return isValid;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    private void handleBack() {
        // Lógica para volver a la pantalla anterior
        System.out.println("Volviendo a la pantalla anterior");
        // Ejemplo:
        // Stage stage = (Stage) backButton.getScene().getWindow();
        // stage.setScene(previousScene);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}