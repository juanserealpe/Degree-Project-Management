package Main;

import Utilities.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/AuthViews/LoginView.fxml")
        );
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        WindowManager.setupWindow(primaryStage, "", true, 600, 800);
        primaryStage.show();
    }
}


