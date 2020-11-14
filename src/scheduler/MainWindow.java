package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainWindow {

    @FXML
    private AnchorPane childPane;

    @FXML
    private Button buttonCustomers;

    @FXML
    private Button buttonViewSchedule;

    @FXML
    private Button buttonAppointments;

    @FXML
    private Button buttonReports;

    /**
     * Turns the side menu buttons off.
     */
    public void disableSideButtons() {
        buttonAppointments.setDisable(true);
        buttonCustomers.setDisable(true);
        buttonReports.setDisable(true);
        buttonViewSchedule.setDisable(true);
    }

    /**
     * Turns the side menu buttons on.
     */
    public void enableSideButtons() {
        buttonAppointments.setDisable(false);
        buttonCustomers.setDisable(false);
        buttonReports.setDisable(false);
        buttonViewSchedule.setDisable(false);
    }

    /**
     * Loads the login menu into the main pane in the GUI.
     * @throws IOException IOException
     */
    public void showLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"));
        Parent root = fxmlLoader.load();

        // Set up a controller and load in the login screen
        LoginMenu loginMenuController = fxmlLoader.getController();
        loginMenuController.setMenuController(this);
        childPane.getChildren().setAll(root.getChildrenUnmodifiable());
    }

    public void handleButtonCustomers(ActionEvent actionEvent) {
    }

    public void handleButtonAppointments(ActionEvent actionEvent) {
    }

    public void handleButtonViewSchedule(ActionEvent actionEvent) {
    }

    public void handleButtonReports(ActionEvent actionEvent) {
    }
}
