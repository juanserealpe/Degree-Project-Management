package Controllers;

import Models.Session;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {
    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    protected ServiceFactory serviceFactory;
    public void setServiceFactory(ServiceFactory serviceFactory) {
        if(this.serviceFactory != null) return;
        this.serviceFactory = serviceFactory;
    }
    public void LogOut(ActionEvent event){
        Session.logOut();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            BaseController baseController = WindowManager.changeScene(stage, "/views/AuthViews/LoginView.fxml", "Login");
            baseController.setServiceFactory(serviceFactory);
            serviceFactory.getCookieService().ResetCookie();
        } catch (IOException e) {
            System.out.println("Error to change scene " + e);
        }

    }
}
