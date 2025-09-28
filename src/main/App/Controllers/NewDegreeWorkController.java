package Controllers;

import Builders.CreateFormatA;
import Builders.ProcessCreator;
import DataBase.DbConnection;
import Enums.EnumModality;
import Enums.EnumState;
import Models.DegreeWork;
import Models.Session;
import Models.Process;
import Models.FormatA;
import Utilities.WindowManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewDegreeWorkController extends BaseController{

    private Session instance;
    Connection connection = DbConnection.getConnection();

    @FXML
    Pane SideMenuContainer;

    @FXML
    private TextField idCodirector;

    @FXML
    private Label idCodirectorError;

    @FXML
    private Button idConfirmBtn;

    @FXML
    private TextField idStudent1;

    @FXML
    private Label idStudent1Error;

    @FXML
    private TextField idStudent2;

    @FXML
    private Label idStudent2Error;

    @FXML
    private TextField idTittle;

    @FXML
    private Label idTittleError;

    public NewDegreeWorkController() throws SQLException {
    }

    @FXML
    public void initialize() {
    }
    public void initData(Session instance) {
        try {
            //inicializar el menu.
            this.instance = instance;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();
            SideMenuContainer.getChildren().clear();
            SideMenuContainer.getChildren().add(sideMenu);
            SideMenuController controller = loader.getController();
            controller.initData(this.instance);
            controller.setServiceFactory(this.serviceFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCreateDW(ActionEvent event) {
        //Ejemplo
        //Aquí debería traer los datos de la base de datos
        List<Integer> idStudents = new ArrayList<>();
        idStudents.add(1);
        idStudents.add(2);
        ProcessCreator creator = new CreateFormatA();
        Process[] process = new Process[1];
        process[0] = creator.createProcess();
        ((FormatA) process[0]).setTittle(idTittle.getText());
        ((FormatA) process[0]).setAttempts(1);
        LocalDate hoy = LocalDate.now();
        DegreeWork degreeWork = new DegreeWork(idStudents, 101, 102, List.of(process), EnumModality.INVESTIGACION, EnumState.INACTIVO,  Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        //Aquí debería insertar el trabajo de grado en la BD
        //HELP

        //Redirige al usuario a La vista del director
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String  fxml = "/views/UserViews/DirectorViews/DirectorView.fxml";
        try {
            BaseController controller = WindowManager.changeScene(stage,fxml, "ver Proyectos");
            controller.setServiceFactory(serviceFactory);
            controller.initData(this.instance);

        } catch (IOException e) {
            System.err.println("Error al cargar el fichero: " + e.getMessage());
        }
    }

}
