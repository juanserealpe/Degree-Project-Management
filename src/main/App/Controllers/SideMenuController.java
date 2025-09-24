package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
/*
* FXMLLoader loader = new FXMLLoader(getClass().getResource("SideMenu.fxml"));
Parent root = loader.load();
SideMenuController controller = loader.getController();
controller.initData("Director"); // ejemplo
*
*
* FXMLLoader loader = new FXMLLoader(getClass().getResource("SideMenu.fxml"));
Node sideMenu = loader.load();
SideMenuController controller = loader.getController();
controller.initData("Student");

borderPane.setLeft(sideMenu);
* */


public class SideMenuController {
    @FXML
    private VBox rolButtons;

    String[][] menuItems = new String[][] {
            {"Student"},
            {"Director", "Crear Formato A", "Ver proyectos que dirigo"},
            {"Coordinador", "Calificar formatos A", "opcionCoor2"}
    };

    @FXML
    public void initialize() {
    }

    public void initData(String rolActual) {
        // inicializar submenu dinamicos segun roles
        for (int i = 0; i < menuItems.length ; i++) {
            //si estamos en el rol actual, desplegar sub menu, si no otros roles como boton.
            if(menuItems[i][0].equals(rolActual)) {
                addsubMenu(menuItems[i][0], i);
            }else{
                rolButtons.getChildren().add(addSubElement(menuItems[i][0], i, 0,false));
            }

        }
    }

    private void addsubMenu(String rol, int index) {
        int len = menuItems[index].length;
        //crea elemento del submenu
        VBox subMenu = new VBox();

        //agregar sub elemento del submenu
        for (int i = 0; i < menuItems[index].length; i++) {
            if(i==0)
                subMenu.getChildren().add(addSubElement(menuItems[index][i], index, i,false));
            else
                subMenu.getChildren().add(addSubElement(menuItems[index][i], index, i,true));
        }

        //agregar subMenu al menu
        rolButtons.getChildren().add(subMenu);
    }
    private Button addSubElement(String nameOption, int index, int subIndex, boolean isSubItem) {
        Button button = new Button();
        button.setText(nameOption);
        button.setMaxWidth(Double.MAX_VALUE);
        if(isSubItem)
            button.getStyleClass().add("btn_subMenuElement");
        else
            button.getStyleClass().add("btn_MenuElement");
        button.setOnAction(e -> {
            System.out.println("click en: "+nameOption+" ["+index+"]["+subIndex+"]");
            //logica segun click boton
            // Cerrar vista actual y abrir nueva lista segun rol
        });
        return button;
    }

}
