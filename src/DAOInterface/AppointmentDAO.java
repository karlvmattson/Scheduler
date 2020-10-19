package DAOInterface;

import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.SQLException;

public interface AppointmentDAO {
    Appointment getAppointment(String appointmentName);

    Appointment getAppointment(int appointmentID);

    ObservableList<Appointment> getAllAppointments() throws SQLException;

    void addAppointment(Appointment newAppointment) throws SQLException;

    void updateAppointment(Appointment toUpdate) throws SQLException;

    void deleteAppointment(Appointment deleteTarget);
}
