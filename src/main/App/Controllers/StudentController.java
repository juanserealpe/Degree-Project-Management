package Controllers;

import Interfaces.IDegreeWorkRepository;
import Models.FormatA;
import Models.Session;
import Services.StudentServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Controlador para la vista del estudiante.
 * Maneja la visualización del FormatA asociado al estudiante logueado.
 */
public class StudentController extends BaseController {

    @FXML
    private Pane SideMenuContainer;

    @FXML
    private VBox FormatAContainer;

    @FXML
    private Label lblWelcome;

    @FXML
    private Label lblNoFormatA;

    private StudentServices studentServices;

    /**
     * Inicializa los datos del estudiante y carga su FormatA.
     *
     * @param session Instancia de la sesión actual
     */
    public void initData(Session session) {
        try {
            studentServices = serviceFactory.getStudentService();

            // Inicializar el menú lateral
            initializeSideMenu(session);

            // Mostrar mensaje de bienvenida
            if (lblWelcome != null) {
                lblWelcome.setText("Bienvenido, " + session.getEmail());
            }

            // Cargar el FormatA del estudiante
            loadStudentFormatA(session.getId());

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al inicializar la vista del estudiante: " + e.getMessage());
        }
    }

    /**
     * Inicializa el menú lateral con solo la opción de cerrar sesión.
     */
    private void initializeSideMenu(Session session) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserViews/SideMenuStudent.fxml"));
            Node sideMenu = loader.load();

            SideMenuContainer.getChildren().clear();
            SideMenuContainer.getChildren().add(sideMenu);

            // Si necesitas pasar datos al controlador del menú
            SideMenuStudentController controller = loader.getController();
            if (controller != null) {
                controller.initData(session);
                controller.setServiceFactory(this.serviceFactory);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo cargar el menú: " + e.getMessage());
        }
    }

    /**
     * Carga y muestra el FormatA del estudiante.
     */
    private void loadStudentFormatA(int accountId) {
        try {
            FormatA formatA = studentServices.getFormatAByStudent(accountId);

            if (formatA != null) {
                // Ocultar mensaje de "no hay FormatA"
                if (lblNoFormatA != null) {
                    lblNoFormatA.setVisible(false);
                    lblNoFormatA.setManaged(false);
                }

                // Mostrar el FormatA
                displayFormatA(formatA);

            } else {
                // Mostrar mensaje de que no hay FormatA
                if (lblNoFormatA != null) {
                    lblNoFormatA.setVisible(true);
                    lblNoFormatA.setManaged(true);
                    lblNoFormatA.setText("No tienes un Formato A asignado aún.");
                }

                // Limpiar el contenedor
                if (FormatAContainer != null) {
                    FormatAContainer.getChildren().clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al cargar el Formato A: " + e.getMessage());

            if (lblNoFormatA != null) {
                lblNoFormatA.setVisible(true);
                lblNoFormatA.setManaged(true);
                lblNoFormatA.setText("Error al cargar el Formato A.");
            }
        }
    }

    /**
     * Muestra la información del FormatA en la interfaz.
     */
    private void displayFormatA(FormatA formatA) {
        try {
            // Cargar la vista de la tarjeta del FormatA
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserViews/FormatAStudentCard.fxml"));
            Node formatACard = loader.load();

            // Configurar el controlador de la tarjeta
            FormatAStudentCardController cardController = loader.getController();
            if (cardController != null) {
                cardController.setFormatAData(formatA);
            }

            // Limpiar el contenedor y agregar la tarjeta
            FormatAContainer.getChildren().clear();
            FormatAContainer.getChildren().add(formatACard);

        } catch (Exception e) {
            e.printStackTrace();

            // Fallback: mostrar información básica en labels
            FormatAContainer.getChildren().clear();

            Label titleLabel = new Label("Título: " + (formatA.getTittle() != null ? formatA.getTittle() : "Sin título"));
            titleLabel.getStyleClass().add("format-title");

            Label stateLabel = new Label("Estado: " + formatA.getState().toString());
            stateLabel.getStyleClass().add("format-state");

            Label dateLabel = new Label("Fecha: " + formatA.getDate().toString());
            dateLabel.getStyleClass().add("format-date");

            FormatAContainer.getChildren().addAll(titleLabel, stateLabel, dateLabel);
        }
    }

    /**
     * Recarga el FormatA del estudiante.
     */
    @FXML
    private void handleRefresh() {
        loadStudentFormatA(Session.getInstance().getId());
    }

    /**
     * Muestra una alerta con el mensaje especificado.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void initialize() {
        // Inicialización básica de la vista
        if (lblNoFormatA != null) {
            lblNoFormatA.setVisible(false);
            lblNoFormatA.setManaged(false);
        }
    }
}