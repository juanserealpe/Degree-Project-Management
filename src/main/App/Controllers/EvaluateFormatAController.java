package Controllers;

import Interfaces.DoingSomething;
import Models.DegreeWork;
import Models.FormatA;
import Enums.EnumState;
import Services.CoordinatorService;
import Services.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.embed.swing.SwingFXUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EvaluateFormatAController extends BaseController implements Initializable {

    @FXML private Label tituloLabel;
    @FXML private ScrollPane pdfScrollPane;
    @FXML private TextArea comentariosTextArea;
    @FXML private Label errorLabel;
    @FXML private Button aprobarButton;
    @FXML private Button rechazarButton;
    @FXML private Button cancelarButton;

    private DegreeWork degreeWork;
    private DoingSomething<Boolean> callbackResultado;
    private PDDocument pdfDocument;
    private CoordinatorService coordinatorService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupEventHandlers();
    }

    public void initData(DegreeWork degreeWork, DoingSomething<Boolean> callbackResultado) {
        this.degreeWork = degreeWork;
        this.callbackResultado = callbackResultado;
        loadData();
    }

    @Override
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
        this.coordinatorService = serviceFactory.getCoordinatorService();
    }

    private void loadData() {
        // Obtener el FormatA del DegreeWork

        FormatA formatA = coordinatorService.getFormatAByDegreeWorkId(degreeWork.getIdDegreeWork());

        if (formatA != null) {
            tituloLabel.setText(formatA.getTittle() != null ? formatA.getTittle() : "Sin título");
        }

        // Cargar y visualizar el PDF
        loadPdfPreview();
    }

    private void loadPdfPreview() {
        FormatA formatA = coordinatorService.getFormatAByDegreeWorkId(degreeWork.getIdDegreeWork());
        if (formatA == null) {
            showPdfError("No se encontró el Formato A asociado.");
            return;
        }

        String pdfUrl = formatA.getURL();
        System.out.println("por imprimir pdf con url: " + pdfUrl);
        try {
            if (pdfUrl != null && !pdfUrl.isEmpty()) {
                File pdfFile = new File(pdfUrl);
                if (pdfFile.exists()) {
                    pdfDocument = PDDocument.load(pdfFile);
                    PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

                    // Renderizar la primera página como preview
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
            boolean resultado = evaluarFormato(EnumState.APROBADO);
            closeModal(resultado);
        }
    }

    private void handleRechazar() {
        if (validateRechazo()) {
            boolean resultado = evaluarFormato(EnumState.RECHAZADO);
            closeModal(resultado);
        }
    }

    private void handleCancelar() {
        cancelarButton.getScene().getWindow().hide();
    }

    private boolean evaluarFormato(EnumState estado) {
        if (serviceFactory != null) {
            CoordinatorService serviceCoordinator = this.serviceFactory.getCoordinatorService();
            return serviceCoordinator.evaluateFormatAByDegreeWorkId(
                    degreeWork.getIdDegreeWork(),
                    comentariosTextArea.getText(),
                    estado
            );
        }
        return false;
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

    private void closeModal(boolean resultado) {
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

        // Llamar al callback con el resultado
        if (callbackResultado != null) {
            callbackResultado.apply(resultado);
        }
    }
}