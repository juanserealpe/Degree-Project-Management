package Controllers;

import Interfaces.IAuthService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class DirectorViewController {
    @FXML
    Pane SideMenuContainer;

    private static IAuthService authService;
    private ServiceFactory serviceFactory;

    @FXML
    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
        Node sideMenu = loader.load();
        SideMenuController controller = loader.getController();
        controller.initData("Director");
        SideMenuContainer.getChildren().add(sideMenu);
    }



}
