package Controllers;

import Dtos.UserDTO;
import Exceptions.RegisterUserFailerException;
import Interfaces.IUserServices;
import Interfaces.IValidatorRegisterServices;
import Models.Account;
import Models.Role;
import Models.User;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    // ==================== CAMPOS FXML ====================

    // Campos de entrada
    @FXML private TextField txtNames;
    @FXML private TextField txtLastNames;
    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtPhone;

    // CheckBoxes
    @FXML private CheckBox chkTerms;
    @FXML private CheckBox chkEstudiante;
    @FXML private CheckBox chkDirector;
    @FXML private CheckBox chkCoordinador;
    @FXML private CheckBox chkJurado;

    // Labels de error
    @FXML private Label lblNamesError;
    @FXML private Label lblLastNamesError;
    @FXML private Label lblEmailError;
    @FXML private Label lblRolesError;
    @FXML private Label lblPasswordError;
    @FXML private Label lblConfirmPasswordError;
    @FXML private Label lblPhoneError;

    // Controles adicionales
    @FXML private Label lblRegisterMessage;
    @FXML private Button btnRegister;

    // ==================== SERVICIOS ====================

    private IValidatorRegisterServices _validatorService;
    private IUserServices _userServices;

    // ==================== INICIALIZACIÓN ====================

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeServices();
        setupValidation();
        setupRoleCheckBoxLogic();
        clearMessage();
    }

    private void initializeServices() {
        this._validatorService = ServiceFactory.getValidatorService();
        this._userServices = ServiceFactory.getUserService();
    }

    private void setupRoleCheckBoxLogic() {
        // Lógica para estudiante - excluyente con otros roles
        chkEstudiante.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                disableOtherRoles(true);
                clearOtherRolesSelection();
            } else {
                disableOtherRoles(false);
            }
        });

        // Lógica para otros roles - excluyentes con estudiante
        chkDirector.selectedProperty().addListener((obs, oldVal, newVal) -> validateStudentRole());
        chkCoordinador.selectedProperty().addListener((obs, oldVal, newVal) -> validateStudentRole());
        chkJurado.selectedProperty().addListener((obs, oldVal, newVal) -> validateStudentRole());
    }

    private void disableOtherRoles(boolean disable) {
        chkDirector.setDisable(disable);
        chkCoordinador.setDisable(disable);
        chkJurado.setDisable(disable);
    }

    private void clearOtherRolesSelection() {
        chkDirector.setSelected(false);
        chkCoordinador.setSelected(false);
        chkJurado.setSelected(false);
    }

    private void validateStudentRole() {
        boolean anyOtherRoleSelected = chkDirector.isSelected() ||
                chkCoordinador.isSelected() ||
                chkJurado.isSelected();

        chkEstudiante.setSelected(false);
        chkEstudiante.setDisable(anyOtherRoleSelected);
    }

    // ==================== CONFIGURACIÓN DE VALIDACIÓN ====================

    private void setupValidation() {
        setupFieldValidation(txtNames, this::validateNames);
        setupFieldValidation(txtLastNames, this::validateLastNames);
        setupFieldValidation(txtEmail, this::validateEmail);
        setupFieldValidation(txtPhone, this::validateTelephone);
        setupPasswordValidation();
        setupTermsValidation();
    }

    private void setupFieldValidation(TextField field, ValidationMethod validator) {
        field.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validator.validate();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });
    }

    private void setupPasswordValidation() {
        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                validatePassword();
            }
            updateRegisterButton();
        });

        txtConfirmPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                validateConfirmPassword();
            }
            updateRegisterButton();
        });
    }

    private void setupTermsValidation() {
        chkTerms.selectedProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
    }

    @FunctionalInterface
    private interface ValidationMethod {
        void validate() throws RegisterUserFailerException;
    }

    // ==================== MANEJADORES DE EVENTOS ====================

    @FXML
    private void handleRegister(ActionEvent event) throws RegisterUserFailerException {
        if (validateForm()) {
            try {
                UserDTO newUserDTO = createUserDTO();
                _userServices.registerUser(newUserDTO);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setUserData("Registro exitoso.");
                WindowManager.changeScene(stage, "/fxml/LoginVIew.fxml", "");

            } catch (Exception e) {
                showErrorMessage("Error al crear la cuenta: " + e.getMessage());
            }
        }
    }

    @FXML
    private void handleBackToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.changeScene(stage, "/fxml/LoginVIew.fxml", "");
    }

    // ==================== CREACIÓN DE OBJETOS ====================

    private UserDTO createUserDTO() {
        List<Role> selectedRoles = getSelectedRoles();

        User newUser = new User(
                txtEmail.getText(),
                txtNames.getText(),
                txtLastNames.getText(),
                0,
                1,
                selectedRoles,
                txtPhone.getText()
        );

        Account newAccount = new Account(0, txtPassword.getText());

        return new UserDTO(newUser, newAccount);
    }

    private List<Role> getSelectedRoles() {
        List<Role> selectedRoles = new ArrayList<>();

        if (chkEstudiante.isSelected()) selectedRoles.add(new Role(1, "Estudiante"));
        if (chkDirector.isSelected()) selectedRoles.add(new Role(2, "Director"));
        if (chkCoordinador.isSelected()) selectedRoles.add(new Role(3, "Coordinador"));
        if (chkJurado.isSelected()) selectedRoles.add(new Role(4, "Jurado"));

        return selectedRoles;
    }

    // ==================== VALIDACIONES DE FORMULARIO ====================

    private boolean validateForm() throws RegisterUserFailerException {
        clearMessage();

        boolean isValid = true;
        isValid &= validateNames();
        isValid &= validateLastNames();
        isValid &= validateEmail();
        isValid &= validatePassword();
        isValid &= validateTelephone();
        isValid &= validateTermsAndConditions();

        return isValid;
    }

    private boolean validateTermsAndConditions() {
        if (!chkTerms.isSelected()) {
            showErrorMessage("Debe aceptar los términos y condiciones");
            return false;
        }
        return true;
    }

    // ==================== VALIDACIONES DE CAMPOS INDIVIDUALES ====================

    private boolean validateNames() throws RegisterUserFailerException {
        return validateField(
                txtNames.getText(),
                txtNames,
                lblNamesError,
                () -> _validatorService.isValidNames(txtNames.getText())
        );
    }

    private boolean validateLastNames() throws RegisterUserFailerException {
        return validateField(
                txtLastNames.getText(),
                txtLastNames,
                lblLastNamesError,
                () -> _validatorService.isValidLastNames(txtLastNames.getText())
        );
    }

    private boolean validateEmail() throws RegisterUserFailerException {
        String email = txtEmail.getText().trim();
        return validateField(
                email,
                txtEmail,
                lblEmailError,
                () -> _validatorService.isValidEmail(email)
        );
    }

    private boolean validateTelephone() throws RegisterUserFailerException {
        String phone = txtPhone.getText().trim();

        // El teléfono es opcional
        if (phone.isEmpty()) {
            setFieldSuccess(txtPhone);
            hideFieldError(lblPhoneError);
            return true;
        }

        return validateField(
                phone,
                txtPhone,
                lblPhoneError,
                () -> _validatorService.isValidTelephone(phone)
        );
    }

    private boolean validatePassword() {
        String password = txtPassword.getText();
        try {
            return validateField(
                    password,
                    txtPassword,
                    lblPasswordError,
                    () -> _validatorService.isValidPassword(password)
            );
        } catch (RegisterUserFailerException e) {
            return false;
        }
    }

    private boolean validateConfirmPassword() {
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        try {
            return validateField(
                    confirmPassword,
                    txtConfirmPassword,
                    lblConfirmPasswordError,
                    () -> _validatorService.validateConfirmPassword(password, confirmPassword)
            );
        } catch (RegisterUserFailerException e) {
            return false;
        }
    }

    // ==================== MÉTODO GENÉRICO DE VALIDACIÓN ====================

    private boolean validateField(String value, Control field, Label errorLabel, ValidationAction action)
            throws RegisterUserFailerException {
        try {
            action.validate();
            setFieldSuccess(field);
            hideFieldError(errorLabel);
            return true;
        } catch (RegisterUserFailerException e) {
            setFieldError(field);
            showFieldError(errorLabel, e.getMessage());
            return false;
        }
    }

    @FunctionalInterface
    private interface ValidationAction {
        void validate() throws RegisterUserFailerException;
    }

    // ==================== GESTIÓN DE ESTILOS Y MENSAJES ====================

    private void updateRegisterButton() {
        boolean allRequiredFieldsFilled = areRequiredFieldsFilled();

        btnRegister.setDisable(!allRequiredFieldsFilled);

        if (allRequiredFieldsFilled) {
            btnRegister.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btnRegister.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        }
    }

    private boolean areRequiredFieldsFilled() {
        return !txtNames.getText().isEmpty() &&
                !txtLastNames.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtPassword.getText().isEmpty() &&
                !txtConfirmPassword.getText().isEmpty() &&
                hasSelectedRole() &&
                chkTerms.isSelected();
    }

    private boolean hasSelectedRole() {
        return chkEstudiante.isSelected() ||
                chkDirector.isSelected() ||
                chkCoordinador.isSelected() ||
                chkJurado.isSelected();
    }

    private void showFieldError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideFieldError(Label errorLabel) {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setText("");
    }

    private void setFieldError(Control field) {
        field.getStyleClass().removeAll("success");
        if (!field.getStyleClass().contains("error")) {
            field.getStyleClass().add("error");
        }
    }

    private void setFieldSuccess(Control field) {
        field.getStyleClass().removeAll("error");
        if (!field.getStyleClass().contains("success")) {
            field.getStyleClass().add("success");
        }
    }

    private void showErrorMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("success");
        if (!lblRegisterMessage.getStyleClass().contains("message-label")) {
            lblRegisterMessage.getStyleClass().add("message-label");
        }
    }

    private void showSuccessMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("message-label");
        lblRegisterMessage.getStyleClass().addAll("message-label", "success");
    }

    private void clearMessage() {
        lblRegisterMessage.setText("");
    }

    // ==================== MÉTODOS DE LIMPIEZA ====================

    private void clearAllFieldErrors() {
        hideFieldError(lblNamesError);
        hideFieldError(lblLastNamesError);
        hideFieldError(lblEmailError);
        hideFieldError(lblRolesError);
        hideFieldError(lblPasswordError);
        hideFieldError(lblConfirmPasswordError);
        hideFieldError(lblPhoneError);
        clearFieldStyles();
    }

    private void clearFieldStyles() {
        txtNames.getStyleClass().removeAll("error", "success");
        txtLastNames.getStyleClass().removeAll("error", "success");
        txtEmail.getStyleClass().removeAll("error", "success");
        txtPassword.getStyleClass().removeAll("error", "success");
        txtConfirmPassword.getStyleClass().removeAll("error", "success");
        txtPhone.getStyleClass().removeAll("error", "success");
    }

    private void clearForm() {
        txtNames.clear();
        txtLastNames.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtPhone.clear();
        chkTerms.setSelected(false);

        // Limpiar selección de roles
        chkEstudiante.setSelected(false);
        chkDirector.setSelected(false);
        chkCoordinador.setSelected(false);
        chkJurado.setSelected(false);

        clearFieldStyles();
        clearMessage();
    }
}