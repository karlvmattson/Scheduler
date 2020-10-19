package DAOInterface;

import javafx.collections.ObservableList;
import model.User;

import java.sql.SQLException;

public interface UserDAO {
    User getUser(String userName);

    User getUser(int userID);

    ObservableList<User> getAllUsers() throws SQLException;

    void addUser(User newUser) throws SQLException;

    void updateUser(User toUpdate) throws SQLException;

    void deleteUser(User deleteTarget);
}
