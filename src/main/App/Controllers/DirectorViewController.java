package Controllers;

import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class DirectorViewController extends BaseController{
    public GridPane CardsContainer;
    @FXML
    Pane SideMenuContainer;


    @FXML
    void btnShowPDF(MouseEvent event) {
        //ShowPDF();
    }
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
