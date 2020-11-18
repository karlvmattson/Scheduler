package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.User;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentMenu implements ChildPaneController, Initializable {

    @FXML
    private Label labelUserName;
    private Button buttonAdd;
    private Button buttonModify;
    private Button buttonDelete;
    @FXML
    private ComboBox<String> comboContact;
    @FXML
    private TextField textAppointmentID;
    @FXML
    private TextField textTitle;
    @FXML
    private TextField textType;
    @FXML
    private TextField textLocation;
    @FXML
    private TextField textDescription;
    @FXML
    private TextField textStartDate;
    @FXML
    private TextField textStartTime;
    @FXML
    private TextField textEndDate;
    @FXML
    private TextField textEndTime;
    @FXML
    private TextField textCustomerID;
    @FXML
    private TextField textUserID;
    @FXML
    private Label labelError;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    private User currentUser;
    private MainWindow mainWindow;
    private Appointment newAppointment;

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
        currentUser = user;
        labelUserName.setText(currentUser.getUserName());
    }

    public void handleButtonSave(ActionEvent actionEvent) {
        mainWindow.showMenu("ViewScheduleMenu.fxml");
    }

    public void handleButtonCancel(ActionEvent actionEvent) {
        mainWindow.showMenu("ViewScheduleMenu.fxml");
    }

    /**
     * Stores a reference to the calling window's controller.
     * @param menuController Controller for main window, needed for communication.
     */
    public void setMenuController(MainWindow menuController ) {
        mainWindow = menuController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void loadAppointment(Appointment newAppointment) {
        textAppointmentID.setText(String.valueOf(newAppointment.getAppointmentID()));
        textCustomerID.setText(String.valueOf(newAppointment.getCustomerID()));
        textDescription.setText(newAppointment.getDescription());
        textLocation.setText(newAppointment.getLocation());
        textTitle.setText(newAppointment.getTitle());
        textType.setText(newAppointment.getType());
        textUserID.setText(String.valueOf(newAppointment.getUserID()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        textEndDate.setText(newAppointment.getEndTime().format(formatter));
        textStartDate.setText(newAppointment.getStartTime().format(formatter));
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
        textEndTime.setText(newAppointment.getEndTime().format(formatter));
        textStartTime.setText(newAppointment.getStartTime().format(formatter));




    }
}
