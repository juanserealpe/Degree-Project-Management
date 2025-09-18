package App.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginViewController {


    public Label registerLabel;
    public PasswordField passwordField;
    public VBox passwordTooltip;

    public void initialize() {
        registerLabel.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/Views/RegisterView.fxml"));
                Parent root = loader.load();

                Stage registerStage = new Stage();
                registerStage.setTitle("Registro de Usuario");
                registerStage.setScene(new Scene(root));
                registerStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public Button loginButton;
}
