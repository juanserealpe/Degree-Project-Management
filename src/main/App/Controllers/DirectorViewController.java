package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DirectorViewController extends BaseController{

    @FXML
    Pane SideMenuContainer;
    @FXML
    public void initialize() {
        try {
            System.out.println("Inicializando DirectorViewController...");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();
            SideMenuController controller = loader.getController();
            controller.initData("Coordinador");
            SideMenuContainer.getChildren().add(sideMenu);
            System.out.println("SideMenu cargado correctamente");
        } catch (Exception e) {
            e.printStackTrace();  // Esto imprimirá todo el stack trace real
        }
    }

}
