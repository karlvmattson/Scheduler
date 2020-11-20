package DAOInterface;

import javafx.collections.ObservableList;
import model.User;

import java.sql.SQLException;

/**
 * Defines interface for working with User objects.
 */
public interface UserDAO {
    User getUser(String userName) throws SQLException;

    User getUser(int userID) throws SQLException;

    ObservableList<User> getAllUsers() throws SQLException;

    void addUser(User newUser) throws SQLException;

    void updateUser(User toUpdate) throws SQLException;

    void deleteUser(User deleteTarget);
}
