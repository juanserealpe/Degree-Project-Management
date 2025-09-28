package Controllers;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.DegreeWork;
import Models.FormatA;
import Models.Session;
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

    @FXML
    public void initialize() {
    }
    public void initData(Session instance) {
        try {
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
        //EJEMPLO
        loadSampleData();
    };

    private void handleCalificar(Object degreeWorkObj) {
        DegreeWork degreeWork = (DegreeWork) degreeWorkObj;
        try {
            // Crear el modal de calificación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserViews/CoordinatorViews/evalueteFormatAModal.fxml"));
            Pane modalContent = loader.load();
            EvaluateFormatAController controller = loader.getController();
            controller.initData(degreeWork, this::handleCalificacionResultado);

            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);

            // Obtener el título del FormatA para la ventana
            FormatA formatA = findFormatAByDegreeWork(degreeWork);
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
    private void handleCalificacionResultado(CalificacionResultado resultado) {
        if (resultado != null) {
            // Aquí procesas el resultado de la calificación
            System.out.println("Formato calificado:");
            System.out.println("Trabajo: " + resultado.getDegreeWork().getIdDegreeWork());
            System.out.println("Estado: " + resultado.getNuevoEstado());
            System.out.println("Comentarios: " + resultado.getComentarios());

            // Actualizar el estado del FormatA en la base de datos
            resultado.getFormatA().setState(resultado.getNuevoEstado());

            // Guardar los comentarios (depende de tu modelo)
            // resultado.getFormatA().setComentarios(resultado.getComentarios());

            showAlert("Éxito", "Formato A calificado exitosamente. Estado: " + resultado.getNuevoEstado());

            // Recargar la vista o actualizar la interfaz
            // loadSampleData(); // Descomenta si quieres recargar los datos
        }
    }
    private FormatA findFormatAByDegreeWork(DegreeWork degreeWork) {
        // Implementa la lógica para buscar el FormatA asociado al DegreeWork
        // Esto depende de tu modelo de datos
        // Por ahora, retornamos un FormatA de ejemplo
        return new FormatA(new Date(), EnumState.ESPERA, EnumTypeProcess.FORMAT_A);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadSampleData() {
        List<DegreeWork> listDegreeWork = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            // Crear FormatA y asociarlo al DegreeWork
            FormatA newFormatA = new FormatA(new Date(1), EnumState.ESPERA, EnumTypeProcess.FORMAT_A);
            newFormatA.setTittle("titulo"+i);
            newFormatA.setURL("C://Users//Usuario//Desktop//1.- Cartas DnD - Por DaniDux.pdf");

            DegreeWork degreeWork = new DegreeWork();
            degreeWork.setIdDegreeWork(i);
            degreeWork.getProcesses().add(newFormatA);
            listDegreeWork.add(degreeWork);
        }

        System.out.println("Cargando " + listDegreeWork.size() + " tarjetas");
        // Cambiar a loadDegreeWork en lugar de loadFormatACards
        loadDegreeWork(listDegreeWork, CardsContainer, this::handleCalificar);

        System.out.println("Número de hijos en CardsContainer: " + CardsContainer.getChildren().size());
    }
}
