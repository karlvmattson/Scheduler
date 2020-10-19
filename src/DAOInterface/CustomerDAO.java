package DAOInterface;

import javafx.collections.ObservableList;
import model.Customer;

import java.sql.SQLException;

public interface CustomerDAO {
    Customer getCustomer(String customerName);

    Customer getCustomer(int customerID);

    ObservableList<Customer> getAllCustomers() throws SQLException;

    void addCustomer(Customer newCustomer) throws SQLException;

    void updateCustomer(Customer toUpdate) throws SQLException;

    void deleteCustomer(Customer deleteTarget);
}
