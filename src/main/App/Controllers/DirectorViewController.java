package Controllers;

import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DirectorViewController extends BaseController{
    @FXML
    Pane SideMenuContainer;
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
