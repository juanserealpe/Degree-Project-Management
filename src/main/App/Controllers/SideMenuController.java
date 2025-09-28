package Controllers;

import Enums.EnumMenuOption;
import Enums.EnumRole;
import Models.Session;
import Utilities.MenuOption;
import Utilities.WindowManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SideMenuController extends BaseController {
    @FXML
    private VBox rolButtons;

    private Session instance;

    private final Map<String, List<MenuOption>> menuItems = new LinkedHashMap<>() {{
        put(String.valueOf(EnumRole.UNDERGRADUATE_STUDENT), List.of(new MenuOption(EnumMenuOption.MI_PROYECTO,"Mi Proyecto")));
        put(String.valueOf(EnumRole.DIRECTOR), List.of(new MenuOption(EnumMenuOption.CREAR_FORMATO_A,"Crear Formato A"),
                new MenuOption(EnumMenuOption.VER_PROYECTOS,"ver Proyectos")));
        put(String.valueOf(EnumRole.COORDINATOR), List.of(new MenuOption(EnumMenuOption.CALIFICAR_FORMATOS_A,"Calificar formatos A")));
    }};

    @FXML
    public void initialize() {
    }
    public void initData(Session instance) {
        this.instance = instance;
        rolButtons.getChildren().clear();
        rolButtons.getStyleClass().add("side-menu");

        // Obtener los roles de la sesión actual
        Set<String> sessionRoles = instance.getRoles().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        // Filtrar los menuItems para mostrar solo los roles de la sesión
        menuItems.entrySet().stream()
                .filter(entry -> sessionRoles.contains(entry.getKey()))
                .forEach(entry -> {
                    String rol = entry.getKey();
                    List<MenuOption> menuOptions = entry.getValue();

                    VBox rolBox = new VBox();
                    rolBox.getStyleClass().add("rol-container");

                    // Botón del rol
                    Button rolButton = createButton(rol, null, false);
                    rolBox.getChildren().add(rolButton);

                    // Submenús
                    VBox subMenu = new VBox();
                    subMenu.getStyleClass().add("sub-menu");
                    subMenu.setVisible(false);
                    subMenu.setManaged(false);
                    for(MenuOption menuOption : menuOptions) {
                        subMenu.getChildren().add(createButton(menuOption.getDescripcion(), menuOption, true));
                    }
                    rolBox.getChildren().add(subMenu);

                    // Animación al hacer click en el rol
                    rolButton.setOnAction(e -> toggleSubMenu(subMenu, rolButton));

                    rolButtons.getChildren().add(rolBox);
                });
    }

    private void toggleSubMenu(VBox subMenu, Button rolButton) {
        boolean willShow = !subMenu.isVisible();

        if (willShow) {
            // Mostrar con animación
            subMenu.setVisible(true);
            subMenu.setManaged(true);
            playExpandAnimation(subMenu);
            rolButton.setStyle("-fx-background-color: #3a5bef; -fx-text-fill: white;");
        } else {
            // Ocultar con animación
            playCollapseAnimation(subMenu);
            rolButton.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-text-fill: #4a6bff;");
        }
    }

    private void playExpandAnimation(VBox subMenu) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), subMenu);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(200), subMenu);
        translateTransition.setFromY(-10);
        translateTransition.setToY(0);

        fadeTransition.play();
        translateTransition.play();
    }

    private void playCollapseAnimation(VBox subMenu) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(150), subMenu);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(150), subMenu);
        translateTransition.setFromY(0);
        translateTransition.setToY(-10);
        translateTransition.setDuration(Duration.millis(200));
        fadeTransition.setOnFinished(e -> {
            subMenu.setVisible(false);
            subMenu.setManaged(false);
        });

        fadeTransition.play();
        translateTransition.play();
    }
    private Button createButton(String nameOption, MenuOption menuOption, boolean isSubItem) {
        Button button = new Button(nameOption);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(35);

        if (isSubItem) {
            button.getStyleClass().add("btn_subMenuElement");
            button.setOnAction(e -> handleAction(e, menuOption));
        } else {
            button.getStyleClass().add("btn_MenuElement");
        }

        return button;
    }

    private void handleAction(ActionEvent event, MenuOption menuOption) {
        System.out.println("Click en: " + menuOption.getDescripcion() + " (rol)");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String  fxml;
        switch (menuOption.getId()) {
            case MI_PROYECTO ->  fxml = "/views/UserViews/StudentView.fxml";
            case VER_PROYECTOS ->   fxml = "/views/UserViews/DirectorViews/DirectorView.fxml";
            case CREAR_FORMATO_A -> fxml = "/views/FormViews/NewDegreeWork.fxml";
            case CALIFICAR_FORMATOS_A -> fxml = "/views/UserViews/CoordinatorViews/CoordinatorView.fxml";
            default -> fxml = null;
        }
        try {
            BaseController controller = WindowManager.changeScene(stage,fxml, menuOption.getDescripcion());
            controller.setServiceFactory(serviceFactory);
            controller.initData(instance);

        } catch (IOException e) {
            System.err.println("Error al cargar el fichero: " + e.getMessage());
        }
    }

    @FXML
    private void handleCloseSession(ActionEvent event) throws IOException {
        System.out.println("Closing session");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AuthViews/LoginView.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        BaseController controller = WindowManager.changeScene(stage,"/views/AuthViews/LoginView.fxml","");
        controller.setServiceFactory(serviceFactory);
    }
}