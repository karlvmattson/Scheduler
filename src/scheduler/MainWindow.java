package scheduler;

import DAO.AppointmentDAOImpl;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Appointment;
import model.Customer;
import model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private Button buttonReports;
    @FXML
    private Label labelUpcomingAppointment;
    @FXML
    private Label labelUpcomingAppointmentAlert;

    private User currentUser;  // logged in user. Set from LoginMenu
    private Customer currentCustomer;  // customer to be modified. Set from CustomerMenu
    private Appointment currentAppointment;  // appointment to be modified. Set from ViewMenuSchedule
    private Boolean appointmentEditMode;    // flag to indicate if we are creating a new record or editing an existing one
                                            // when swapping to AppointmentMenu

    /**
     * Displays the upcoming appointment on the side bar.
     * @param appointment the appointment to display beneath the alert
     */
    public void setUpcomingAppointment( Appointment appointment) {
        labelUpcomingAppointmentAlert.setText("Appointment scheduled within the next 15 minutes!");
        labelUpcomingAppointmentAlert.setStyle("-fx-font-color : RED");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd  hh:mm a");
        String time = appointment.getStartTime().format(formatter);
        String id = String.valueOf(appointment.getAppointmentID());
        labelUpcomingAppointment.setText("Appointment ID: " + id + "\n" + time);
        labelUpcomingAppointment.setVisible(true);
    }

    /**
     * LAMBDA EXPRESSION For conciseness. Checks if there is an appointment scheduled in the next 15 minutes and calls setUpcomingAppointment to show if found.
     */
    public void checkUpcomingAppointment() {
        labelUpcomingAppointmentAlert.setVisible(true);
        try {
            ObservableList<Appointment> appointmentList = new AppointmentDAOImpl().getAllAppointments();

            FilteredList<Appointment> filteredAppointments = appointmentList.filtered(
                    a -> a.getStartTime().isEqual(LocalDateTime.now()) ||
                    (a.getStartTime().isAfter(LocalDateTime.now()) &
                            a.getStartTime().isBefore(LocalDateTime.now().plusMinutes(15))));

            SortedList<Appointment> sortedList = new SortedList<>(filteredAppointments);
            if(sortedList.size() > 0) {
                setUpcomingAppointment(sortedList.get(0));
            }
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /**
     * Turns the side menu buttons off.
     */
    public void disableSideButtons() {
        buttonCustomers.setDisable(true);
        buttonReports.setDisable(true);
        buttonViewSchedule.setDisable(true);
    }

    /**
     * Turns the side menu buttons on.
     */
    public void enableSideButtons() {
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
        appointmentEditMode = false;
        currentUser = user;
        enableSideButtons();
        childPane.getChildren().clear();
        showMenu("CustomerMenu.fxml");
        highButton(buttonCustomers);

        checkUpcomingAppointment();
    }

    /**
     * Loads AppointmentMenu and sets it up for either record creation or update based on appointmentEditMode.
     */
    public void showAppointmentMenu() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AppointmentMenu.fxml"));
            Parent root = fxmlLoader.load();

            // pass the current user to the child pane
            AppointmentMenu childPaneController = fxmlLoader.getController();
            childPaneController.setUser(currentUser);
            childPaneController.setMenuController(this);
            if(appointmentEditMode) {
                childPaneController.loadAppointment(currentAppointment);
            }
            else
            {
                childPaneController.newAppointment(currentCustomer.getCustomerID());
            }
            childPane.getChildren().setAll(root.getChildrenUnmodifiable());
            disableSideButtons();
        }
        catch(IOException ioException) {
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }

    }

    /**
     * Changes the displayed menu in the child pane.
     * @param menuFile the fxml file to display in the child pane
     */
    public void showMenu(String menuFile) {
        enableSideButtons();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(menuFile));
            Parent root = fxmlLoader.load();

            // pass the current user to the child pane
            ChildPaneController childPaneController = fxmlLoader.getController();
            childPaneController.setUser(currentUser);
            childPaneController.setMenuController(this);

            childPane.getChildren().setAll(root.getChildrenUnmodifiable());
        }
        catch(IOException ioException) {
            System.out.println(ioException.getMessage());
            ioException.printStackTrace();
        }
    }

    /**
     * Helper function to highlight the active menu's button.
     * @param button button to highlight
     */
    private void highButton(Button button) {

        // reset color on all buttons
        ObservableList<Node> children = vboxMenuButtons.getChildren();
        for (Node child : children) {
            if (child instanceof Button) {
                child.setStyle("-fx-background-color: #789455");
            }
        }

        // highlight the active menu's button
        button.setStyle("-fx-background-color: #D2DBC6");
    }

    /**
     * Sets appointmentEditMode.
     * @param appointmentEditMode true if user intends to edit an Appointment record.
     */
    public void setAppointmentEditMode(Boolean appointmentEditMode) {
        this.appointmentEditMode = appointmentEditMode;
    }

    /**
     * Setter.
     * @param currentCustomer customer to set
     */
    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    /**
     * Setter.
     * @param currentAppointment appointment to set
     */
    public void setCurrentAppointment(Appointment currentAppointment) {
        this.currentAppointment = currentAppointment;
    }
}
