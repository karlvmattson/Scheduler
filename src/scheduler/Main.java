package scheduler;

import DAO.DBConnection;
import DAO.UserDAOImpl;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Locale;

/**
 * Main class.
 */
public class Main extends Application {

    /**
     * Creates the initial program window and loads the first page.
     * @param primaryStage GUI stage
     * @throws Exception exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
        Parent root = fxmlLoader.load();

        // Set up a controller and load in the login screen
        MainWindow mainWindowController = fxmlLoader.getController();
        mainWindowController.disableSideButtons();
        mainWindowController.showLogin();

        // Display the window
        primaryStage.setTitle("WGU C195 - Scheduling Application - Karl Mattson");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * Main method.
     * @param args args
     */
    public static void main(String[] args) {

        // check that user language is either English or French
        if(!(Locale.getDefault().toString().startsWith("en") || Locale.getDefault().toString().startsWith("fr"))) {
            System.out.println(Locale.getDefault());
            System.out.println("System language must be set to English or French.");
            System.exit(0);
        }

        // Startup DB connection
        DBConnection.startConnection();

        // run program
        launch(args);

        // Close DB connection when user closes program
        DBConnection.closeConnection();
    }
}
