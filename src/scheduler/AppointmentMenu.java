package scheduler;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;

public class AppointmentMenu implements ChildPaneController{

    public Label labelUserName;
    public Button buttonAdd;
    public Button buttonModify;
    public Button buttonDelete;
    public ComboBox<String> comboContact;
    public TextField TextAppointmentID;
    public TextField textTitle;
    public TextField textType;
    public TextField textLocation;
    public TextField textDescription;
    public TextField textStartDate;
    public TextField textStartTime;
    public TextField textEndDate;
    public TextField textEndTime;
    public TextField textCustomerID;
    public TextField textUserID;
    public Label labelError;
    public Button buttonSave;
    public Button buttonCancel;
    private User currentUser;

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
