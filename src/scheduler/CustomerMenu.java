package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Customer;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerMenu implements ChildPaneController, Initializable {
    @FXML
    private Button buttonCreateAppointment;
    @FXML
    private TableColumn<Customer, String> CustomerPhoneColumn;
    @FXML
    private TableColumn<Customer, String> CustomerAddressColumn;
    @FXML
    private TableColumn<Customer, String> CustomerPostalCodeColumn;
    @FXML
    private TableColumn<Customer, Integer> CustomerIDColumn;
    @FXML
    private TableColumn<Customer, String> CustomerNameColumn;
    @FXML
    private TableView<?> tableCustomers;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonModify;
    @FXML
    private Button buttonDelete;
    @FXML
    private ComboBox<String> comboFilterDivision;
    @FXML
    private ComboBox<String> comboFilterCountry;
    @FXML
    private Label labelUserName;
    @FXML
    private TextField textName;
    @FXML
    private TextField textAddress;
    @FXML
    private TextField textPhone;
    @FXML
    private TextField textPostalCode;
    @FXML
    private TextField textID;
    @FXML
    private Button buttonSave;
    @FXML
    private Button buttonCancel;
    @FXML
    private ComboBox<String> comboDivision;
    @FXML
    private ComboBox<String> comboCountry;

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

    public void handleButtonAdd(ActionEvent actionEvent) {
    }

    public void handleButtonModify(ActionEvent actionEvent) {
    }

    public void handleButtonDelete(ActionEvent actionEvent) {
    }

    public void handleButtonSave(ActionEvent actionEvent) {
    }

    public void handleButtonCancel(ActionEvent actionEvent) {
    }

    public void handleButtonCreateAppointment(ActionEvent actionEvent) {
        mainWindow.showMenu("AppointmentMenu");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
