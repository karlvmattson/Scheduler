package scheduler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.User;

public class ReportsMenu implements ChildPaneController{
    public Label labelUserName;
    public Button buttonAppointmentTotals;
    public Button buttonContactSchedule;
    public Button buttonDivisionReport;
    public TextArea textReportWindow;

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
    }

    public void handleButtonAppointmentTotals(ActionEvent actionEvent) {
    }

    public void handleButtonContactSchedule(ActionEvent actionEvent) {
    }

    public void handleButtonDivisionReport(ActionEvent actionEvent) {
    }
}
