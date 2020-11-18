package DAOInterface;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.SQLException;

public interface ContactDAO {
    Contact getContact(String contactName) throws SQLException;

    Contact getContact(int contactID) throws SQLException;

    ObservableList<Contact> getAllContacts() throws SQLException;

    void addContact(Contact newContact) throws SQLException;

    void updateContact(Contact toUpdate) throws SQLException;

    void deleteContact(Contact deleteTarget);
}
