package Controllers;

import Interfaces.DoingSomething;
import Models.DegreeWork;
import Models.FormatA;
import Models.Session;
import Services.ServiceFactory;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

public abstract class BaseController {
    /** Fábrica de servicios para acceder a las diferentes capas de la aplicación */
    protected ServiceFactory serviceFactory;
    private  Session session;
    public void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }
    public void initData(Session session) {};

    void loadDegreeWork(List<DegreeWork> degreeWorks, GridPane CardsContainer, DoingSomething doingSomething) {
        CardsContainer.getColumnConstraints().clear();
        CardsContainer.getRowConstraints().clear();

        // Configurar columnas flexibles
        int maxCols = 2; // Se puede parametrizar para responsividad futura
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

        for (DegreeWork work : degreeWorks) {
            VBox card = createCard(work, doingSomething);

            // Asegurar que hay suficientes filas
            if (row >= CardsContainer.getRowConstraints().size()) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                rowConstraints.setPrefHeight(150);
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

    void loadFormatACards(List<FormatA> formatos, GridPane CardsContainer, DoingSomething DoingSomething) {
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
            VBox card = createCard(format, DoingSomething);

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
    private VBox createCard(FormatA formato, DoingSomething doingSomething) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        card.setPrefSize(250, 150);
        card.setMinSize(200, 120);
        card.setMaxSize(300, 200);

        Label lblTitulo = new Label("Título: " + formato.getTittle());
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblEstudiante = new Label("Estudiante: ");
        Label lblDirector = new Label("Director: ");
        Label lblFecha = new Label("Fecha: ");

        Button btnCalificar = new Button("Calificar");
        btnCalificar.setStyle("-fx-background-color: #4a6bff; -fx-text-fill: white;");
        // Pasar el objeto completo en lugar de solo el título
        btnCalificar.setOnAction(e -> doingSomething.apply(formato));

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(lblTitulo, lblEstudiante, lblDirector, lblFecha, spacer, btnCalificar);
        return card;
    }
    private VBox createCard(DegreeWork degreeWork, DoingSomething doingSomething) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        card.setPrefSize(250, 150);
        card.setMinSize(200, 120);
        card.setMaxSize(300, 200);

        Label lblTitulo = new Label("Modality: " + degreeWork.getModality());
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        Label lblEstudiante = new Label("Estudiante: ");
        Label lblDirector = new Label("Director: ");
        Label lblFecha = new Label("Fecha: ");

        Button btnCalificar = new Button("Calificar");
        btnCalificar.setStyle("-fx-background-color: #4a6bff; -fx-text-fill: white;");
        // Pasar el objeto DegreeWork completo
        btnCalificar.setOnAction(e -> doingSomething.apply(degreeWork));

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(lblTitulo, lblEstudiante, lblDirector, lblFecha, spacer, btnCalificar);
        return card;
    }

}
