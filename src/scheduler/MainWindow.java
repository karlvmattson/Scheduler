package scheduler;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow {

    @FXML
    private VBox vboxMenuButtons;

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

    private User currentUser;

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
        ResourceBundle rb = ResourceBundle.getBundle("scheduler/loginMenu", Locale.getDefault());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginMenu.fxml"), rb);
        Parent root = fxmlLoader.load();

        // Set up a controller and load in the login screen
        LoginMenu loginMenuController = fxmlLoader.getController();
        loginMenuController.setMenuController(this);
        childPane.getChildren().setAll(root.getChildrenUnmodifiable());
    }

    /**
     * Change to the Customer menu
     * @param actionEvent button clicked
     */
    public void handleButtonCustomers(ActionEvent actionEvent) {
        highButton(buttonCustomers);
        showMenu("CustomerMenu.fxml");

    }

    /**
     * Change to the Appointment menu
     * @param actionEvent button clicked
     */
    public void handleButtonAppointments(ActionEvent actionEvent) {
        highButton(buttonAppointments);
        showMenu("AppointmentMenu.fxml");

    }

    /**
     * Change to the View Schedule menu
     * @param actionEvent button clicked
     */
    public void handleButtonViewSchedule(ActionEvent actionEvent) {
        highButton(buttonViewSchedule);
        showMenu("ViewScheduleMenu.fxml");
    }

    /**
     * Change to the Report menu
     * @param actionEvent button clicked
     */
    public void handleButtonReports(ActionEvent actionEvent) {
        highButton(buttonReports);
        showMenu("ReportsMenu.fxml");
    }

    /**
     * Exits the program.
     */
    public void closeProgram() {
        // get a handle to the stage
        Stage stage = (Stage) buttonCustomers.getScene().getWindow();
        // close stage
        stage.close();
    }

    /**
     * Logs in a user and closes the login screen.
     * @param user user who just logged in
     */
    public void logInUser(User user) {
        currentUser = user;
        enableSideButtons();
        childPane.getChildren().clear();
        showMenu("CustomerMenu.fxml");
    }

    /**
     * Helper function to swap the menu in the child pane.
     * @param menuFile the fxml file to display in the child pane
     */
    private void showMenu(String menuFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(menuFile));
            Parent root = fxmlLoader.load();

            // pass the current user to the child pane
            ChildPaneController childPaneController = fxmlLoader.getController();
            childPaneController.setUser(currentUser);
        }
        catch(IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    /**
     * Helper function to highlight the active menu's button.
     * @param button button to highlight
     */
    private void highButton(Button button) {
        ObservableList<Node> children = vboxMenuButtons.getChildren();
        for (Node child : children) {
            if (child instanceof Button) {
                child.setStyle("-fx-background-color: #789455");
            }
        }

        button.setStyle("-fx-background-color: #D2DBC6");
    }
}
