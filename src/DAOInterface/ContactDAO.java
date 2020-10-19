package DAOInterface;

import javafx.collections.ObservableList;
import model.Contact;

import java.sql.SQLException;

public interface ContactDAO {
    Contact getContact(String contactName);

    Contact getContact(int contactID);

    ObservableList<Contact> getAllContacts() throws SQLException;

    void addContact(Contact newContact) throws SQLException;

    void updateContact(Contact toUpdate) throws SQLException;

    void deleteContact(Contact deleteTarget);
}
