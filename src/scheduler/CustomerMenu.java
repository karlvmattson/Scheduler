package scheduler;

import DAO.*;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerMenu implements ChildPaneController, Initializable {
    @FXML
    private Label labelModifyError;
    @FXML
    private TableColumn<CustomerWithDivision, String> CustomerDivisionColumn;
    @FXML
    private VBox boxAddModDelete;
    @FXML
    private HBox boxEdit1;
    @FXML
    private VBox boxEdit2;
    @FXML
    private Button buttonCreateAppointment;
    @FXML
    private TableColumn<CustomerWithDivision, String> CustomerPhoneColumn;
    @FXML
    private TableColumn<CustomerWithDivision, String> CustomerAddressColumn;
    @FXML
    private TableColumn<CustomerWithDivision, String> CustomerPostalCodeColumn;
    @FXML
    private TableColumn<CustomerWithDivision, Integer> CustomerIDColumn;
    @FXML
    private TableColumn<CustomerWithDivision, String> CustomerNameColumn;
    @FXML
    private TableView<CustomerWithDivision> tableCustomers;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonModify;
    @FXML
    private Button buttonDelete;
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
    private ComboBox<Division> comboDivision;
    @FXML
    private ComboBox<Country> comboCountry;
    @FXML
    private Label labelSelectCustomerFirstError;

    private MainWindow mainWindow;
    private User currentUser;
    private ObservableList<Country> countryList = FXCollections.observableArrayList();
    private ObservableList<Division> divisionList = FXCollections.observableArrayList();
    private Boolean modifyRecord;
    private Customer modifyCustomer;

    /**
     * Loads menu to add a new customer record.
     * @param actionEvent actionEvent
     */
    public void handleButtonAdd(ActionEvent actionEvent) {
        hideErrors();
        modifyRecord = false;
        textPostalCode.setText("");
        textPhone.setText("");
        textName.setText("");
        textID.setText("");
        textAddress.setText("");
        setupComboBoxes();
        showEditFields();
    }

    /**
     * Loads menu to modify the selected record.
     * @param actionEvent actionEvent
     */
    public void handleButtonModify(ActionEvent actionEvent) {
        hideErrors();
        Customer customer;
        if (tableCustomers.getSelectionModel().getSelectedIndex() == -1) {
            labelModifyError.setText("Select a customer first.");
            labelModifyError.setVisible(true);
            return;
        }
        customer = tableCustomers.getSelectionModel().getSelectedItem();
        modifyRecord = true;
        textPostalCode.setText(customer.getPostalCode());
        textPhone.setText(customer.getPhone());
        textName.setText(customer.getCustomerName());
        textID.setText(String.valueOf(customer.getCustomerID()));
        textAddress.setText(customer.getAddress());
        setupComboBoxes();
        comboDivision.getSelectionModel().select(new DivisionDAOImpl().getDivision(customer.getDivisionID()));
        comboCountry.getSelectionModel().select(new CountryDAOImpl().getCountry((new DivisionDAOImpl().getDivision(customer.getDivisionID())).getCountryID()));
        showEditFields();
    }

    /**
     * Deletes the selected customer.
     * @param actionEvent actionEvent
     */
    public void handleButtonDelete(ActionEvent actionEvent) {
        hideErrors();

        if (tableCustomers.getSelectionModel().getSelectedIndex() > -1) {
            CustomerWithDivision selected = tableCustomers.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete customer?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                labelModifyError.setText("Customer record for " + selected.getCustomerName() + " has been deleted.");
                labelModifyError.setVisible(true);
                new CustomerDAOImpl().deleteCustomer(selected);
                reloadTableView();
            }
        } else {
            labelModifyError.setText("Please select an appointment first.");
            labelModifyError.setVisible(true);
        }
    }

    /**
     * Validates data in the customer add/modify fields.
     * @return true if entries are valid
     */
    private boolean  validateEntries() {
        // make sure there are no empty fields
        if(textAddress.getLength() < 1 || textName.getLength() < 1 || textPhone.getLength() < 1 || textPostalCode.getLength() < 1) {
            labelModifyError.setText("Please fill out all fields.");
            return false;
        }

        // make sure a country and division are selected
        if(comboCountry.getSelectionModel().getSelectedIndex() < 0 || comboDivision.getSelectionModel().getSelectedIndex() < 0) {
            labelModifyError.setText("Please select a country and division.");
            return false;
        }

        return true;
    }

    /**
     * Saves the new or modified customer. Calls validation methods to ensure input is correct.
     * @param actionEvent actionEvent
     */
    public void handleButtonSave(ActionEvent actionEvent) {
        hideErrors();

        if(!validateEntries()) { return; } // make sure entries are valid

        String name = textName.getText();
        String address = textAddress.getText();
        String phone = textPhone.getText();
        String postalCode = textPostalCode.getText();
        String modifyUser = currentUser.getUserName();
        int divisionID = comboDivision.getSelectionModel().getSelectedItem().getDivisionID();

        // Get current time to log the update
        LocalDateTime currentTime = LocalDateTime.now();

        // Build LocalDateTime objects
        LocalDateTime createDate;
        String createdBy;
        if(modifyRecord) {
            createDate = modifyCustomer.getCreateDate();
            createdBy = modifyCustomer.getCreatedBy();
        }
        else {
            createDate = LocalDateTime.now();
            createdBy = currentUser.getUserName();
        }

        // create and save new customer record
        try {
            if (modifyRecord) {
                int customerID = modifyCustomer.getCustomerID();
                Customer newCustomer = new Customer(customerID, name, address, postalCode, phone, createDate,
                        createdBy, currentTime, modifyUser, divisionID);
                new CustomerDAOImpl().updateCustomer(newCustomer);
            } else {
                Customer newCustomer = new Customer(name, address, postalCode, phone, currentTime,
                        modifyUser, currentTime, modifyUser, divisionID);
                new CustomerDAOImpl().addCustomer(newCustomer);

            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
            labelModifyError.setText("SQL Error.");
            labelModifyError.setVisible(true);
            return;
        }
        reloadTableView();
        hideEditFields();
    }

    /**
     * Cancels customer create/edit.
     * @param actionEvent actionEvent
     */
    public void handleButtonCancel(ActionEvent actionEvent) {
        hideErrors();
        hideEditFields();
    }

    /**
     * Creates a new appointment for the selected customer.
     * @param actionEvent actionEvent
     */
    public void handleButtonCreateAppointment(ActionEvent actionEvent) {

        // make sure a customer is selected
        if (tableCustomers.getSelectionModel().getSelectedIndex() > -1) {
            Customer selected = tableCustomers.getSelectionModel().getSelectedItem();
            mainWindow.setAppointmentEditMode(false);
            mainWindow.setCurrentCustomer(selected);
            mainWindow.showAppointmentMenu();
        } else {
            labelSelectCustomerFirstError.setVisible(true);
        }
    }

    /**
     * LAMBDA EXPRESSION Lambdas are used to generate the cell value factories. This is easier and safer than using the
     * older PropertyValueFactory functions.
     */
    private void setupTableViewColumns() {

        CustomerIDColumn.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getCustomerID()).asObject());
        CustomerNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCustomerName()));
        CustomerPhoneColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPhone()));
        CustomerAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress()));
        CustomerPostalCodeColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPostalCode()));
        CustomerDivisionColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDivision()));
    }

    /**
     * Loads data to the combo boxes.
     */
    private void setupComboBoxes() {
        comboDivision.setValue(null);
        comboDivision.setItems(null);
        comboCountry.setValue(null);
        comboCountry.setItems(countryList);
    }

    /**
     * Pulls new data from the DB and loads it into the tableview.
     */
    private void reloadTableView() {
        ObservableList<CustomerWithDivision> customerList = FXCollections.observableArrayList();
        try {
            for (Customer customer : new CustomerDAOImpl().getAllCustomers()) {
                customerList.add(new CustomerWithDivision(customer));
                tableCustomers.setItems(customerList);

            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        SortedList<CustomerWithDivision> sortedList = new SortedList<>(customerList);
        tableCustomers.setItems(sortedList);
        sortedList.comparatorProperty().bind(tableCustomers.comparatorProperty());
    }

    /**
     * Filters the Division editing box by the selected country.
     */
    private void filterDivisionEditBox() {
        hideErrors();
        Country selectedCountry = comboCountry.getSelectionModel().getSelectedItem();
        FilteredList<Division> filteredList = divisionList.filtered(a -> a.getCountryID() == selectedCountry.getCountryID());
        comboDivision.setItems(filteredList);
    }

    /**
     * Sets up the screen.
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countryList = new CountryDAOImpl().getAllCountries();
            divisionList = new DivisionDAOImpl().getAllDivisions();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        setupTableViewColumns();
        setupComboBoxes();
        reloadTableView();
    }

    /**
     * Displays editable fields for add/modifying customers.
     */
    public void showEditFields() {
        hideErrors();
        // disable menus
        mainWindow.disableSideButtons();
        tableCustomers.setDisable(true);
        buttonCreateAppointment.setDisable(true);
        boxAddModDelete.setDisable(true);

        // show editable fields
        boxEdit1.setVisible(true);
        boxEdit2.setVisible(true);
    }

    /**
     * Hides editable fields for add/modifying customers.
     */
    public void hideEditFields() {
        hideErrors();
        // enable menus
        mainWindow.enableSideButtons();
        tableCustomers.setDisable(false);
        buttonCreateAppointment.setDisable(false);
        boxAddModDelete.setDisable(false);

        // show editable fields
        boxEdit1.setVisible(false);
        boxEdit2.setVisible(false);
    }

    /**
     * Triggers filtering of the Division combo box.
     * @param actionEvent actionEvent
     */
    public void handleComboCountryChange(ActionEvent actionEvent) {
        hideErrors();
        if(comboCountry.getSelectionModel().getSelectedIndex() > -1) {
            filterDivisionEditBox();
        }
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
     * Stores reference to the main window's controller.
     * @param menuController Controller for main window, needed for communication.
     */
    @Override
    public void setMenuController(MainWindow menuController) {
        mainWindow = menuController;
    }

    public void hideErrors() {
        labelModifyError.setVisible(false);
        labelSelectCustomerFirstError.setVisible(false);
    }
}
