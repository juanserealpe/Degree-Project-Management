package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class DashboardController {

    @FXML
    private void handleLogout(ActionEvent event) throws Exception {
        // Obtener el Stage actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Cargar LoginVIew.fxml de nuevo
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginVIew.fxml"));
        Scene loginScene = new Scene(loader.load());

        stage.setScene(loginScene);
        stage.setTitle("Login");
    }
}
