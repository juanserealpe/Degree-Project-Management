package Controllers;

import Services.ServiceFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class DirectorViewController extends BaseController{

    @FXML
    Pane SideMenuContainer;
    SideMenuController sideMenuController;
    @FXML
    public void initialize() {
        try {
            //inicializar el menu.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();
            sideMenuController = loader.getController();
            sideMenuController.initData();
            SideMenuContainer.getChildren().add(sideMenu);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setServiceFactory(ServiceFactory serviceFactory) {
        super.setServiceFactory(serviceFactory);
        sideMenuController.setServiceFactory(serviceFactory);
    }
}
