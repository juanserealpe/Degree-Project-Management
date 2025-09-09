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

    @FXML private TextField txtNames;
    @FXML private TextField txtLastNames;
    @FXML private TextField txtEmail;
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private TextField txtPhone;
    @FXML private CheckBox chkTerms;
    @FXML private Label lblRegisterMessage;
    @FXML private Button btnRegister;
    @FXML private CheckBox chkEstudiante;
    @FXML private CheckBox chkDirector;
    @FXML private CheckBox chkCoordinador;
    @FXML private CheckBox chkJurado;
    @FXML private Label lblNamesError;
    @FXML private Label lblLastNamesError;
    @FXML private Label lblEmailError;
    @FXML private Label lblRolesError;
    @FXML private Label lblPasswordError;
    @FXML private Label lblConfirmPasswordError;
    @FXML private Label lblPhoneError;

    private IValidatorRegisterServices _validatorService;
    private IUserServices _userServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidation();
        clearMessage();
        this._validatorService = ServiceFactory.getValidatorService();
        chkEstudiante.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                chkDirector.setSelected(false);
                chkCoordinador.setSelected(false);
                chkJurado.setSelected(false);

                chkDirector.setDisable(true);
                chkCoordinador.setDisable(true);
                chkJurado.setDisable(true);
            } else {
                chkDirector.setDisable(false);
                chkCoordinador.setDisable(false);
                chkJurado.setDisable(false);
            }
        });
        chkDirector.selectedProperty().addListener((obs, oldVal, newVal) -> validarEstudiante());
        chkCoordinador.selectedProperty().addListener((obs, oldVal, newVal) -> validarEstudiante());
        chkJurado.selectedProperty().addListener((obs, oldVal, newVal) -> validarEstudiante());
        this._userServices = ServiceFactory.getUserService();
    }

    /**
     * Configura la validación en tiempo real de los campos
     */
    private void setupValidation() {
        // Nombres
        txtNames.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validateNames();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });

        // Apellidos
        txtLastNames.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validateLastNames();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });

        // Email
        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validateEmail();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });

        // Teléfono
        txtPhone.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validateTelephone();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });

        // Contraseña y confirmación
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
        //Teléfono
        // En tu inicialización (por ejemplo initialize() en el controller)
        txtPhone.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.isEmpty()) {
                try {
                    validateTelephone();
                } catch (RegisterUserFailerException e) {
                    throw new RuntimeException(e);
                }
            }
            updateRegisterButton();
        });



        // Términos
        chkTerms.selectedProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
    }

    /**
     * Maneja el evento de registro
     */
    @FXML
    private void handleRegister(ActionEvent event) throws RegisterUserFailerException {
        if (validateForm()) {
            try {
                List<Role> selectedRoles = new ArrayList<>();

                if (chkEstudiante.isSelected()) selectedRoles.add(new Role(1, "Estudiante"));
                if (chkDirector.isSelected()) selectedRoles.add(new Role(2, "Director"));
                if (chkCoordinador.isSelected()) selectedRoles.add(new Role(3, "Coordinador"));
                if (chkJurado.isSelected()) selectedRoles.add(new Role(4, "Jurado"));
                //String email, String names, String lastNames, int accountId, int programId, List<Role> listRole, String numberPhone
                User newUser = new User(txtEmail.getText(), txtNames.getText(), txtLastNames.getText(), 0, 1,selectedRoles, txtPhone.getText());
                Account newAccount = new Account(0, txtPassword.getText());
                UserDTO newUserDTO = new UserDTO(newUser, newAccount);
                _userServices.registerUser(newUserDTO);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setUserData("Registro exitoso."); //
                WindowManager.changeScene(stage, "/fxml/login.fxml", "");


            } catch (Exception e) {
                showErrorMessage("Error al crear la cuenta: " + e.getMessage());
            }
        }
    }

    /**
     * Maneja el evento de volver al login
     */
    @FXML
    private void handleBackToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        WindowManager.changeScene(stage, "/fxml/login.fxml", "");
    }

    /**
     * Valida todo el formulario
     */
    private boolean validateForm() throws RegisterUserFailerException {
        boolean isValid = true;
        //clearFieldStyles();
        clearMessage();
        // Validar nombres
        if (!validateNames()) return false;
        // Validar apellidos
        if (!validateLastNames()) return false;
        // Validar email
        if (!validateEmail()) return false;
        // Validar contraseña
        if (!validatePassword()) return false;
        // Validar telefono
        if (!validateTelephone()) return false;
        // Validar términos y condiciones
        if (!chkTerms.isSelected()) {
            showErrorMessage("Debe aceptar los términos y condiciones");
            return false;
        }

        return isValid;
    }

    /**
     * Valida los nombres
     */
    private boolean validateNames() throws RegisterUserFailerException {
        try{
            _validatorService.isValidNames(txtNames.getText());
            setFieldSuccess(txtNames);
            hideFieldError(lblNamesError);
            return true;
        }catch (RegisterUserFailerException e){
            setFieldError(txtNames);
            lblNamesError.setText(e.getMessage());
            lblNamesError.setVisible(true);
            lblNamesError.setManaged(true);
            return false;
        }
    }

    /**
     * Valida los apellidos
     */
    private boolean validateLastNames() throws RegisterUserFailerException {
        try{
            _validatorService.isValidLastNames(txtLastNames.getText());
            setFieldSuccess(txtLastNames);
            hideFieldError(lblLastNamesError);
            return true;
        }catch (RegisterUserFailerException e){
            setFieldError(txtLastNames);
            lblLastNamesError.setText(e.getMessage());
            lblLastNamesError.setVisible(true);
            lblLastNamesError.setManaged(true);
            return false;
        }
    }

    /**
     * Valida el teléfono
     */
    private boolean validateTelephone() throws RegisterUserFailerException {
        try{
            _validatorService.isValidTelephone(txtPhone.getText());
            setFieldSuccess(txtPhone);
            hideFieldError(lblPhoneError);
            return true;
        }catch (RegisterUserFailerException e){
            setFieldError(txtPhone);
            lblPhoneError.setText(e.getMessage());
            lblPhoneError.setVisible(true);
            lblPhoneError.setManaged(true);
            return false;
        }
    }

    /**
     * Valida el email
     */
    private boolean validateEmail() throws RegisterUserFailerException {
        String email = txtEmail.getText().trim();
        try{
            _validatorService.isValidEmail(email);
            setFieldSuccess(txtEmail);
            hideFieldError(lblEmailError);
            return true;
        }
        catch (RegisterUserFailerException e){
            setFieldError(txtEmail);
            lblEmailError.setText(e.getMessage());
            lblEmailError.setVisible(true);
            lblEmailError.setManaged(true);
            return false;
        }
    }

    /**
     * Valida las contraseñas
     */
    private boolean validatePassword() {
        String password = txtPassword.getText();
        try{
            _validatorService.isValidPassword(password);
            setFieldSuccess(txtPassword);
            hideFieldError(lblPasswordError);
        }catch (RegisterUserFailerException e){
            setFieldError(txtPassword);
            lblPasswordError.setText(e.getMessage());
            lblPasswordError.setVisible(true);
            lblPasswordError.setManaged(true);
            return false;
        }


        return true;
    }

    /**
     * Valida la confimación de la contraseña
     */
    private boolean validateConfirmPassword() {
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        try{
            _validatorService.validateConfirmPassword(password, confirmPassword);
            setFieldSuccess(txtConfirmPassword);
            hideFieldError(lblConfirmPasswordError);
        }catch (RegisterUserFailerException e){
            setFieldError(txtConfirmPassword);
            lblConfirmPasswordError.setText(e.getMessage());
            lblConfirmPasswordError.setVisible(true);
            lblConfirmPasswordError.setManaged(true);
            return false;
        }
        return true;
    }

    /**
     * Actualiza el estado del botón de registro
     */
    private void updateRegisterButton() {
        boolean allRequiredFieldsFilled =
                !txtNames.getText().isEmpty() &&
                        !txtLastNames.getText().isEmpty() &&
                        !txtEmail.getText().isEmpty() &&
                        //!txtPhone.getText().isEmpty() &&
                        !txtPassword.getText().isEmpty() &&
                        !txtConfirmPassword.getText().isEmpty() &&
                        chkTerms.isSelected();

        btnRegister.setDisable(!allRequiredFieldsFilled);

        if (allRequiredFieldsFilled) {
            btnRegister.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btnRegister.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        }
    }


    /**
     * Muestra error en un campo específico
     */
    private void showFieldError(Control field, Label errorLabel, String message) {
        // Aplicar estilo de error al campo
        setFieldError(field);

        // Mostrar el mensaje de error
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    /**
     * Oculta el error de un campo específico
     */
    private void hideFieldError(Label errorLabel) {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setText("");
    }

    /**
     * Limpia todos los errores de campo
     */
    private void clearAllFieldErrors() {
        // Ocultar todos los mensajes de error
        hideFieldError(lblNamesError);
        hideFieldError(lblLastNamesError);
        hideFieldError(lblEmailError);
        hideFieldError(lblRolesError);
        hideFieldError(lblPasswordError);
        hideFieldError(lblConfirmPasswordError);

        // Limpiar estilos de los campos
        clearFieldStyles();
    }

    /**
     * Limpia los estilos de error/éxito de todos los campos
     */
    private void clearFieldStyles() {
        txtNames.getStyleClass().removeAll("error", "success");
        txtEmail.getStyleClass().removeAll("error", "success");
        txtUsername.getStyleClass().removeAll("error", "success");
        txtPassword.getStyleClass().removeAll("error", "success");
        txtConfirmPassword.getStyleClass().removeAll("error", "success");
        txtPhone.getStyleClass().removeAll("error", "success");
    }

    /**
     * Remueve un estilo específico de un campo
     */
    private void removeFieldStyle(Control field, String styleClass) {
        field.getStyleClass().remove(styleClass);
    }

    /**
     * Aplica estilo de error a un campo
     */
    private void setFieldError(Control field) {
        field.getStyleClass().removeAll("success");
        if (!field.getStyleClass().contains("error")) {
            field.getStyleClass().add("error");
        }
    }

    /**
     * Aplica estilo de éxito a un campo
     */
    private void setFieldSuccess(Control field) {
        field.getStyleClass().removeAll("error");
        if (!field.getStyleClass().contains("success")) {
            field.getStyleClass().add("success");
        }
    }

    /**
     * Muestra mensaje de error
     */
    private void showErrorMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("success");
        if (!lblRegisterMessage.getStyleClass().contains("message-label")) {
            lblRegisterMessage.getStyleClass().add("message-label");
        }
    }

    /**
     * Muestra mensaje de éxito
     */
    private void showSuccessMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("message-label");
        lblRegisterMessage.getStyleClass().addAll("message-label", "success");
    }

    /**
     * Limpia el mensaje
     */
    private void clearMessage() {
        lblRegisterMessage.setText("");
    }

    /**
     * Valida Roles
     */
    private void validarEstudiante() {
        if (chkDirector.isSelected() || chkCoordinador.isSelected() || chkJurado.isSelected()) {
            chkEstudiante.setSelected(false);
            chkEstudiante.setDisable(true);
        } else {
            chkEstudiante.setDisable(false);
        }
    }

    /**
     * Limpia todo el formulario
     */
    private void clearForm() {
        txtNames.clear();
        txtEmail.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();
        txtPhone.clear();
        chkTerms.setSelected(false);
        clearFieldStyles();
        clearMessage();
    }
}