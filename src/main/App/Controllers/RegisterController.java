package Controllers;

import Dtos.UserRegisterDTO;
import Enums.EnumProgram;
import Enums.EnumRole;
import Exceptions.RegisterUserFailerException;
import Interfaces.IUserRegisterServices;
import Interfaces.IValidatorRegisterServices;
import Models.Account;
import Models.User;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlador para la vista de registro de usuarios.
 *
 * Esta clase maneja toda la lógica de la interfaz de usuario para el registro de nuevos usuarios,
 * incluyendo validación de formularios, manejo de roles, y navegación entre vistas.
 *
 * Características principales:
 * - Validación en tiempo real de campos de entrada
 * - Gestión de roles con lógica excluyente (estudiante vs otros roles)
 * - Integración con servicios de validación y registro
 * - Manejo de errores con retroalimentación visual
 * - Navegación fluida entre vistas de autenticación
 *
 * @author juanserealpe
 */
public class RegisterController implements Initializable {

    // ==================== CAMPOS FXML ====================

    /** Campo de texto para ingresar los nombres del usuario */
    @FXML private TextField txtNames;

    /** Campo de texto para ingresar los apellidos del usuario */
    @FXML private TextField txtLastNames;

    /** Campo de texto para ingresar el email del usuario */
    @FXML private TextField txtEmail;

    /** Campo de contraseña para ingresar la contraseña */
    @FXML private PasswordField txtPassword;

    /** Campo de contraseña para confirmar la contraseña */
    @FXML private PasswordField txtConfirmPassword;

    /** Campo de texto para ingresar el teléfono (opcional) */
    @FXML private TextField txtPhone;

    // CheckBoxes
    /** Checkbox para aceptar términos y condiciones */
    @FXML private CheckBox chkTerms;

    /** Checkbox para seleccionar rol de estudiante */
    @FXML private CheckBox chkEstudiante;

    /** Checkbox para seleccionar rol de director */
    @FXML private CheckBox chkDirector;

    /** Checkbox para seleccionar rol de coordinador */
    @FXML private CheckBox chkCoordinador;

    /** Checkbox para seleccionar rol de jurado */
    @FXML private CheckBox chkJurado;

    // Labels de error
    /** Label para mostrar errores relacionados con el campo nombres */
    @FXML private Label lblNamesError;

    /** Label para mostrar errores relacionados con el campo apellidos */
    @FXML private Label lblLastNamesError;

    /** Label para mostrar errores relacionados con el campo email */
    @FXML private Label lblEmailError;

    /** Label para mostrar errores relacionados con la selección de roles */
    @FXML private Label lblRolesError;

    /** Label para mostrar errores relacionados con la contraseña */
    @FXML private Label lblPasswordError;

    /** Label para mostrar errores relacionados con la confirmación de contraseña */
    @FXML private Label lblConfirmPasswordError;

    /** Label para mostrar errores relacionados con el teléfono */
    @FXML private Label lblPhoneError;

    // Controles adicionales
    /** Label para mostrar mensajes generales del proceso de registro */
    @FXML private Label lblRegisterMessage;

    /** Botón para ejecutar el proceso de registro */
    @FXML private Button btnRegister;

    // ==================== SERVICIOS ====================

    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    private ServiceFactory serviceFactory;

    /** Servicio encargado de las validaciones del formulario de registro */
    private IValidatorRegisterServices _validatorService;

    /** Servicio encargado del registro de usuarios */
    private IUserRegisterServices _userServices;

    // ==================== INICIALIZACIÓN ====================

