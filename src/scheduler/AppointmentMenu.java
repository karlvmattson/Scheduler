package scheduler;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.User;
import utils.TimeFunctions;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Controller class for the appointment editing menu.
 */
public class AppointmentMenu implements ChildPaneController, Initializable {

    @FXML
    private Label labelUserName;
    @FXML
    private ComboBox<Contact> comboContact;
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

    private User currentUser; // the logged in user
    private MainWindow mainWindow; // controller for main window
    private Appointment newAppointment; // the appointment being modified
    private Boolean editMode; // flag for whether we are editing or creating an Appointment

    /**
     * Saves the new or modified appointment. Calls validation methods to check for errors in input.
     * @param actionEvent actionEvent
     */
    public void handleButtonSave(ActionEvent actionEvent) {

        // Validate entries
        if(!validateAppointmentEntries()) {
            return;  // error in data entry. Do not save.
        }

        // Get current time to log the update
        LocalDateTime currentTime = LocalDateTime.now();

        // Build LocalDateTime objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        LocalDateTime startTime = LocalDateTime.parse(textStartDate.getText() + " " + textStartTime.getText(),formatter);
        LocalDateTime endTime = LocalDateTime.parse(textEndDate.getText() + " " + textEndTime.getText(),formatter);
        LocalDateTime createDate;
        String createdBy;
        if(editMode) {
            createDate = newAppointment.getCreateDate();
            createdBy = newAppointment.getCreatedBy();
        }
        else {
            createDate = LocalDateTime.now();
            createdBy = currentUser.getUserName();
        }

        // make sure appointment time is within business hours
        if(!TimeFunctions.checkWithinBusinessHours(startTime)) {
            labelError.setText("Appointment time is outside of business hours.");
            labelError.setVisible(true);
            return;
        }

        // make sure there are no overlapping appointments
        if(!TimeFunctions.checkNoOverlaps(startTime,endTime, newAppointment.getCustomerID())) {
            labelError.setText("There are overlapping appointments for this customer.");
            labelError.setVisible(true);
            return;
        }

        if(editMode) {
            // modify existing appointment
            try {
                newAppointment = new Appointment(Integer.parseInt(textAppointmentID.getText()),textTitle.getText(),textDescription.getText(),
                        textLocation.getText(),textType.getText(),startTime,endTime,createDate,createdBy,LocalDateTime.now(),currentUser.getUserName(),
                        Integer.parseInt(textCustomerID.getText()),Integer.parseInt(textUserID.getText()),
                        comboContact.getSelectionModel().getSelectedItem().getContactID());
                new AppointmentDAOImpl().updateAppointment(newAppointment);
            }
            catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }

            // return to calling menu
            mainWindow.showMenu("ViewScheduleMenu.fxml");
        }
        else {

            // create new appointment record
            try {
                newAppointment = new Appointment(textTitle.getText(),textDescription.getText(),
                        textLocation.getText(),textType.getText(),startTime,endTime,createDate,createdBy,LocalDateTime.now(),currentUser.getUserName(),
                        Integer.parseInt(textCustomerID.getText()),Integer.parseInt(textUserID.getText()),
                        comboContact.getSelectionModel().getSelectedItem().getContactID());
                new AppointmentDAOImpl().addAppointment(newAppointment);
            }
            catch (SQLException sqlException) {
                System.out.println(sqlException.getMessage());
            }

            // return to calling menu
            mainWindow.showMenu("CustomerMenu.fxml");
        }
    }

    /**
     * Checks the entries on the appointment form to make sure they are valid. Displays appropriate error message if not.
     * @return true if entries are valid
     */
    public Boolean validateAppointmentEntries() {
        DateTimeFormatter formatter;


        // validate Start and End DATE
        formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            formatter.parse(textStartDate.getText());
        }
        catch (Exception e) {
            setErrorMessage("Please enter a valid start date.");
            return false;
        }
        try {
            formatter.parse(textEndDate.getText());
        }
        catch (Exception e) {
            setErrorMessage("Please enter a valid end date.");
            return false;
        }

        // validate Start and End TIME
        formatter = DateTimeFormatter.ofPattern("hh:mm a");
        try {
            formatter.parse(textStartTime.getText());
        }
        catch (Exception e) {
            setErrorMessage("Please enter a valid start time.");
            return false;
        }
        try {
            formatter.parse(textEndTime.getText());
        }
        catch (Exception e) {
            setErrorMessage("Please enter a valid end time.");
            return false;
        }

        // verify that fields are not empty
        if(comboContact.getSelectionModel().getSelectedIndex() < 0) {
            setErrorMessage("Please select a contact.");
            return false;
        }
        if(textTitle.getText().length() < 1) {
            setErrorMessage("Please enter a title.");
            return false;
        }
        if(textType.getText().length() < 1) {
            setErrorMessage("Please enter a type.");
            return false;
        }
        if(textLocation.getText().length() < 1) {
            setErrorMessage("Please enter a location.");
            return false;
        }
        if(textDescription.getText().length() < 1) {
            setErrorMessage("Please enter a description.");
            return false;
        }
        boolean exists = false;
        try {
            ObservableList<Customer> customers = new CustomerDAOImpl().getAllCustomers();
            int id = Integer.parseInt(textCustomerID.getText());
            for(Customer customer : customers) {
                if (customer.getCustomerID() == id) {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                setErrorMessage("Invalid CustomerID entered.");
                return false;
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return true;
    }
    /**
     * Discard any entries and return to the sending menu
     * @param actionEvent actionEvent
     */
    public void handleButtonCancel(ActionEvent actionEvent) {
        if(editMode) {
            // we were editing, return to the schedule menu
            mainWindow.showMenu("ViewScheduleMenu.fxml");
        }
        else {
            // we were creating a new record, return to customer menu
            mainWindow.showMenu("CustomerMenu.fxml");
        }
    }

    /**
     * Stores a reference to the calling window's controller.
     * @param menuController Controller for main window, needed for communication.
     */
    public void setMenuController(MainWindow menuController ) {
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
     * Displays an error message in the UI.
     * @param message error message to display
     */
    public void setErrorMessage(String message) {
        labelError.setVisible(true);
        labelError.setText(message);
    }

    /**
     * Sets up the page. Loads contacts into the combo box.
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editMode = false;
        try {
            comboContact.setItems(new ContactDAOImpl().getAllContacts());
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    /**
     * Saves the selected CustomerID for use when creating a new appointment record.
     * @param useCustomerID the customerID to use for the new appointment
     */
    public void newAppointment(int useCustomerID) {
        textCustomerID.setText(String.valueOf(useCustomerID));
        textUserID.setText(String.valueOf(currentUser.getUserID()));
    }

    /**
     * Sets up the page to edit an existing appointment.
     * @param appointment the appointment to be modified
     */
    public void loadAppointment(Appointment appointment) {
        editMode = true;
        this.newAppointment = appointment;
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
        for(Contact contact : comboContact.getItems()) {
            if(contact.getContactID() == newAppointment.getContactID()) {
                comboContact.setValue(contact);
            }
        }
    }
}
