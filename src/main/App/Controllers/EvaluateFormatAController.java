package Controllers;

import Models.DegreeWork;
import Models.FormatA;
import Enums.EnumState;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.embed.swing.SwingFXUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class EvaluateFormatAController implements Initializable {

    @FXML private Label tituloLabel;
    @FXML private VBox pdfContainer;
    @FXML private ScrollPane pdfScrollPane;
    @FXML private TextArea comentariosTextArea;
    @FXML private Label errorLabel;
    @FXML private Button aprobarButton;
    @FXML private Button rechazarButton;
    @FXML private Button cancelarButton;

    private DegreeWork degreeWork;
    private Consumer<CalificacionResultado> callback;
    private PDDocument pdfDocument;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEventHandlers();
    }

    public void initData(DegreeWork degreeWork, Consumer<CalificacionResultado> callback) {
        this.degreeWork = degreeWork;
        this.callback = callback;

        loadData();
    }

    private void loadData() {
        // Obtener el FormatA del DegreeWork
        FormatA formatA = getFormatAFromDegreeWork();
        if (formatA != null) {
            tituloLabel.setText(formatA.getTittle() != null ? formatA.getTittle() : "Sin título");
        }

        // Cargar y visualizar el PDF
        loadPdfPreview();
    }

    private FormatA getFormatAFromDegreeWork() {
        if (degreeWork != null && degreeWork.getProcesses() != null && !degreeWork.getProcesses().isEmpty()) {
            return (FormatA) degreeWork.getProcesses().get(0);
        }
        return null;
    }

    private void loadPdfPreview() {
        FormatA format = getFormatAFromDegreeWork();
        String pdfUrl = format.getURL();
        System.out.println("por imprimir pdf con url: " + pdfUrl);
        try {
            if (format == null) {
                showPdfError("No se encontró el Formato A asociado.");
                return;
            }

            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                File pdfFile = new File(pdfUrl);
                if (pdfFile.exists()) {
                    pdfDocument = PDDocument.load(pdfFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

                    // Renderizar la primera página como preview - CORRECCIÓN AQUÍ
                    BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 100); // 100 DPI para buena calidad

                    // Convertir BufferedImage a Image de JavaFX
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                    ImageView imageView = new ImageView(image);
                    imageView.setPreserveRatio(true);
                    imageView.setFitWidth(580);

                    pdfScrollPane.setContent(imageView);
                } else {
                    showPdfError("El archivo PDF no existe en la ruta especificada: " + pdfUrl);
                }
            } else {
                showPdfError("No hay PDF asociado a este trabajo de grado.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            showPdfError("Error al cargar el PDF: " + e.getMessage());
        }
    }

    private void showPdfError(String message) {
        Label errorLabel = new Label(message);
        errorLabel.setWrapText(true);
        errorLabel.setStyle("-fx-text-fill: red; -fx-padding: 10;");
        pdfScrollPane.setContent(errorLabel);
    }

    private void setupEventHandlers() {
        aprobarButton.setOnAction(event -> handleAprobar());
        rechazarButton.setOnAction(event -> handleRechazar());
        cancelarButton.setOnAction(event -> handleCancelar());
    }

    private void handleAprobar() {
        if (validateAprobacion()) {
            FormatA formatA = getFormatAFromDegreeWork();
            CalificacionResultado resultado = new CalificacionResultado(
                    degreeWork,
                    formatA,
                    EnumState.APROBADO,
                    comentariosTextArea.getText()
            );
            closeModal(resultado);
        }
    }

    private void handleRechazar() {
        if (validateRechazo()) {
            FormatA formatA = getFormatAFromDegreeWork();
            CalificacionResultado resultado = new CalificacionResultado(
                    degreeWork,
                    formatA,
                    EnumState.RECHAZADO,
                    comentariosTextArea.getText()
            );
            closeModal(resultado);
        }
    }

    private void handleCancelar() {
        closeModal(null);
    }

    private boolean validateAprobacion() {
        errorLabel.setVisible(false);
        // Para aprobar, los comentarios son opcionales
        return true;
    }

    private boolean validateRechazo() {
        errorLabel.setVisible(false);

        if (comentariosTextArea.getText() == null || comentariosTextArea.getText().trim().isEmpty()) {
            errorLabel.setText("Para rechazar el formato debe escribir un comentario explicando las razones.");
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private void closeModal(CalificacionResultado resultado) {
        // Cerrar el documento PDF
        if (pdfDocument != null) {
            try {
                pdfDocument.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Cerrar la ventana
        cancelarButton.getScene().getWindow().hide();

        // Llamar al callback si existe
        if (callback != null && resultado != null) {
            callback.accept(resultado);
        }
    }
}

// Clase para encapsular el resultado de la calificación (mantener igual)
class CalificacionResultado {
    private DegreeWork degreeWork;
    private FormatA formatA;
    private EnumState nuevoEstado;
    private String comentarios;

    public CalificacionResultado(DegreeWork degreeWork, FormatA formatA, EnumState nuevoEstado, String comentarios) {
        this.degreeWork = degreeWork;
        this.formatA = formatA;
        this.nuevoEstado = nuevoEstado;
        this.comentarios = comentarios;
    }

    // Getters
    public DegreeWork getDegreeWork() { return degreeWork; }
    public FormatA getFormatA() { return formatA; }
    public EnumState getNuevoEstado() { return nuevoEstado; }
    public String getComentarios() { return comentarios; }
}