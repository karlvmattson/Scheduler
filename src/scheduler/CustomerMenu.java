package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.User;

public class CustomerMenu implements ChildPaneController{
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

    private User currentUser;

    public CustomerMenu() {
    }

    /**
     * Takes and stores the current user of the application for logging purposes.
     * @param user the current user of the application
     */
    @Override
    public void setUser(User user) {
        currentUser = user;
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
}
