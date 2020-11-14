package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginMenu implements Initializable {
    @FXML
    private Label labelRegion;
    @FXML
    private TextField textUserName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label labelUserName;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelErrorMessage;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonQuit;
    @FXML
    private Label labelComputerRegion;
    private MainWindow mainWindow;

    public void setMenuController(MainWindow menuController ) {
        mainWindow = menuController;
    }

    public void handleButtonLogin(ActionEvent actionEvent) {
        mainWindow.enableSideButtons();
    }

    public void handleButtonQuit(ActionEvent actionEvent) {
        mainWindow.disableSideButtons();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
