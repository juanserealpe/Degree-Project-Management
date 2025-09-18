package App.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class StudentViewController {

    @FXML
    private VBox panelMenu;

    @FXML
    private Button btnCerrarSesion;

    @FXML
    private Label jLabelEmail;

    @FXML
    private VBox panelRight;

    @FXML
    private TableView<?> tblProcesos;

    @FXML
    private Button btnCrearNuevoProceso;

    @FXML
    public void initialize() {

    }

    @FXML
    private void handleCerrarSesion() {
        System.out.println("Cerrando sesión...");
    }

    @FXML
    private void handleCrearNuevoProceso() {
        System.out.println("Creando nuevo proceso...");
    }
}