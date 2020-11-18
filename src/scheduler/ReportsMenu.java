package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsMenu implements ChildPaneController, Initializable {
    @FXML
    private Label labelUserName;
    @FXML
    private Button buttonAppointmentTotals;
    @FXML
    private Button buttonContactSchedule;
    @FXML
    private Button buttonDivisionReport;
    @FXML
    private TextArea textReportWindow;

    private MainWindow mainWindow;
    private User currentUser;

    @Override
    public void setMenuController(MainWindow menuController) {
        mainWindow = menuController;
    }

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
        currentUser = user;
        labelUserName.setText(currentUser.getUserName());
    }

    public void handleButtonAppointmentTotals(ActionEvent actionEvent) {
        resetButtons();
        buttonAppointmentTotals.setStyle("-fx-background-color: #3BAD5B");
    }

    public void handleButtonContactSchedule(ActionEvent actionEvent) {
        resetButtons();
        buttonContactSchedule.setStyle("-fx-background-color: #3BAD5B");
    }

    public void handleButtonDivisionReport(ActionEvent actionEvent) {
        resetButtons();
        buttonDivisionReport.setStyle("-fx-background-color: #3BAD5B");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetButtons();

    }

    private void resetButtons() {
        buttonAppointmentTotals.setStyle("-fx-background-color: #789455");
        buttonContactSchedule.setStyle("-fx-background-color: #789455");
        buttonDivisionReport.setStyle("-fx-background-color: #789455");
    }
}