    /**
     * Establece la fábrica de servicios y configura los servicios dependientes.
     * Este método debe ser llamado después de la creación del controlador.
     *
     * @param serviceFactory La fábrica de servicios de la aplicación
     */
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this._validatorService = serviceFactory.getValidatorService();
        this._userServices = serviceFactory.getUserService();
    }

    /**
     * Método de inicialización de JavaFX llamado automáticamente después de cargar el FXML.
     * Configura las validaciones, la lógica de roles y limpia los mensajes.
     *
     * @param location La ubicación utilizada para resolver paths relativos para el objeto raíz
     * @param resources Los recursos utilizados para localizar el objeto raíz
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupValidation();
        setupRoleCheckBoxLogic();
        clearMessage();
    }

    /**
     * Configura la lógica de los checkboxes de roles.
     * Implementa la regla de negocio donde:
     * - El rol de estudiante es excluyente con otros roles
     * - Los demás roles (director, coordinador, jurado) son excluyentes con estudiante
     */
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

    /**
     * Habilita o deshabilita los checkboxes de roles no estudiantiles.
     *
     * @param disable true para deshabilitar, false para habilitar
     */
    private void disableOtherRoles(boolean disable) {
        chkDirector.setDisable(disable);
        chkCoordinador.setDisable(disable);
        chkJurado.setDisable(disable);
    }

    /**
     * Deselecciona todos los checkboxes de roles no estudiantiles.
     */
    private void clearOtherRolesSelection() {
        chkDirector.setSelected(false);
        chkCoordinador.setSelected(false);
        chkJurado.setSelected(false);
    }

    /**
     * Valida y gestiona la selección del rol de estudiante cuando se seleccionan otros roles.
     * Si hay algún otro rol seleccionado, deshabilita y deselecciona el rol de estudiante.
     */
    private void validateStudentRole() {
        boolean anyOtherRoleSelected = chkDirector.isSelected() ||
                chkCoordinador.isSelected() ||
                chkJurado.isSelected();

        chkEstudiante.setSelected(false);
        chkEstudiante.setDisable(anyOtherRoleSelected);
    }

    // ==================== CONFIGURACIÓN DE VALIDACIÓN ====================

    /**
     * Configura las validaciones para todos los campos del formulario.
     * Establece listeners que ejecutan validaciones en tiempo real.
     */
    private void setupValidation() {
        setupFieldValidation(txtNames, this::validateNames);
        setupFieldValidation(txtLastNames, this::validateLastNames);
        setupFieldValidation(txtEmail, this::validateEmail);
        setupFieldValidation(txtPhone, this::validateTelephone);
        setupPasswordValidation();
        setupTermsValidation();
    }

    /**
     * Configura la validación para un campo de texto específico.
     *
     * @param field El campo de texto a validar
     * @param validator El método de validación a ejecutar
     */
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

    /**
     * Configura la validación específica para los campos de contraseña.
     * Incluye validación individual y de confirmación.
     */
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

    /**
     * Configura la validación para el checkbox de términos y condiciones.
     */
    private void setupTermsValidation() {
        chkTerms.selectedProperty().addListener((obs, oldVal, newVal) -> updateRegisterButton());
    }

    /**
     * Interfaz funcional para métodos de validación.
     */
    @FunctionalInterface
    private interface ValidationMethod {
        void validate() throws RegisterUserFailerException;
    }

    // ==================== MANEJADORES DE EVENTOS ====================

    /**
     * Maneja el evento de registro de usuario.
     * Valida el formulario completo y, si es válido, procede con el registro
     * y navega a la vista de login.
     *
     * @param event El evento de acción del botón de registro
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
    @FXML
    private void handleRegister(ActionEvent event) throws RegisterUserFailerException {
        if (validateForm()) {
            try {
                // Crear DTO y registrar usuario
                UserRegisterDTO newUserDTO = createUserDTO();
                _userServices.registerUser(newUserDTO);

                // Obtener el Stage actual
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                // Cargar la vista de Login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
                Scene scene = new Scene(loader.load());

                // Obtener el controller de Login y pasarle la misma fábrica
                LoginController loginController = loader.getController();
                loginController.setServiceFactory(this.serviceFactory);

                // Cambiar la escena
                stage.setScene(scene);

            } catch (Exception e) {
                showErrorMessage("Error al crear la cuenta: " + e.getMessage());
            }
        }
    }

    /**
     * Maneja el evento de regreso a la vista de login.
     * Navega de vuelta a la pantalla de login manteniendo la instancia de ServiceFactory.
     *
     * @param event El evento de acción del botón de regreso
     * @throws IOException Si ocurre un error al cargar la vista
     */
    @FXML
    private void handleBackToLogin(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Cargar la vista de Login
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
        Scene scene = new Scene(loader.load());

        // Pasar la misma instancia de ServiceFactory al controller de Login
        LoginController loginController = loader.getController();
        loginController.setServiceFactory(this.serviceFactory);

        // Cambiar la escena
        stage.setScene(scene);
    }

    // ==================== CREACIÓN DE OBJETOS ====================

    /**
     * Crea un DTO (Data Transfer Object) con la información del formulario.
     * Combina los datos del usuario, cuenta y contraseña en un objeto único.
     *
     * @return UserRegisterDTO con todos los datos necesarios para el registro
     */
    private UserRegisterDTO createUserDTO() {
        List<EnumRole> selectedRoles = getSelectedRoles();

        User newUser = new User(
                txtNames.getText(),
                txtLastNames.getText(),
                txtPhone.getText()
        );

        Account newAccount = new Account();
        newAccount.setEmail(txtEmail.getText());
        newAccount.setProgram(EnumProgram.ING_SISTEMAS);
        newAccount.setRoles(selectedRoles);

        return new UserRegisterDTO(txtPassword.getText(),newUser, newAccount);
    }

    /**
     * Obtiene la lista de roles seleccionados en el formulario.
     *
     * @return Lista de EnumRole con los roles seleccionados
     */
    private List<EnumRole> getSelectedRoles() {
        List<EnumRole> selectedRoles = new ArrayList<>();

        if (chkEstudiante.isSelected()) selectedRoles.add(EnumRole.UNDERGRADUATE_STUDENT);
        if (chkDirector.isSelected()) selectedRoles.add(EnumRole.DIRECTOR);
        if (chkCoordinador.isSelected()) selectedRoles.add(EnumRole.COORDINATOR);
        if (chkJurado.isSelected()) selectedRoles.add(EnumRole.JURY);

        return selectedRoles;
    }

    // ==================== VALIDACIONES DE FORMULARIO ====================

    /**
     * Valida todo el formulario antes del registro.
     * Ejecuta todas las validaciones individuales y retorna true solo si todas pasan.
     *
     * @return true si el formulario es válido, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
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

    /**
     * Valida que se hayan aceptado los términos y condiciones.
     *
     * @return true si se aceptaron los términos, false en caso contrario
     */
    private boolean validateTermsAndConditions() {
        if (!chkTerms.isSelected()) {
            showErrorMessage("Debe aceptar los términos y condiciones");
            return false;
        }
        return true;
    }

    // ==================== VALIDACIONES DE CAMPOS INDIVIDUALES ====================

    /**
     * Valida el campo de nombres.
     *
     * @return true si el nombre es válido, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
    private boolean validateNames() throws RegisterUserFailerException {
        return validateField(
                txtNames.getText(),
                txtNames,
                lblNamesError,
                () -> _validatorService.isValidNames(txtNames.getText())
        );
    }

    /**
     * Valida el campo de apellidos.
     *
     * @return true si los apellidos son válidos, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
    private boolean validateLastNames() throws RegisterUserFailerException {
        return validateField(
                txtLastNames.getText(),
                txtLastNames,
                lblLastNamesError,
                () -> _validatorService.isValidLastNames(txtLastNames.getText())
        );
    }

    /**
     * Valida el campo de email.
     *
     * @return true si el email es válido, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
    private boolean validateEmail() throws RegisterUserFailerException {
        String email = txtEmail.getText().trim();
        return validateField(
                email,
                txtEmail,
                lblEmailError,
                () -> _validatorService.isValidEmail(email)
        );
    }

    /**
     * Valida el campo de teléfono.
     * Este campo es opcional, por lo que se considera válido si está vacío.
     *
     * @return true si el teléfono es válido o está vacío, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
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

    /**
     * Valida el campo de contraseña.
     *
     * @return true si la contraseña es válida, false en caso contrario
     */
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

    /**
     * Valida que la confirmación de contraseña coincida con la contraseña original.
     *
     * @return true si las contraseñas coinciden, false en caso contrario
     */
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

    /**
     * Método genérico para validar campos y gestionar la retroalimentación visual.
     *
     * @param value El valor a validar
     * @param field El control de interfaz asociado al campo
     * @param errorLabel El label donde mostrar errores
     * @param action La acción de validación a ejecutar
     * @return true si la validación pasa, false en caso contrario
     * @throws RegisterUserFailerException Si ocurre un error durante la validación
     */
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

    /**
     * Interfaz funcional para acciones de validación.
     */
    @FunctionalInterface
    private interface ValidationAction {
        void validate() throws RegisterUserFailerException;
    }

    // ==================== GESTIÓN DE ESTILOS Y MENSAJES ====================

    /**
     * Actualiza el estado y apariencia del botón de registro.
     * Habilita el botón solo cuando todos los campos requeridos están llenos
     * y cambia su estilo visual según su estado.
     */
    private void updateRegisterButton() {
        boolean allRequiredFieldsFilled = areRequiredFieldsFilled();

        btnRegister.setDisable(!allRequiredFieldsFilled);

        if (allRequiredFieldsFilled) {
            btnRegister.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-weight: bold;");
        } else {
            btnRegister.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        }
    }

    /**
     * Verifica si todos los campos requeridos del formulario están llenos.
     *
     * @return true si todos los campos requeridos están completos, false en caso contrario
     */
    private boolean areRequiredFieldsFilled() {
        return !txtNames.getText().isEmpty() &&
                !txtLastNames.getText().isEmpty() &&
                !txtEmail.getText().isEmpty() &&
                !txtPassword.getText().isEmpty() &&
                !txtConfirmPassword.getText().isEmpty() &&
                hasSelectedRole() &&
                chkTerms.isSelected();
    }

    /**
     * Verifica si se ha seleccionado al menos un rol.
     *
     * @return true si hay al menos un rol seleccionado, false en caso contrario
     */
    private boolean hasSelectedRole() {
        return chkEstudiante.isSelected() ||
                chkDirector.isSelected() ||
                chkCoordinador.isSelected() ||
                chkJurado.isSelected();
    }

    /**
     * Muestra un mensaje de error en el label correspondiente.
     *
     * @param errorLabel El label donde mostrar el error
     * @param message El mensaje de error a mostrar
     */
    private void showFieldError(Label errorLabel, String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    /**
     * Oculta el mensaje de error de un label específico.
     *
     * @param errorLabel El label cuyo error se desea ocultar
     */
    private void hideFieldError(Label errorLabel) {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        errorLabel.setText("");
    }

    /**
     * Aplica estilos de error a un control específico.
     *
     * @param field El control al que aplicar el estilo de error
     */
    private void setFieldError(Control field) {
        field.getStyleClass().removeAll("success");
        if (!field.getStyleClass().contains("error")) {
            field.getStyleClass().add("error");
        }
    }

    /**
     * Aplica estilos de éxito a un control específico.
     *
     * @param field El control al que aplicar el estilo de éxito
     */
    private void setFieldSuccess(Control field) {
        field.getStyleClass().removeAll("error");
        if (!field.getStyleClass().contains("success")) {
            field.getStyleClass().add("success");
        }
    }

    /**
     * Muestra un mensaje de error general en el formulario.
     *
     * @param message El mensaje de error a mostrar
     */
    private void showErrorMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("success");
        if (!lblRegisterMessage.getStyleClass().contains("message-label")) {
            lblRegisterMessage.getStyleClass().add("message-label");
        }
    }

    /**
     * Muestra un mensaje de éxito general en el formulario.
     *
     * @param message El mensaje de éxito a mostrar
     */
    private void showSuccessMessage(String message) {
        lblRegisterMessage.setText(message);
        lblRegisterMessage.getStyleClass().removeAll("message-label");
        lblRegisterMessage.getStyleClass().addAll("message-label", "success");
    }

    /**
     * Limpia el mensaje general del formulario.
     */
    private void clearMessage() {
        lblRegisterMessage.setText("");
    }

    // ==================== MÉTODOS DE LIMPIEZA ====================

    /**
     * Limpia todos los errores mostrados en los campos del formulario.
     */
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

    /**
     * Limpia todos los estilos aplicados a los campos del formulario.
     */
    private void clearFieldStyles() {
        txtNames.getStyleClass().removeAll("error", "success");
        txtLastNames.getStyleClass().removeAll("error", "success");
        txtEmail.getStyleClass().removeAll("error", "success");
        txtPassword.getStyleClass().removeAll("error", "success");
        txtConfirmPassword.getStyleClass().removeAll("error", "success");
        txtPhone.getStyleClass().removeAll("error", "success");
    }

    /**
     * Limpia completamente el formulario, restaurándolo a su estado inicial.
     * Incluye campos de texto, checkboxes, estilos y mensajes.
     */
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