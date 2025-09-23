package Main;

import DataBase.DbConnection;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Connection connection = DbConnection.getConnection();
        ServiceFactory factory = new ServiceFactory(connection);
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/AuthViews/LoginView.fxml")
        );
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        WindowManager.setupWindow(primaryStage, "", true, 600, 800);
        primaryStage.show();
    }
}


