package DAO;

import DAOInterface.CountryDAO;
import utils.TimeFunctions;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Implementation of CountryDAO interface.
 */
public class CountryDAOImpl implements CountryDAO {

    /**
     * @param countryName the country name to search for
     * @return the Country object
     */
    @Override
    public Country getCountry(String countryName) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet result;
            Country foundCountry;

            // create and run query
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM countries WHERE Country_Name = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setString(1, countryName);
            result = preparedStatement.executeQuery();

            // create and return contact if result is found
            result.first();
            foundCountry = makeCountryFromResult(result);
            return foundCountry;
        }
        catch (SQLException sqlException) {
            return null;
        }
    }

    /**
     * @param countryID the country ID to search for
     * @return the Country object
     */
    @Override
    public Country getCountry(int countryID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet result;
            Country foundCountry;

            // create and run query
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM countries WHERE Country_ID = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setInt(1, countryID);
            result = preparedStatement.executeQuery();

            // create and return contact if result is found
            result.first();
            foundCountry = makeCountryFromResult(result);
            return foundCountry;
        }
        catch (SQLException sqlException) {
            return null;
        }
    }

    /**
     * Returns a list containing all countries in the database.
     *
     * @return an ObservableList of all countries in the DB
     * @throws SQLException SQLException
     */
    @Override
    public ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> allCountries = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM countries";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            Country nextCountry = makeCountryFromResult(result);
            allCountries.add(nextCountry);
        }

        return allCountries;
    }

    /**
     * Adds a new country to the database. Assumes table has already been checked for duplicate values. Assumes times and user values already filled in.
     *
     * @param newCountry the country to be added
     */
    @Override
    public void addCountry(Country newCountry) throws SQLException {
        // create query
        String query = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "VALUES (?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newCountry);
        DBQuery.executePreparedStatement();
    }

    /**
     * Updates an existing country in the database. The param should already have the updated time and user for the update columns.
     *
     * @param toUpdate the country to be updated
     */
    @Override
    public void updateCountry(Country toUpdate) throws SQLException {
        // create query
        String query = "UPDATE countries " +
                "SET Country = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ? " +
                "WHERE Country_ID = " + toUpdate.getCountryID();
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        DBQuery.executePreparedStatement();
    }

    /**
     * Deletes a country in the database.
     *
     * @param deleteTarget the country to be deleted
     */
    @Override
    public void deleteCountry(Country deleteTarget) {
        // create and run query
        String query = "DELETE * FROM countries WHERE Country_ID = " + deleteTarget.getCountryID();
        DBQuery.executePreparedStatement(query);
    }

    /**
     * Fill in the fields in a PreparedStatement
     *
     * @param country the Country to use
     */
    private void fillInPS(Country country) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, country.getCountry());
        statement.setTimestamp(2, TimeFunctions.toDBTimestamp(country.getCreateDate()));
        statement.setString(3, country.getCreatedBy());
        statement.setTimestamp(4, TimeFunctions.toDBTimestamp(country.getLastUpdate()));
        statement.setString(5, country.getLastUpdatedBy());
    }

    /**
     * Helper function to extract a single country from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired country's data
     * @return A Country constructed from the given ResultSet
     */
    private Country makeCountryFromResult(ResultSet result) {
        Country newCountry = null;

        // create country if result is found
        try {
            int countryID = result.getInt("Country_ID");
            String country = result.getString("Country");
            LocalDateTime createDate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Create_Date"));
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Last_Update"));
            String lastUpdatedBy = result.getString("Last_Updated_By");
            newCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newCountry;
    }
}
