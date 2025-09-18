package App.Views;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;


public class FileUploadView extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        /*
        VBox root = new VBox(15);

        // Aquí creamos el campo de subida, solo permitiendo PDF
        FileUploadPanel pdfUploader = new FileUploadPanel(stage, "*.pdf");

        root.getChildren().add(pdfUploader);

        stage.setScene(new Scene(root, 400, 200));
        stage.setTitle("Ejemplo subida de archivo");
         */

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/Views/FileUpload.fxml"));
        GridPane load = loader.load();

        Scene scene = new Scene(load);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
