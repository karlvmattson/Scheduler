package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.User;
import utils.ReportFunctions;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for the report menu.
 */
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

    /**
     * Stores the controller from the main window.
     * @param menuController Controller for main window, needed for communication.
     */
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

    /**
     * Highlights button and runs selected report.
     * @param actionEvent actionEvent
     */
    public void handleButtonAppointmentTotals(ActionEvent actionEvent) {
        resetButtons();
        buttonAppointmentTotals.setStyle("-fx-background-color: #3BAD5B");

        textReportWindow.setText(ReportFunctions.getReport("AppointmentTotals"));
    }

    /**
     * Highlights button and runs selected report.
     * @param actionEvent actionEvent
     */
    public void handleButtonContactSchedule(ActionEvent actionEvent) {
        resetButtons();
        buttonContactSchedule.setStyle("-fx-background-color: #3BAD5B");
        textReportWindow.setText(ReportFunctions.getReport("ContactSchedule"));
    }

    /**
     * Highlights button and runs selected report.
     * @param actionEvent actionEvent
     */
    public void handleButtonDivisionReport(ActionEvent actionEvent) {
        resetButtons();
        buttonDivisionReport.setStyle("-fx-background-color: #3BAD5B");
        textReportWindow.setText(ReportFunctions.getReport("DivisionReport"));
    }

    /**
     * Sets up window.
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetButtons();

    }

    /**
     * Resets buttons to be deselected.
     */
    private void resetButtons() {
        buttonAppointmentTotals.setStyle("-fx-background-color: #789455");
        buttonContactSchedule.setStyle("-fx-background-color: #789455");
        buttonDivisionReport.setStyle("-fx-background-color: #789455");
    }
}
