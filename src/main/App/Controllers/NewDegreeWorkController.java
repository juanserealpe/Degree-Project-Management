package Controllers;

import Builders.CreateFormatA;
import Builders.ProcessCreator;
import DataBase.DbConnection;
import Enums.EnumModality;
import Enums.EnumState;
import Interfaces.IFileService;
import Models.*;
import Models.Process;
import Repositories.CookieRepository;
import Repositories.DegreeWorkRepository;
import Services.FileService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewDegreeWorkController extends BaseController{

    private Session instance;
    Connection connection = DbConnection.getConnection();
    private final DegreeWorkRepository degreeWorkRepository = new DegreeWorkRepository(connection);
    private final CookieRepository cookieRepository = new CookieRepository();
    private final String path = System.getProperty("user.dir") + "/Secrets/ProjectManager/";
    private final IFileService fileService = new FileService();

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
    void btnCreateDW(ActionEvent event) throws SQLException, IOException {
        //Ejemplo
        //Aquí debería traer los datos de la base de datos

        //Trayendo id de estudiantes
        List<Integer> idStudents = new ArrayList<>();
        idStudents.add(degreeWorkRepository.getIdAccountByEmail(idStudent1.getText()));
        idStudents.add(degreeWorkRepository.getIdAccountByEmail(idStudent2.getText()));

        //Trayendo id de directores
        int director = 0;
        String filename = "cookie.txt";
        String content = fileService.readFile(path, filename);
        Cookie cookie = cookieRepository.getByString(content);
        director = cookie.UserId;
        int codirector = degreeWorkRepository.getIdAccountByEmail(idCodirector.getText());

        //nuevo Formato A
        ProcessCreator creator = new CreateFormatA();
        Process[] process = new Process[1];
        process[0] = creator.createProcess();
        ((FormatA) process[0]).setTittle(idTittle.getText());
        ((FormatA) process[0]).setAttempts(1);

        //Fecha de hoy
        LocalDate hoy = LocalDate.now();

        //nuevo Trabajo de Grado
        DegreeWork degreeWork = new DegreeWork(idStudents, director, codirector, List.of(process), EnumModality.INVESTIGACION, EnumState.ESPERA, Date.from(hoy.atStartOfDay(ZoneId.systemDefault()).toInstant()));

        //Aquí debería insertar el trabajo de grado en la BD
        degreeWork.setIdDegreeWork(degreeWorkRepository.insertNewDegreeWork(degreeWork));
        degreeWorkRepository.insertFormatA(degreeWorkRepository.insertProcess(degreeWork), (FormatA)process[0]);
        degreeWorkRepository.insertDegreeWork_Students(degreeWork.getIdDegreeWork(), idStudents.get(0));
        if(!(idStudents.get(1) == null || idStudents.get(1) == 0)){
            degreeWorkRepository.insertDegreeWork_Students(degreeWork.getIdDegreeWork(), idStudents.get(1));
        }
        //HELP <- No sabe programar

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
