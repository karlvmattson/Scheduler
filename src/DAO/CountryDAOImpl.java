package DAO;

import DAOInterface.CountryDAO;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CountryDAOImpl implements CountryDAO {

    @Override
    public Country getCountry(String countryName) {
        return null;
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
            int countryID = result.getInt("Country_ID");
            String country = result.getString("Country");
            LocalDateTime createDate = result.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = result.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = result.getString("Last_Updated_By");
            Country nextCountry = new Country(countryID, country, createDate, createdBy, lastUpdate, lastUpdatedBy);
            allCountries.add(nextCountry);
        }

        return allCountries;
    }

    /**
     * Adds a new country to the database. Assumes table has already been checked for duplicate values.
     *
     * @param newCountry the country to be added
     */
    @Override
    public void addCountry(Country newCountry) throws SQLException {
        // create query
        String query = "INSERT INTO countries (Country, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "VALUES (?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement
        fillInPS(newCountry);

        // run query
        DBQuery.executePreparedStatement();
    }

    /**
     * Updates an existing country in the database.
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

        // fill in values in prepared statement
        fillInPS(toUpdate);

        // run query
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
        statement.setTimestamp(2, Timestamp.valueOf(country.getCreateDate()));
        statement.setString(3, country.getCreatedBy());
        statement.setTimestamp(4, Timestamp.valueOf(country.getLastUpdate()));
        statement.setString(5, country.getLastUpdatedBy());
    }
}
