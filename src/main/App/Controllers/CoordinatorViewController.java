package Controllers;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.FormatA;
import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CoordinatorViewController extends BaseController{
    @FXML
    Pane SideMenuContainer;

    @FXML
    GridPane CardsContainer;

    @FXML
    public void initialize() {
    }
    public void initData(Session session) {
        try {
            //inicializar el menu.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView/SideMenu.fxml"));
            Node sideMenu = loader.load();

            // Asegurar que el menú ocupe todo el espacio disponible
            SideMenuContainer.getChildren().clear();
            SideMenuContainer.getChildren().add(sideMenu);

            SideMenuController controller = loader.getController();
            //pasarle la sesion al menu
            controller.initData(session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //EJEMPLO
        loadSampleData();
    };




    private void handleCalificar(Object titulo) {
        if(! (titulo instanceof String)) return; //No es un titulo
        String title = (String) titulo;
        System.out.println("Calificar formato con título: " + titulo);
        // Aquí puedes abrir un modal, cambiar de vista, etc.
    }

    private void loadSampleData() {
        List<FormatA> list = new ArrayList<>();


        for (int i = 0; i < 6; i++) {
            FormatA newFormatA = new FormatA(new Date(1), EnumState.ESPERA, EnumTypeProcess.FORMAT_A);
            newFormatA.setTittle("titulo"+i);
            list.add(newFormatA);
        }

        System.out.println("Cargando " + list.size() + " tarjetas");
        loadFormatACards(list, CardsContainer, this::handleCalificar);

        // Verificar que las tarjetas se hayan añadido
        System.out.println("Número de hijos en CardsContainer: " + CardsContainer.getChildren().size());
    }

}
