package scheduler;

import DAO.AppointmentDAOImpl;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Appointment;
import model.AppointmentWithContact;
import model.User;
import utils.TimeFunctions;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ViewScheduleMenu implements ChildPaneController, Initializable {
    @FXML
    private TableColumn<Appointment, Integer> AppointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String> TitleColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> LocationColumn;
    @FXML
    private TableColumn<AppointmentWithContact, String> ContactColumn;
    @FXML
    private TableColumn<Appointment, String> TypeColumn;
    @FXML
    private TableColumn<Appointment, String> StartColumn;
    @FXML
    private TableColumn<Appointment, String> EndColumn;
    @FXML
    private TableColumn<Appointment, Integer> CustomerIDColumn;
    @FXML
    private TableView<AppointmentWithContact> tableAppointments;
    @FXML
    private Label labelUserName;
    @FXML
    private RadioButton radioFilterByWeek;
    @FXML
    private RadioButton radioFilterByMonth;
    @FXML
    private Button buttonModify;
    @FXML
    private Button buttonDelete;
    @FXML
    private Label labelError;

    private User currentUser;  // logged in user
    private MainWindow mainWindow;  // controller for main window
    private ObservableList<AppointmentWithContact> appointmentList = FXCollections.observableArrayList();  // list of all Appointments


    /**
     * Takes and stores the current user of the application for logging purposes.
     *
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
        currentUser = user;
        labelUserName.setText(currentUser.getUserName());
    }

    /**
     * Reloads data for the tableView.
     */
    public void refreshData() {
        try {
            appointmentList.clear();
            for (Appointment appointment : new AppointmentDAOImpl().getAllAppointments()) {
                appointmentList.add(new AppointmentWithContact(appointment));
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

    }

    /**
     * Modify the selected appointment or display an error if none is selected.
     *
     * @param actionEvent actionEvent
     */
    public void handleButtonModify(ActionEvent actionEvent) {
        if (tableAppointments.getSelectionModel().getSelectedIndex() > -1) {
            Appointment selected = tableAppointments.getSelectionModel().getSelectedItem();
            mainWindow.setCurrentAppointment(selected);
            mainWindow.setAppointmentEditMode(true);
            mainWindow.showAppointmentMenu();
        } else {
            labelError.setText("Please select an appointment first.");
            labelError.setVisible(true);
        }
    }

    /**
     * Deletes the Appointment currently selected in the TableView.
     *
     * @param actionEvent actionEvent
     */
    public void handleButtonDelete(ActionEvent actionEvent) {
        if (tableAppointments.getSelectionModel().getSelectedIndex() > -1) {
            Appointment selected = tableAppointments.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete appointment?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                new AppointmentDAOImpl().deleteAppointment(selected);
                refreshData();
                if (radioFilterByWeek.isSelected()) {
                    handleRadioWeek(new ActionEvent());
                } else {
                    handleRadioMonth(new ActionEvent());
                }
            }
        } else {
            labelError.setText("Please select an appointment first.");
            labelError.setVisible(true);
        }
    }

    /**
     * LAMBDA FUNCTION to filter the list of appointments. Filters the list of appointments to show any in the next week.
     *
     * @param actionEvent actionEvent
     */
    public void handleRadioWeek(ActionEvent actionEvent) {
        radioFilterByWeek.setSelected(true);
        radioFilterByMonth.setSelected(false);

        FilteredList<AppointmentWithContact> filteredAppointments = appointmentList.filtered(a -> a.getStartTime().toLocalDate().isEqual(LocalDate.now()) ||
                (a.getStartTime().toLocalDate().isAfter(LocalDate.now()) &
                        a.getStartTime().toLocalDate().isBefore(LocalDate.now().plusDays(7))));
        buildSchedule(filteredAppointments);
    }

    /**
     * LAMBDA FUNCTION to filter the list of appointments. Filters the list of appointments to show any in the next month.
     *
     * @param actionEvent actionEvent
     */
    public void handleRadioMonth(ActionEvent actionEvent) {
        radioFilterByWeek.setSelected(false);
        radioFilterByMonth.setSelected(true);

        FilteredList<AppointmentWithContact> filteredAppointments = appointmentList.filtered(a -> a.getStartTime().toLocalDate().isEqual(LocalDate.now()) ||
                (a.getStartTime().toLocalDate().isAfter(LocalDate.now()) &
                        a.getStartTime().toLocalDate().isBefore(LocalDate.now().plusMonths(1))));
        buildSchedule(filteredAppointments);
    }

    /**
     * Wraps the filtered list into a SortedList and displays it in the TableView
     *
     * @param sourceList list filtered to items to be displayed
     */
    private void buildSchedule(FilteredList<AppointmentWithContact> sourceList) {
        SortedList<AppointmentWithContact> sortedList = new SortedList<>(sourceList);
        tableAppointments.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableAppointments.comparatorProperty());
    }

    /**
     * LAMBDA EXPRESSION Lambdas are used to generate the cell value factories. This is easier and safer than using the
     * older PropertyValueFactory functions.
     */
    private void setupTableViewColumns() {
        AppointmentIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getAppointmentID()).asObject());
        TitleColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getTitle()));
        DescriptionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));
        LocationColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLocation()));
        TypeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getType()));
        StartColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(TimeFunctions.formatDateTimeForDisplay(cellData.getValue().getStartTime())));
        EndColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(TimeFunctions.formatDateTimeForDisplay(cellData.getValue().getEndTime())));
        CustomerIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCustomerID()).asObject());

        // Originally done with nested getter calls but caused severe slowdown.
        // Created AppointmentWithContact class to pull contact name in ahead of time. Much better performance.
        ContactColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty((cellData.getValue().getContactName())));
    }

    /**
     * Set up the menu to it's initial state.
     *
     * @param url            url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        radioFilterByMonth.setSelected(false);
        radioFilterByWeek.setSelected(true);
        labelError.setVisible(false);

        refreshData();
        setupTableViewColumns();
        handleRadioWeek(new ActionEvent());
    }

    /**
     * Stores a reference to the calling window's controller.
     *
     * @param menuController Controller for main window, needed for communication.
     */
    public void setMenuController(MainWindow menuController) {
        mainWindow = menuController;
    }
}
