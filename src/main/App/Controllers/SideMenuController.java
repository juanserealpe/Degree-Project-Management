package Controllers;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class SideMenuController {
    @FXML
    private VBox rolButtons;

    private final Map<String, List<String>> menuItems = new LinkedHashMap<>() {{
        put("Student", List.of("Mi Proyecto"));
        put("Director", List.of("Crear Formato A", "ver Proyectos"));
        put("Coordinador", List.of("Calificar formatos A"));
    }};

    @FXML
    public void initialize() {
        initData();
    }

    public void initData() {
        rolButtons.getChildren().clear();
        rolButtons.getStyleClass().add("side-menu");

        menuItems.forEach((rol, opciones) -> {
            VBox rolBox = new VBox();
            rolBox.getStyleClass().add("rol-container");

            // Botón del rol
            Button rolButton = createButton(rol, false);
            rolBox.getChildren().add(rolButton);

            // Submenús
            VBox subMenu = new VBox();
            subMenu.getStyleClass().add("sub-menu");
            subMenu.setVisible(false);
            subMenu.setManaged(false);

            for (String opcion : opciones) {
                subMenu.getChildren().add(createButton(opcion, true));
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

    private Button createButton(String nameOption, boolean isSubItem) {
        Button button = new Button(nameOption);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setMinHeight(35);

        if (isSubItem) {
            button.getStyleClass().add("btn_subMenuElement");
        } else {
            button.getStyleClass().add("btn_MenuElement");
        }

        button.setOnAction(e -> handleAction(nameOption, isSubItem));
        return button;
    }

    private void handleAction(String nameOption, boolean isSubItem) {
        System.out.println("Click en: " + nameOption + (isSubItem ? " (submenu)" : " (rol)"));
        // Lógica de navegación aquí
    }

    @FXML
    private void handleCloseSession(ActionEvent event) throws IOException {
        System.out.println("Closing session");
        // Lógica para cerrar sesión
    }
}