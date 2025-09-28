package Controllers;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.DegreeWork;
import Models.FormatA;
import Models.Session;
import Services.CoordinatorService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;



public class CoordinatorViewController extends BaseController{
    @FXML
    Pane SideMenuContainer;

    @FXML
    GridPane CardsContainer;
    CoordinatorService serviceCoordinator;

    @FXML
    public void initialize() {
    }
    public void initData(Session instance) {
        try {
            serviceCoordinator = this.serviceFactory.getCoordinatorService();
            //inicializar el menu.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();
            SideMenuContainer.getChildren().clear();
            SideMenuContainer.getChildren().add(sideMenu);
            SideMenuController controller = loader.getController();
            controller.initData(instance);
            controller.setServiceFactory(this.serviceFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadData();
    };

    private void handleCalificar(Object degreeWorkObj) {
        DegreeWork degreeWork = (DegreeWork) degreeWorkObj;
        try {
            // Crear el modal de calificación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserViews/CoordinatorViews/evalueteFormatAModal.fxml"));
            Pane modalContent = loader.load();
            EvaluateFormatAController controller = loader.getController();
            controller.initData(degreeWork, this::handleCalificacionResultado);
            setServiceFactory(this.serviceFactory);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // Obtener el título del FormatA para la ventana

            FormatA formatA = serviceCoordinator.getFormatAByDegreeWorkId(degreeWork.getIdDegreeWork());

            String titulo = formatA != null ? formatA.getTittle() : "Sin título";
            modalStage.setTitle("Calificar Formato A - " + titulo);

            modalStage.setScene(new Scene(modalContent));
            modalStage.setResizable(false);

            // Mostrar el modal y esperar
            modalStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "No se pudo abrir el modal de calificación: " + e.getMessage());
        }
    }
    private void handleCalificacionResultado(boolean exito) {
        if (exito) {
            showAlert("Éxito", "Formato A calificado exitosamente.");
            loadData();
        } else {
            showAlert("Error", "No se pudo calificar el Formato A. Intente nuevamente.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private  void loadData() {
        List<DegreeWork> listDegreeWork = serviceCoordinator.getPendingDegreeWorks();
        loadDegreeWork(listDegreeWork, CardsContainer, this::handleCalificar);
    }
}