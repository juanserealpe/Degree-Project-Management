package App.Controllers;

import App.Exceptions.FileException;
import App.Services.FileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

import javafx.stage.Stage;
import App.Models.FileUploadModel;

import javax.swing.*;

public class FileUploadController {
    private FileService fileService;
    FileChooser fileChooser = new FileChooser();
    private boolean fileCharged = false;
    private boolean sentFile = false;

    public void initialize() {
        fileService = new FileService();

        resultArea.setOnDragOver(event -> {
            if (event.getGestureSource() != resultArea && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(javafx.scene.input.TransferMode.COPY);
            }
            event.consume();
        });

        resultArea.setOnDragDropped(event -> {
            javafx.scene.input.Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasFiles()) {
                File droppedFile = db.getFiles().get(0);

                if (droppedFile.getName().toLowerCase().endsWith(".pdf")) {
                    try {
                        this.fileCharged = true;
                        String path = fileService.uploadFile(model.getFilePath(droppedFile));
                        resultArea.setText(path);
                        btnConfirm.getStyleClass().add("able-button-style");
                        btnConfirm.getStyleClass().removeAll("dissable-button-style");
                        success = true;
                    } catch (FileException e) {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        resultArea.setText("Error: " + e.getMessage());
                    }
                } else {
                    resultArea.setText("Solo se aceptan archivos PDF.");
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });

    }


    @FXML
    private Label lblMessage;

    @FXML
    private Button btnConfirm;

    //added
    @FXML
    private Label resultArea;

    private final FileUploadModel model = new FileUploadModel();
    //end added

    @FXML
    void click(ActionEvent event) {
        System.out.println("Botón funcionando!");

        if (sentFile) {
            System.out.println("Archivo ya enviado");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar formato A");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Formato A", "*.pdf")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            try {
                this.fileCharged = true;
                String path = fileService.uploadFile(model.getFilePath(selectedFile));
                resultArea.setText(path);
                btnConfirm.getStyleClass().add("able-button-style");
                btnConfirm.getStyleClass().removeAll("dissable-button-style");
                sentFile = true;
            } catch (FileException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                resultArea.setText("Error: " + e.getMessage());
                this.fileCharged = false;
                btnConfirm.getStyleClass().add("dissable-button-style");
                btnConfirm.getStyleClass().removeAll("able-button-style");
            }
        } else {
            resultArea.setText("No se seleccionó ningún archivo.");
            this.fileCharged = false;
            btnConfirm.getStyleClass().add("dissable-button-style");
            btnConfirm.getStyleClass().removeAll("able-button-style");
        }
    }

    @FXML
    void confirm(ActionEvent event) {
        System.out.println("Confirmar funcionando!");
        if (fileCharged) {
            lblMessage.setText("Archivo enviado exitosamente!");
            lblMessage.getStyleClass().add("confirmation-label-style");
            lblMessage.getStyleClass().removeAll("error-label-style");
            lblMessage.setVisible(true);
            this.sentFile = true;
        }else{
            lblMessage.setText("Debe seleccionar un archivo");
            lblMessage.getStyleClass().add("error-label-style");
            lblMessage.getStyleClass().removeAll("confirmation-label-style");
            lblMessage.setVisible(true);
        }
    }

}