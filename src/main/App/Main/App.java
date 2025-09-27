package Main;

import Controllers.BaseController;
import DataBase.DbConnection;
import Dtos.UserRegisterDTO;
import Enums.EnumRole;
import Models.Session;
import Services.CookieService;
import Services.ServiceFactory;
import Utilities.WindowManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.List;

/**
 * Clase principal de la aplicación JavaFX.
 * Esta clase se encarga de inicializar la aplicación:
 * - Establece la conexión a la base de datos.
 * - Crea una única instancia de {@link ServiceFactory} para inyectar servicios en los controllers.
 * - Carga la vista de login desde el archivo FXML.
 * - Configura y muestra la ventana principal mediante {@link WindowManager}.
 *
 *  @author juanseralpe
 */
public class App extends Application {

    /**
     * Método principal de inicio de la aplicación JavaFX.
     * @param primaryStage Escenario principal donde se muestran las vistas.
     * @throws Exception Si ocurre un error al cargar el FXML o establecer la conexión.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Establecer la conexión a la base de datos
        Connection connection = DbConnection.getConnection();

        // Crear la fábrica de servicios UNA sola vez
        // Fábrica de servicios para inyección en controllers
        ServiceFactory serviceFactory = new ServiceFactory(connection);

        // Crea CookieService
        CookieService cookieService = new CookieService();
        UserRegisterDTO userRegisterDTO = cookieService.getUserRegisterDTOByCookie();

        // Verificar si existe el usuario con esa cookie, si es null, cargar el LoginView.fxml
        Scene scene;
        if(userRegisterDTO == null) {

            // Cargar la interfaz de login desde el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
            scene = new Scene(loader.load());

            // Obtener el controller de login y pasarle la fábrica de servicios
            BaseController controller = loader.getController();
            controller.setServiceFactory(serviceFactory);
        }else{
            Session.setEmail(userRegisterDTO.getAccount().getEmail());
            List<EnumRole> roles = userRegisterDTO.getAccount().getRoles();
            Session.setRoles(userRegisterDTO.getAccount().getRoles());
            //Cargar la ventana del primer rol
            String resource = getRolResource(roles.get(0));

            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            scene = new Scene(loader.load());
            BaseController controller = loader.getController();
            controller.setServiceFactory(serviceFactory);
        }


        // Configurar y mostrar la ventana principal
        primaryStage.setScene(scene);
        WindowManager.setupWindow(primaryStage, "", true, 600, 800);
        primaryStage.show();
    }

    private static String getRolResource(EnumRole rol) {
        String resource;
        switch (rol){
            case JURY -> resource =  "/views/UserViews/JuryView.fxml";
            case DIRECTOR ->  resource =  "/views/UserViews/DirectorView.fxml";
            case COORDINATOR ->   resource =  "/views/UserViews/CoordinatorView.fxml";
            case UNDERGRADUATE_STUDENT ->   resource =  "/views/UserViews/StudentView.fxml";
            default -> resource =  "/views/AuthViews/LoginView.fxml"; //No tiene sentido, volvemos a el login
        }
        return resource;
    }
}
