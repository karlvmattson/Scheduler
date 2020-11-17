package scheduler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewScheduleMenu implements ChildPaneController, Initializable {
    @FXML
    private TableView<?> tableAppointments;
    @FXML
    private Label labelUserName;
    @FXML
    private RadioButton radioFilterByWeek;
    @FXML
    private RadioButton radioFilterByMonth;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonModify;
    @FXML
    private Button buttonDelete;
    private User currentUser;

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

    /**
     * Set username label to the logged in user.
     * @param url url
     * @param resourceBundle rb
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
