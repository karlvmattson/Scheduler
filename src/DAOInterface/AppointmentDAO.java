package DAOInterface;

import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;

import java.sql.SQLException;
import java.time.LocalDateTime;

public interface AppointmentDAO {
    Appointment getAppointment(String appointmentName);

    Appointment getAppointment(int appointmentID);

    ObservableList<Appointment> getAllAppointments() throws SQLException;

    void addAppointment(Appointment newAppointment) throws SQLException;

    void updateAppointment(Appointment toUpdate) throws SQLException;

    void deleteAppointment(Appointment deleteTarget);

    void deleteAppointmentByCustomer(Customer customer);

    ObservableList<Appointment> getOverlappingAppointments(LocalDateTime startTime, LocalDateTime endTime, int customerID);

    ObservableList<String> getAppointmentTypes();


}
