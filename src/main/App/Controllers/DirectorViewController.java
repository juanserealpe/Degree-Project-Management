package Controllers;

import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DirectorViewController extends BaseController{
    public GridPane CardsContainer;
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
    @FXML
    public void initialize() {

    }
    public void initData(Session session) {
        try {
            //inicializar el menu.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();

            // Asegurar que el menú ocupe todo el espacio disponible
            SideMenuContainer.getChildren().clear();
            SideMenuContainer.getChildren().add(sideMenu);

            SideMenuController controller = loader.getController();
            //pasarle la sesion al menu
            controller.initData(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
