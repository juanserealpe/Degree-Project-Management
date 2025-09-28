package Controllers;

import Models.FormatA;
import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class DirectorViewController extends BaseController{
    public GridPane CardsContainer;
    @FXML
    Pane SideMenuContainer;


    @FXML
    void btnShowPDF(MouseEvent event) {
        //ShowPDF();
    }
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
        /*List<DegreeWork> degreeWorkList =  serviceFactory.getDirectorService().getDegreeWorksById(session.getId());
        if(!degreeWorkList.isEmpty()) {
            loadDegreeWork(degreeWorkList, CardsContainer, this::viewMore);
        }*/

        List<FormatA> formatAList = serviceFactory.getDirectorService().getFormatAByDirectorId(session.getId());
        if(formatAList.isEmpty())return;
        loadFormatACards(formatAList, CardsContainer, this::viewMore);
    }
    private void viewMore(Object _formatA){
        String degreeWork = (String) _formatA;
        //TODO: modified this
    }

}
