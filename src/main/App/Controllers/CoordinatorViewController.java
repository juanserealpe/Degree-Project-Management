package Controllers;

import Enums.EnumState;
import Enums.EnumTypeProcess;
import Models.FormatA;
import Models.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.IOException;
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

    private void loadFormatACards(List<FormatA> formatos) {
        CardsContainer.getColumnConstraints().clear();
        CardsContainer.getRowConstraints().clear();

        // Configurar columnas flexibles
        int maxCols = 2; //toca ver como hacerlo responsivo.
        for (int i = 0; i < maxCols; i++) {
            ColumnConstraints colConstraints = new ColumnConstraints();
            colConstraints.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
            colConstraints.setFillWidth(true);
            CardsContainer.getColumnConstraints().add(colConstraints);
        }

        CardsContainer.setHgap(50);
        CardsContainer.setVgap(50);
        CardsContainer.setPadding(new javafx.geometry.Insets(20));

        int col = 0;
        int row = 0;

        for (FormatA format : formatos) {
            VBox card = createCard(format);

            // Asegurar que hay suficientes filas
            if (row >= CardsContainer.getRowConstraints().size()) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                rowConstraints.setPrefHeight(150); // Altura preferida para las filas
                CardsContainer.getRowConstraints().add(rowConstraints);
            }

            CardsContainer.add(card, col, row);

            col++;
            if (col == maxCols) {
                col = 0;
                row++;
            }
        }
    }
    private VBox createCard(FormatA formato) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        card.setPrefSize(250, 150);
        card.setMinSize(200, 120);
        card.setMaxSize(300, 200);

        // Permitir que la tarjeta crezca pero mantenga proporciones
        card.setFillWidth(true);

        Label lblTitulo = new Label("Título: " + formato.getTittle());
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblEstudiante = new Label("Estudiante: ");
        Label lblDirector = new Label("Director: ");
        Label lblFecha = new Label("Fecha: ");

        Button btnCalificar = new Button("Calificar");
        btnCalificar.setStyle("-fx-background-color: #4a6bff; -fx-text-fill: white;");
        btnCalificar.setOnAction(e -> handleCalificar(formato.getTittle()));

        // Espaciador para empujar el botón hacia abajo
        Pane spacer = new Pane();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        card.getChildren().addAll(lblTitulo, lblEstudiante, lblDirector, lblFecha, spacer, btnCalificar);

        return card;
    }


    private void handleCalificar(String titulo) {
        System.out.println("Calificar formato con título: " + titulo);
        // Aquí puedes abrir un modal, cambiar de vista, etc.
    }

    private void loadSampleData() {
        List<FormatA> list = new ArrayList<>();
        FormatA newFormatA = new FormatA(new Date(1), EnumState.ESPERA, EnumTypeProcess.FORMAT_A);
        newFormatA.setTittle("titulo1");

        for (int i = 0; i < 6; i++) {
            list.add(newFormatA);
        }

        System.out.println("Cargando " + list.size() + " tarjetas");
        loadFormatACards(list);

        // Verificar que las tarjetas se hayan añadido
        System.out.println("Número de hijos en CardsContainer: " + CardsContainer.getChildren().size());
    }

}
