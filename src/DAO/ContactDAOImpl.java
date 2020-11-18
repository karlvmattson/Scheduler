package DAO;

import DAOInterface.ContactDAO;
import javafx.collections.ObservableList;
import model.Contact;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOImpl implements ContactDAO {
    /**
     * @param contactName the contact name to search for
     * @return the Contact object
     */
    @Override
    public Contact getContact(String contactName) throws SQLException {

        Connection conn = DBConnection.getConnection();
        ResultSet result;
        Contact foundContact;

        // create and run query
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contacts WHERE Contact_Name = ?",
                ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);
        preparedStatement.setString(1,contactName);
        result = preparedStatement.executeQuery();

        // create and return contact if result is found
        result.first();
        foundContact = makeContactFromResult(result);
        return foundContact;
    }

    /**
     * @param contactID the contact ID to search for
     * @return the Contact object
     */
    @Override
    public Contact getContact(int contactID) {
        try {
            Connection conn = DBConnection.getConnection();
            ResultSet result;
            Contact foundContact;

            // create and run query
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM contacts WHERE Contact_ID = ?",
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setInt(1, contactID);
            result = preparedStatement.executeQuery();

            // create and return contact if result is found
            result.first();
            foundContact = makeContactFromResult(result);
            return foundContact;
        }
        catch (SQLException sqlException) {
            return null;
        }
    }

    /**
     * @return a list containing all contacts
     * @throws SQLException sqlexception
     */
    @Override
    public ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> allContacts = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM contacts";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            Contact nextContact = makeContactFromResult(result);
            allContacts.add(nextContact);
        }

        return allContacts;
    }

    /**
     * @param newContact the contact to add
     * @throws SQLException sqlexception
     */
    @Override
    public void addContact(Contact newContact) throws SQLException {
        // create query
        String query = "INSERT INTO contacts (Contact_Name, Email) " +
                "VALUES (?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newContact);
        DBQuery.executePreparedStatement();
    }

    /**
     * @param toUpdate the contact to update
     * @throws SQLException sqlexception
     */
    @Override
    public void updateContact(Contact toUpdate) throws SQLException {
        // create query
        String query = "UPDATE contacts " +
                "SET Contact_Name = ?, Email = ? WHERE Contact_ID = " + toUpdate.getContactID();
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        DBQuery.executePreparedStatement();
    }

    @Override
    public void deleteContact(Contact deleteTarget) {
        // create and run query
        String query = "DELETE * FROM contacts WHERE Contact_ID = " + deleteTarget.getContactID();
        DBQuery.executePreparedStatement(query);
    }


    /**
     * Fill in the fields in a PreparedStatement.
     *
     * @param contact the Contact to use
     */
    private void fillInPS(Contact contact) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, contact.getContactName());
        statement.setString(2, contact.getEmail());
    }

    /**
     * Helper function to extract a single contact from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired contact's data
     * @return A Contact constructed from the given ResultSet
     */
    private Contact makeContactFromResult(ResultSet result) {
        Contact newContact = null;

        // create contact if result is found
        try {
            int contactID = result.getInt("Contact_ID");
            String contact = result.getString("Contact_Name");
            String email = result.getString("Email");
            newContact = new Contact(contactID, contact, email);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newContact;
    }
}
