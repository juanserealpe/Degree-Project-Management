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
import java.util.stream.Collectors;

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
        int maxCols = 2;
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

            if (row >= CardsContainer.getRowConstraints().size()) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                rowConstraints.setPrefHeight(180); // Aumentar altura para más información
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

        int maxCols = 2;
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

            if (row >= CardsContainer.getRowConstraints().size()) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setVgrow(javafx.scene.layout.Priority.SOMETIMES);
                rowConstraints.setPrefHeight(180);
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
        VBox card = new VBox(8); // Reducir espacio entre elementos
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        card.setPrefSize(280, 180); // Aumentar tamaño para más información
        card.setMinSize(250, 160);
        card.setMaxSize(320, 200);

        // Título del FormatoA
        Label lblTitulo = new Label("Título: " + (formato.getTittle() != null ? formato.getTittle() : "Sin título"));
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-wrap-text: true;");
        lblTitulo.setMaxWidth(250);

        // Intentos del FormatoA
        Label lblIntentos = new Label("Intentos: " + formato.getAttempts());
        lblIntentos.setStyle("-fx-font-size: 12px;");

        // Información adicional que podría ser útil
        Label lblObservacion = new Label("Obs: " +
                (formato.getObservation() != null && !formato.getObservation().isEmpty() ?
                        formato.getObservation().substring(0, Math.min(30, formato.getObservation().length())) + "..." :
                        "Sin observaciones"));
        lblObservacion.setStyle("-fx-font-size: 11px; -fx-text-fill: #666; -fx-wrap-text: true;");
        lblObservacion.setMaxWidth(250);

        Button btnCalificar = new Button("Calificar");
        btnCalificar.setStyle("-fx-background-color: #4a6bff; -fx-text-fill: white; -fx-font-size: 12px;");
        btnCalificar.setOnAction(e -> doingSomething.apply(formato));

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(lblTitulo, lblIntentos, lblObservacion, spacer, btnCalificar);
        return card;
    }

    private VBox createCard(DegreeWork degreeWork, DoingSomething doingSomething) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ccc; -fx-border-radius: 8; -fx-background-radius: 8; -fx-padding: 15;");
        card.setPrefSize(280, 180);
        card.setMinSize(250, 160);
        card.setMaxSize(320, 200);

        // Obtener el FormatA asociado al DegreeWork
        FormatA formatA = getFormatAFromDegreeWork(degreeWork);

        // Título del FormatoA
        String titulo = formatA != null && formatA.getTittle() != null ? formatA.getTittle() : "Sin título";
        Label lblTitulo = new Label("Título: " + titulo);
        lblTitulo.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-wrap-text: true;");
        lblTitulo.setMaxWidth(250);

        // IDs de Estudiantes
        String estudiantesText = "Estudiantes: " + getStudentIdsText(degreeWork.getStudentIds());
        Label lblEstudiantes = new Label(estudiantesText);
        lblEstudiantes.setStyle("-fx-font-size: 12px; -fx-wrap-text: true;");
        lblEstudiantes.setMaxWidth(250);

        // Director y Codirector
        String directoresText = "Director: " + degreeWork.getDirectorId() +
                (degreeWork.getCodirectorId() != 0 ?
                        " | Codirector: " + degreeWork.getCodirectorId() : "");
        Label lblDirectores = new Label(directoresText);
        lblDirectores.setStyle("-fx-font-size: 12px;");

        // Modalidad
        Label lblModalidad = new Label("Modalidad: " +
                (degreeWork.getModality() != null ? degreeWork.getModality().toString() : "No definida"));
        lblModalidad.setStyle("-fx-font-size: 12px;");

        // Intentos del FormatoA
        Label lblIntentos = new Label("Intentos: " + formatA.getAttempts());
        lblIntentos.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #d35400;");

        // Estado del DegreeWork
        Label lblEstado = new Label("Estado: " +
                (degreeWork.getState() != null ? degreeWork.getState().toString() : "No definido"));
        lblEstado.setStyle("-fx-font-size: 11px; -fx-text-fill: #2c3e50;");

        Button btnCalificar = new Button("Calificar");
        btnCalificar.setStyle("-fx-background-color: #4a6bff; -fx-text-fill: white; -fx-font-size: 12px;");
        btnCalificar.setOnAction(e -> doingSomething.apply(degreeWork));

        Pane spacer = new Pane();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // Construir la tarjeta con los elementos apropiados
        if (formatA != null) {
            card.getChildren().add(lblTitulo);
            card.getChildren().add(lblEstudiantes);
            card.getChildren().add(lblDirectores);
            card.getChildren().add(lblModalidad);
            card.getChildren().add(lblIntentos);
            card.getChildren().add(spacer);
            card.getChildren().add(btnCalificar);
        }
        return card;
    }

    // Método auxiliar para obtener el FormatA de un DegreeWork
    private FormatA getFormatAFromDegreeWork(DegreeWork degreeWork) {
        if (degreeWork != null && degreeWork.getProcesses() != null) {
            for (Object process : degreeWork.getProcesses()) {
                if (process instanceof FormatA) {
                    return (FormatA) process;
                }
            }
        }
        return null;
    }

    // Método auxiliar para formatear los IDs de estudiantes
    private String getStudentIdsText(List<Integer> studentIds) {
        if (studentIds == null || studentIds.isEmpty()) {
            return "No asignados";
        }
        return studentIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}