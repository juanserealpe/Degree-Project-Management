package Main;

import Controllers.LoginController;
import DataBase.DbConnection;
import Models.Account;
import Services.CookieService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;

/**
 * Clase principal de la aplicación JavaFX.
 *
 * Esta clase se encarga de inicializar la aplicación:
 * - Establece la conexión a la base de datos.
 * - Crea una única instancia de {@link ServiceFactory} para inyectar servicios en los controllers.
 * - Carga la vista de login desde el archivo FXML.
 * - Configura y muestra la ventana principal mediante {@link WindowManager}.
 *
 *  @author juanseralpe
 */
public class App extends Application {

    private ServiceFactory serviceFactory; // Fábrica de servicios para inyección en controllers

    /**
     * Método principal de inicio de la aplicación JavaFX.
     *
     * @param primaryStage Escenario principal donde se muestran las vistas.
     * @throws Exception Si ocurre un error al cargar el FXML o establecer la conexión.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        CookieService cookieService = new CookieService();
        serviceFactory = new ServiceFactory(DbConnection.getConnection());

        Account result = cookieService.getAccountByCookie();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
        Parent root = loader.load();

        LoginController loginController = loader.getController();
        loginController.setServiceFactory(serviceFactory);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        WindowManager.setupWindow(primaryStage, "", true, 600, 800);
        primaryStage.show();
    }
}