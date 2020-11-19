package DAO;

import DAOInterface.AppointmentDAO;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import utils.TimeFunctions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentDAOImpl implements AppointmentDAO {
    /**
     * @param appointmentTitle the appointment name to search for
     * @return the Appointment object
     */
    @Override
    public Appointment getAppointment(String appointmentTitle) {
        ResultSet result;
        Appointment foundAppointment;

        // create and run query
        String query = "SELECT * FROM appointments WHERE Title = " + appointmentTitle;
        result = DBQuery.executePreparedStatement(query);

        // create and return appointment if result is found
        foundAppointment = makeAppointmentFromResult(result);
        return foundAppointment;
    }

    /**
     * @param appointmentID the appointment ID to search for
     * @return the Appointment object
     */
    @Override
    public Appointment getAppointment(int appointmentID) {
        ResultSet result;
        Appointment foundAppointment;

        // create and run query
        String query = "SELECT * FROM appointments WHERE Appointment_ID = " + appointmentID;
        result = DBQuery.executePreparedStatement(query);

        // create appointment if result is found
        foundAppointment = makeAppointmentFromResult(result);
        return foundAppointment;
    }

    /**
     * @return a list containing all appointments
     * @throws SQLException sqlexception
     */
    @Override
    public ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = javafx.collections.FXCollections.observableArrayList();
        ResultSet result;

        // create and run query
        String query = "SELECT * FROM appointments";
        result = DBQuery.executePreparedStatement(query);

        // loop through results and add them to the list
        while (result.next()) {
            Appointment nextAppointment = makeAppointmentFromResult(result);
            allAppointments.add(nextAppointment);
        }

        return allAppointments;
    }

    /**
     * @param newAppointment the appointment to add
     * @throws SQLException sqlexception
     */
    @Override
    public void addAppointment(Appointment newAppointment) throws SQLException {
        // create query
        String query = "INSERT INTO appointments (Title, Description, Location, Type, Start, " +
                "End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(newAppointment);
        DBQuery.executePreparedStatement();
    }

    /**
     * @param toUpdate the appointment to update
     * @throws SQLException sqlexception
     */
    @Override
    public void updateAppointment(Appointment toUpdate) throws SQLException {
        // create query
        String query = "UPDATE appointments " +
                "SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " +
                "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? " +
                "WHERE Appointment_ID = ?";
        DBQuery.setPreparedStatement(query);

        // fill in values in prepared statement and execute
        fillInPS(toUpdate);
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setInt(14,toUpdate.getAppointmentID());
        DBQuery.executePreparedStatement();
    }

    /**
     * @param deleteTarget the Appointment to delete
     */
    @Override
    public void deleteAppointment(Appointment deleteTarget) {
        // create and run query
        String query = "DELETE FROM appointments WHERE Appointment_ID = " + deleteTarget.getAppointmentID();
        DBQuery.executePreparedStatement(query);
    }

    /**
     * @param customer the Customer whose appointments we want to delete
     */
    @Override
    public void deleteAppointmentByCustomer(Customer customer) {
        // create and run query
        String query = "DELETE FROM appointments WHERE Customer_ID = " + customer.getCustomerID();
        DBQuery.executePreparedStatement(query);
    }


    /**
     * Fill in the fields in a PreparedStatement.
     * (Title, Description, Location, Type, Start, End,
     * Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)
     *
     * @param appointment the Appointment to use
     */
    private void fillInPS(Appointment appointment) throws SQLException {
        PreparedStatement statement = DBQuery.getPreparedStatement();
        statement.setString(1, appointment.getTitle());
        statement.setString(2, appointment.getDescription());
        statement.setString(3, appointment.getLocation());
        statement.setString(4, appointment.getType());
        statement.setTimestamp(5, TimeFunctions.toDBTimestamp(appointment.getStartTime()));
        statement.setTimestamp(6, TimeFunctions.toDBTimestamp(appointment.getEndTime()));
        statement.setTimestamp(7, TimeFunctions.toDBTimestamp(appointment.getCreateDate()));
        statement.setString(8, appointment.getCreatedBy());
        statement.setTimestamp(9, TimeFunctions.toDBTimestamp(appointment.getLastUpdate()));
        statement.setString(10, appointment.getLastUpdatedBy());
        statement.setInt(11, appointment.getCustomerID());
        statement.setInt(12, appointment.getUserID());
        statement.setInt(13, appointment.getContactID());

    }

    /**
     * Helper function to extract a single appointment from a ResultSet
     *
     * @param result A ResultSet with pointer set to the desired appointment's data
     * @return A Appointment constructed from the given ResultSet
     */
    private Appointment makeAppointmentFromResult(ResultSet result) {
        Appointment newAppointment = null;

        // create appointment if result is found
        try {
            int appointmentID = result.getInt("Appointment_ID");
            String title = result.getString("Title");
            String description = result.getString("Description");
            String location = result.getString("Location");
            String type = result.getString("Type");
            LocalDateTime start = TimeFunctions.fromDBTimestamp(result.getTimestamp("Start"));
            LocalDateTime end = TimeFunctions.fromDBTimestamp(result.getTimestamp("End"));
            LocalDateTime createDate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Create_Date"));
            String createdBy = result.getString("Created_By");
            LocalDateTime lastUpdate = TimeFunctions.fromDBTimestamp(result.getTimestamp("Last_Update"));
            String lastUpdatedBy = result.getString("Last_Updated_By");
            int customerID = result.getInt("Customer_ID");
            int userID = result.getInt("User_ID");
            int contactID = result.getInt("Contact_ID");
            newAppointment = new Appointment(appointmentID, title, description, location, type, start, end,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return newAppointment;
    }
}
