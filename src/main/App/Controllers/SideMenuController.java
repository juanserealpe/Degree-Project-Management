package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.*;

public class SideMenuController {
    @FXML
    private VBox rolButtons;

    // Estructura: rol -> lista de opciones
    private final Map<String, List<String>> menuItems = new LinkedHashMap<>() {{
        put("Student", List.of("Mis Cursos", "Ver Notas"));
        put("Director", List.of("Crear Formato A", "Ver proyectos que dirijo"));
        put("Coordinador", List.of("Calificar formatos A", "OpciónCoor2"));
    }};

    @FXML
    public void initialize() {
        initData();
    }

    public void initData() {
        rolButtons.getChildren().clear();

        //no menuItems, segun lista de sesion.
        menuItems.forEach((rol, opciones) -> {
            VBox rolBox = new VBox(); // Contenedor de rol + submenú

            // Botón del rol
            Button rolButton = createButton(rol, false);
            rolBox.getChildren().add(rolButton);

            // Submenús
            VBox subMenu = new VBox();
            subMenu.setVisible(false); // inicialmente oculto
            subMenu.setManaged(false); // no ocupa espacio cuando está oculto

            for (String opcion : opciones) {
                subMenu.getChildren().add(createButton(opcion, true));
            }

            rolBox.getChildren().add(subMenu);

            // Toggle al hacer click en el rol
            rolButton.setOnAction(e -> {
                boolean visible = subMenu.isVisible();
                subMenu.setVisible(!visible);
                subMenu.setManaged(!visible);
            });

            rolButtons.getChildren().add(rolBox);
        });
    }

    private Button createButton(String nameOption, boolean isSubItem) {
        Button button = new Button(nameOption);
        button.setMaxWidth(Double.MAX_VALUE);

        if (isSubItem) {
            button.getStyleClass().add("btn_subMenuElement");
        } else {
            button.getStyleClass().add("btn_MenuElement");
        }

        button.setOnAction(e -> handleAction(nameOption, isSubItem));
        return button;
    }

    private void handleAction(String nameOption, boolean isSubItem) {
        System.out.println("click en: " + nameOption + (isSubItem ? " (submenu)" : " (rol)"));
        // Aquí va la lógica de navegación o acciones
    }
    @FXML
    private void handleCloseSession(ActionEvent event) throws IOException {
        System.out.println("Closing session");
    }
}
