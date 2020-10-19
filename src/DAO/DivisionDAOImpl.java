package DAO;

import DAOInterface.DivisionDAO;
import javafx.collections.ObservableList;
import model.Division;
import model.Division;
import utils.TimeFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DivisionDAOImpl implements DivisionDAO {
    /**
     * @param divisionName the division name to search for
     * @return the Division object
     */
    @Override
    public Division getDivision(String divisionName) {
        ResultSet result;
        Division foundDivision;

        // create and run query
        String query = "SELECT * FROM divisions WHERE Division = " + divisionName;
        result = DBQuery.executePreparedStatement(query);

        // create and return division if result is found
        foundDivision = makeDivisionFromResult(result);
        return foundDivision;
    }

    /**
     * @param divisionID the division ID to search for
     * @return the Division object
     */
    @Override
    public Division getDivision(int divisionID) {
        ResultSet result;
        Division foundDivision;

        // create and run query
        String query = "SELECT * FROM divisions WHERE Division_ID = " + divisionID;
        result = DBQuery.executePreparedStatement(query);

        // create division if result is found
        foundDivision = makeDivisionFromResult(result);
        return foundDivision;
    }

    /**
     * @return a list containing all divisions
     * @throws SQLException sqlexception
     */
    @Override
    public ObservableList<Division> getAllDivisions() throws SQLException {
        ObservableList<Division> allDivisions = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM divisions";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            Division nextDivision = makeDivisionFromResult(result);
            allDivisions.add(nextDivision);
        }

        return allDivisions;
    }

    /**
     * @param newDivision the division to add
     * @throws SQLException sqlexception
     */
    @Override
    public void addDivision(Division newDivision) throws SQLException {
        // create query
        String query = "INSERT INTO divisions (Division, Create_Date, Created_By, Last_Update, Last_Updated_By, Country_ID) " +
                "VALUES (?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newDivision);
        DBQuery.executePreparedStatement();
    }

    /**
     * @param toUpdate the division to update
     * @throws SQLException sqlexception
     */
    @Override
    public void updateDivision(Division toUpdate) throws SQLException {
        // create query
        String query = "UPDATE divisions " +
                "SET Division = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Country_ID = ? " +
                "WHERE Division_ID = " + toUpdate.getDivisionID();
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        DBQuery.executePreparedStatement();
    }

    @Override
    public void deleteDivision(Division deleteTarget) {
        // create and run query
        String query = "DELETE * FROM divisions WHERE Division_ID = " + deleteTarget.getDivisionID();
        DBQuery.executePreparedStatement(query);
    }


    /**
     * Fill in the fields in a PreparedStatement.
     *
     * @param division the Division to use
     */
    private void fillInPS(Division division) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, division.getDivision());
        statement.setTimestamp(2, TimeFunctions.toDBTimestamp(division.getCreateDate()));
        statement.setString(3, division.getCreatedBy());
        statement.setTimestamp(4, TimeFunctions.toDBTimestamp(division.getLastUpdate()));
        statement.setString(5, division.getLastUpdatedBy());
        statement.setInt(6, division.getCountryID());
    }

    /**
     * Helper function to extract a single division from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired division's data
     * @return A Division constructed from the given ResultSet
     */
    private Division makeDivisionFromResult(ResultSet result) {
        Division newDivision = null;

        // create division if result is found
        try {
            int divisionID = result.getInt("Division_ID");
            String division = result.getString("Division");
            LocalDateTime createDate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Create_Date"));
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Last_Update"));
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int countryID = result.getInt("Country_ID");
            newDivision = new Division(divisionID, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newDivision;
    }
}
