package DAO;

import DAOInterface.AppointmentDAO;
import DAOInterface.CustomerDAO;
import javafx.collections.ObservableList;
import model.Customer;
import utils.TimeFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CustomerDAOImpl implements CustomerDAO {
    /**
     * @param customerName the customer name to search for
     * @return the Customer object
     */
    @Override
    public Customer getCustomer(String customerName) {
        ResultSet result;
        Customer foundCustomer;

        // create and run query
        String query = "SELECT * FROM customers WHERE Customer_Name = " + customerName;
        result = DBQuery.executePreparedStatement(query);

        // create and return customer if result is found
        foundCustomer = makeCustomerFromResult(result);
        return foundCustomer;
    }

    /**
     * @param customerID the customer ID to search for
     * @return the Customer object
     */
    @Override
    public Customer getCustomer(int customerID) {
        ResultSet result;
        Customer foundCustomer;

        // create and run query
        String query = "SELECT * FROM customers WHERE Customer_ID = " + customerID;
        result = DBQuery.executePreparedStatement(query);

        // create customer if result is found
        foundCustomer = makeCustomerFromResult(result);
        return foundCustomer;
    }

    /**
     * @return a list containing all customers
     * @throws SQLException sqlexception
     */
    @Override
    public ObservableList<Customer> getAllCustomers() throws SQLException {
        ObservableList<Customer> allCustomers = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM customers";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            Customer nextCustomer = makeCustomerFromResult(result);
            allCustomers.add(nextCustomer);
        }

        return allCustomers;
    }

    /**
     * @param newCustomer the customer to add
     * @throws SQLException sqlexception
     */
    @Override
    public void addCustomer(Customer newCustomer) throws SQLException {
        // create query
        String query = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newCustomer);
        DBQuery.executePreparedStatement();
    }

    /**
     * @param toUpdate the customer to update
     * @throws SQLException sqlexception
     */
    @Override
    public void updateCustomer(Customer toUpdate) throws SQLException {
        // create query
        String query = "UPDATE customers " +
                "SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, " +
                "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? " +
                "WHERE Customer_ID = " + toUpdate.getCustomerID();
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        DBQuery.executePreparedStatement();
    }

    @Override
    public void deleteCustomer(Customer deleteTarget) {
        // first delete all of the customer's appointments
        AppointmentDAOImpl appointment = new AppointmentDAOImpl();
        appointment.deleteAppointmentByCustomer(deleteTarget);
//        String query = "DELETE * FROM appointments WHERE Customer_ID = " + deleteTarget.getCustomerID();
//        DBQuery.executePreparedStatement(query);

        // create and run query
        String query = "DELETE * FROM customers WHERE Customer_ID = " + deleteTarget.getCustomerID();
        DBQuery.executePreparedStatement(query);
    }


    /**
     * Fill in the fields in a PreparedStatement.
     * (Customer_Name, Password, Create_Date, Created_By, Last_Update, Last_Updated_By)
     *
     * @param customer the Customer to use
     */
    private void fillInPS(Customer customer) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, customer.getCustomerName());
        statement.setString(2, customer.getAddress());
        statement.setString(3, customer.getPostalCode());
        statement.setString(4, customer.getPhone());
        statement.setTimestamp(5, TimeFunctions.toDBTimestamp(customer.getCreateDate()));
        statement.setString(6, customer.getCreatedBy());
        statement.setTimestamp(7, TimeFunctions.toDBTimestamp(customer.getLastUpdate()));
        statement.setString(8, customer.getLastUpdatedBy());
        statement.setInt(9, customer.getDivisionID());
    }

    /**
     * Helper function to extract a single customer from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired customer's data
     * @return A Customer constructed from the given ResultSet
     */
    private Customer makeCustomerFromResult(ResultSet result) {
        Customer newCustomer = null;

        // create customer if result is found
        try {
            int customerID = result.getInt("Customer_ID");
            String customer = result.getString("Customer_Name");
            String address = result.getString("Address");
            String postalCode = result.getString("Postal_Code");
            String phone = result.getString("Phone");
            LocalDateTime createDate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Create_Date"));
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Last_Update"));
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int divisionID = result.getInt("Division_ID");
            newCustomer = new Customer(customerID, customer, address, postalCode, phone,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newCustomer;
    }
}
