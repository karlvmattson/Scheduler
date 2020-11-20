package scheduler;

import DAO.UserDAOImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import utils.LogFunctions;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * Controller class for the login menu.
 */
public class LoginMenu implements Initializable {
    @FXML
    private Label labelRegion;
    @FXML
    private TextField textUserName;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label labelUserName;
    @FXML
    private Label labelPassword;
    @FXML
    private Label labelErrorMessage;
    @FXML
    private Button buttonLogin;
    @FXML
    private Button buttonQuit;
    @FXML
    private Label labelComputerRegion;
    private MainWindow mainWindow;
    private ResourceBundle resourceBundle;

    /**
     * Stores a reference to the calling window's controller.
     * @param menuController Controller for main window, needed for communication.
     */
    public void setMenuController(MainWindow menuController ) {
        mainWindow = menuController;
    }

    /**
     * Check to see if login is successful and handle the result.
     * @param actionEvent button is clicked
     */
    public void handleButtonLogin(ActionEvent actionEvent) {

        // check for blank fields and display appropriate error messages
        if(textUserName.getText().length()==0 || passwordField.getText().length()==0) {
            labelErrorMessage.setText(resourceBundle.getString("label.labelErrorMessageBlankEntry"));
            labelErrorMessage.setVisible(true);
            return;
        }

        UserDAOImpl userDAO = new UserDAOImpl();
        User user;
        LocalDateTime currentTime = java.time.LocalDateTime.now();
        String logMessage = "\n" + currentTime.toString() + " - ";
        String logFile = "login.txt";

        // get user if one matches the typed username
        try {
            user = userDAO.getUser(textUserName.getText());

            // check that username is valid
            if(user == null) { throw new Exception("User not found."); }

            // check password
            if(user.getPassword().equals(passwordField.getText())) {
                // log a successful login attempt
                logMessage = logMessage + "Successfully logged in as user: " + user.getUserName();
                LogFunctions.logEntry(logFile, logMessage);

                // send logged in user back up to the main window
                mainWindow.logInUser(user);
                return;

            }
        }
        catch(SQLException sqlException) {

            sqlException.printStackTrace();
        }
        catch(Exception exception) {
            System.out.println(exception.getMessage());
        }

        // log a failed login attempt
        logMessage = logMessage + "FAILED login attempt as user: " + textUserName.getText();
        LogFunctions.logEntry(logFile,logMessage);

        // display error message
        labelErrorMessage.setText(resourceBundle.getString("label.labelErrorMessage"));
        labelErrorMessage.setVisible(true);

    }

    /**
     * Exit the program.
     * @param actionEvent button is clicked
     */
    public void handleButtonQuit(ActionEvent actionEvent) {
        mainWindow.closeProgram();
    }

    /**
     * Final setup of login page. Get and display user's timezone.
     * @param url url
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        // Get user's timezone and display on login menu
        TimeZone tz = TimeZone.getDefault();
        labelComputerRegion.setText(tz.getDisplayName());

    }
    }
