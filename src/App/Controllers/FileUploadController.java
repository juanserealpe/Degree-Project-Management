package App.Controllers;

import App.Services.FileResult;
import App.Services.FileService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.FileChooser;

import java.io.File;

import javafx.stage.Stage;
import App.Models.FileUploadModel;

public class FileUploadController {

    private FileService fileService;
    FileChooser fileChooser = new FileChooser();
    private boolean fileCharged = false;
    private boolean sentFile = false;

    public void initialize() {
        fileService = new FileService();
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

        if(sentFile){
            System.out.println("Archivo ya enviado");
        }else{
            FileResult fileResult = fileService.UploadFile();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar formato A");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Formato A", "*.pdf")
            );

            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null && !sentFile) {
                this.fileCharged = true;
                String result = model.getFilePath(selectedFile);
                resultArea.setText(result);
                System.out.println(result);
                btnConfirm.getStyleClass().add("able-button-style");
                btnConfirm.getStyleClass().removeAll("dissable-button-style");
            } else {
                resultArea.setText("No se seleccionó ningún archivo.");
                this.fileCharged = false;
                btnConfirm.getStyleClass().add("dissable-button-style");
                btnConfirm.getStyleClass().removeAll("able-button-style");
            }
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