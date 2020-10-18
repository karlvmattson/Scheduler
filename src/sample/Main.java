package sample;

import DAO.CountryDAOImpl;
import DAOInterface.CountryDAO;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import DAO.DBConnection;
import DAO.DBQuery;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {

        // Startup DB connection
        DBConnection.startConnection();

//        CountryDAOImpl countryDAO = new CountryDAOImpl();
//        countryDAO.getAllCountries();

//        String sqlStatement = "";
//        DBQuery.setPreparedStatement(conn, sqlStatement); // Create Statement
//        PreparedStatement statement = DBQuery.getPreparedStatement(); // Get Statement

        launch(args);

        // Close DB connection
        DBConnection.closeConnection();
    }
}
