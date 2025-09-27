package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DirectorViewController {
    @FXML
    Pane SideMenuContainer;
    @FXML
    public void initialize() {
        try {
            //inicializar el menu.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();
            SideMenuController controller = loader.getController();
            controller.initData();
            SideMenuContainer.getChildren().add(sideMenu);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
