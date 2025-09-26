package Controllers;

import Models.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DirectorViewController extends BaseController {
    @FXML
    public Label jLabelEmail;
    public void initialize() {
        jLabelEmail.setText(Session.getEmail());
    }

}
