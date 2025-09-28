package Controllers;

import Enums.EnumState;
import Models.FormatA;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.awt.Desktop;
import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Controlador para la tarjeta que muestra la información del FormatA del estudiante.
 * Presenta los datos de manera clara y permite acciones básicas como visualizar el PDF.
 */
public class FormatAStudentCardController {

    @FXML
    private VBox cardContainer;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblState;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTypeProcess;

    @FXML
    private Button btnViewPDF;

    @FXML
    private Button btnDownloadPDF;

    @FXML
    private HBox stateContainer;

    @FXML
    private HBox actionsContainer;

    private FormatA formatA;

    /**
     * Configura la tarjeta con los datos del FormatA.
     */
    public void setFormatAData(FormatA formatA) {
        this.formatA = formatA;
        updateCardDisplay();
    }

    /**
     * Actualiza la visualización de la tarjeta con los datos del FormatA.
     */
    private void updateCardDisplay() {
        if (formatA == null) return;

        // Configurar título
        if (lblTitle != null) {
            String title = formatA.getTittle() != null && !formatA.getTittle().isEmpty()
                    ? formatA.getTittle()
                    : "Formato A - Sin título";
            lblTitle.setText(title);
        }

        // Configurar estado
        if (lblState != null) {
            EnumState state = formatA.getState();
            lblState.setText(getStateDisplayText(state));
            updateStateStyle(state);
        }

        // Configurar fecha
        if (lblDate != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String dateText = formatA.getDate() != null
                    ? dateFormat.format(formatA.getDate())
                    : "Fecha no disponible";
            lblDate.setText("Fecha de creación: " + dateText);
        }


        lblTypeProcess.setText("Tipo: " + "FormatoA");

        // Configurar botones según disponibilidad del archivo
        updateActionButtons();
    }

    /**
     * Obtiene el texto a mostrar según el estado.
     */
    private String getStateDisplayText(EnumState state) {
        if (state == null) return "Estado desconocido";

        switch (state) {
            case ESPERA:
                return "En espera de revisión";
            case APROBADO:
                return "Aprobado";
            case RECHAZADO:
                return "Rechazado";
            default:
                return state.toString();
        }
    }

    /**
     * Aplica estilos según el estado del FormatA.
     */
    private void updateStateStyle(EnumState state) {
        if (lblState == null || stateContainer == null) return;

        // Limpiar clases de estado previas
        lblState.getStyleClass().removeAll("state-pending", "state-approved", "state-rejected", "state-review");
        stateContainer.getStyleClass().removeAll("state-container-pending", "state-container-approved",
                "state-container-rejected", "state-container-review");

        // Aplicar nuevas clases según el estado
        if (state != null) {
            switch (state) {
                case ESPERA:
                    lblState.getStyleClass().add("state-pending");
                    stateContainer.getStyleClass().add("state-container-pending");
                    break;
                case APROBADO:
                    lblState.getStyleClass().add("state-approved");
                    stateContainer.getStyleClass().add("state-container-approved");
                    break;
                case RECHAZADO:
                    lblState.getStyleClass().add("state-rejected");
                    stateContainer.getStyleClass().add("state-container-rejected");
                    break;
            }
        }
    }

    /**
     * Configura la disponibilidad de los botones de acción.
     */
    private void updateActionButtons() {
        boolean hasFile = formatA != null && formatA.getURL() != null && !formatA.getURL().isEmpty();

        if (btnViewPDF != null) {
            btnViewPDF.setDisable(!hasFile);
            btnViewPDF.setText(hasFile ? "👁️ Ver PDF" : "📄 Sin archivo");
        }

        if (btnDownloadPDF != null) {
            btnDownloadPDF.setDisable(!hasFile);
            btnDownloadPDF.setText(hasFile ? "💾 Descargar" : "Sin archivo");
        }
    }

    /**
     * Maneja el clic en el botón de ver PDF.
     */
    @FXML
    private void handleViewPDF() {
        if (formatA == null || formatA.getURL() == null || formatA.getURL().isEmpty()) {
            showAlert("Sin archivo", "No hay archivo PDF asociado a este Formato A.");
            return;
        }

        try {
            File pdfFile = new File(formatA.getURL());

            if (!pdfFile.exists()) {
                showAlert("Archivo no encontrado",
                        "El archivo PDF no se encuentra en la ruta especificada:\n" + formatA.getURL());
                return;
            }

            // Abrir el archivo con la aplicación por defecto del sistema
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(pdfFile);
                } else {
                    showAlert("No compatible", "Tu sistema no permite abrir archivos automáticamente.");
                }
            } else {
                showAlert("No compatible", "Esta funcionalidad no está disponible en tu sistema.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al abrir el archivo PDF:\n" + e.getMessage());
        }
    }

    /**
     * Maneja el clic en el botón de descargar PDF.
     */
    @FXML
    private void handleDownloadPDF() {
        if (formatA == null || formatA.getURL() == null || formatA.getURL().isEmpty()) {
            showAlert("Sin archivo", "No hay archivo PDF asociado a este Formato A.");
            return;
        }

        try {
            File sourceFile = new File(formatA.getURL());

            if (!sourceFile.exists()) {
                showAlert("Archivo no encontrado",
                        "El archivo PDF no se encuentra en la ruta especificada:\n" + formatA.getURL());
                return;
            }

            // Mostrar información del archivo (por ahora)
            // En una implementación completa, podrías abrir un FileChooser para guardar
            String info = String.format(
                    "Archivo: %s\nUbicación: %s\nTamaño: %.2f MB",
                    sourceFile.getName(),
                    sourceFile.getAbsolutePath(),
                    sourceFile.length() / (1024.0 * 1024.0)
            );

            showAlert("Información del archivo", info);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "Error al acceder al archivo:\n" + e.getMessage());
        }
    }

    /**
     * Muestra una alerta con el título y mensaje especificados.
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
        // Inicialización básica si es necesaria
    }

    /**
     * Obtiene el FormatA asociado a esta tarjeta.
     */
    public FormatA getFormatA() {
        return formatA;
    }
}