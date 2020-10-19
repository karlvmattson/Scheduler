package DAO;

import DAOInterface.UserDAO;
import javafx.collections.ObservableList;
import model.User;
import utils.TimeFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserDAOImpl implements UserDAO {
    /**
     * @param userName the user name to search for
     * @return the User object
     */
    @Override
    public User getUser(String userName) {
        ResultSet result;
        User foundUser;

        // create and run query
        String query = "SELECT * FROM users WHERE User_Name = " + userName;
        result = DBQuery.executePreparedStatement(query);

        // create and return user if result is found
        foundUser = makeUserFromResult(result);
        return foundUser;
    }

    /**
     * @param userID the user ID to search for
     * @return the User object
     */
    @Override
    public User getUser(int userID) {
        ResultSet result;
        User foundUser;

        // create and run query
        String query = "SELECT * FROM users WHERE User_ID = " + userID;
        result = DBQuery.executePreparedStatement(query);

        // create user if result is found
        foundUser = makeUserFromResult(result);
        return foundUser;
    }

    /**
     * @return a list containing all users
     * @throws SQLException sqlexception
     */
    @Override
    public ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> allUsers = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM users";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            User nextUser = makeUserFromResult(result);
            allUsers.add(nextUser);
        }

        return allUsers;
    }

    /**
     * @param newUser the user to add
     * @throws SQLException sqlexception
     */
    @Override
    public void addUser(User newUser) throws SQLException {
        // create query
        String query = "INSERT INTO users (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By) " +
                "VALUES (?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newUser);
        DBQuery.executePreparedStatement();
    }

    /**
     * @param toUpdate the user to update
     * @throws SQLException sqlexception
     */
    @Override
    public void updateUser(User toUpdate) throws SQLException {
        // create query
        String query = "UPDATE users " +
                "SET User_Name = ?, Password = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ? " +
                "WHERE User_ID = " + toUpdate.getUserID();
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        DBQuery.executePreparedStatement();
    }

    @Override
    public void deleteUser(User deleteTarget) {
        // create and run query
        String query = "DELETE * FROM users WHERE User_ID = " + deleteTarget.getUserID();
        DBQuery.executePreparedStatement(query);
    }


    /**
     * Fill in the fields in a PreparedStatement.
     * (User_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By)
     *
     * @param user the User to use
     */
    private void fillInPS(User user) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getPassword());
        statement.setTimestamp(3, TimeFunctions.toDBTimestamp(user.getCreateDate()));
        statement.setString(4, user.getCreatedBy());
        statement.setTimestamp(5, TimeFunctions.toDBTimestamp(user.getLastUpdated()));
        statement.setString(6, user.getLastUpdatedBy());
    }

    /**
     * Helper function to extract a single user from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired user's data
     * @return A User constructed from the given ResultSet
     */
    private User makeUserFromResult(ResultSet result) {
        User newUser = null;

        // create user if result is found
        try {
            int userID = result.getInt("User_ID");
            String user = result.getString("User_Name");
            String password = result.getString("Password");
            LocalDateTime createDate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Create_Date"));
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Last_Update"));
            String lastUpdatedBy = result.getString("Last_Updated_By");
            newUser = new User(userID, user, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newUser;
    }
}
