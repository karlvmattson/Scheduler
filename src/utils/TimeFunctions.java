package utils;

import DAO.AppointmentDAOImpl;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import model.Appointment;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Functions related to working with time objects in the application.
 */
public class TimeFunctions {

    /**
     * Placeholder function for converting to Timestamp. Might need later if conversion issues arise from the sql driver.
     *
     * @param time the time to convert to a Timestamp formatted for the DB
     * @return a Timestamp in the correct format for the DB
     */
    public static Timestamp toDBTimestamp(LocalDateTime time) {
        Timestamp DBTime;
        DBTime = Timestamp.valueOf(time);
        return DBTime;
    }

    /**
     * Placeholder function for converting from Timestamp. Might need later if conversion issues arise from the sql driver.
     *
     * @param time the Timestamp to convert to LocalDateTime
     * @return the time converted to LocalDateTime
     */
    public static LocalDateTime fromDBTimestamp(Timestamp time) {
        LocalDateTime localTime;
        localTime = time.toLocalDateTime();
        return localTime;
    }

    /**
     * Converts a LocalDateTime object to a string formatted for display in the ViewSchedule tableview.
     * @param localDateTime time to be converted
     * @return String in format MM-dd hh:mm a
     */
    public static String formatDateTimeForDisplay(LocalDateTime localDateTime) {
        String formattedDateTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd hh:mm a");
        formattedDateTime = localDateTime.format(formatter);
        return formattedDateTime;
    }

    /**
     * Checks a LocalDateTime (user local time) against business hours and returns true if within business hours (8am EST - 10pm EST).
     * @param localDateTime time to be checked
     * @return true if within business hours
     */
    public static boolean checkWithinBusinessHours(LocalDateTime localDateTime) {
        // convert time to EST
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime appointmentTime = ZonedDateTime.of(localDateTime, zoneID);
        ZonedDateTime convertedAppointmentTime = appointmentTime.withZoneSameInstant(ZoneId.of("America/New_York"));

        // make sure this isn't a weekend
        if(convertedAppointmentTime.getDayOfWeek() == DayOfWeek.SATURDAY || convertedAppointmentTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            return false;
        }

        // make sure we are between 8am and 10pm
        return convertedAppointmentTime.getHour() >= 8 & convertedAppointmentTime.getHour() <= 22;
    }

    /**
     * Checks to make sure there are no overlapping appointments for a given customer and time.
     * @param startTime  start time to check
     * @param endTime end time to check
     * @param customerID customer to be checked
     * @param newAppointment true if this is a new appointment
     * @return true if no overlapping appointments
     */
    public static boolean checkNoOverlaps(LocalDateTime startTime, LocalDateTime endTime, int customerID, Boolean newAppointment) {
        try {
            ObservableList<Appointment> appointments = new AppointmentDAOImpl().getAllAppointments();
            FilteredList<Appointment> filteredList = appointments.filtered(a -> a.getCustomerID() ==
                        customerID & !(a.getStartTime().isAfter(endTime) || a.getEndTime().isBefore(startTime)));
            if(newAppointment) {
                return filteredList.size() <= 0;
            }
            else {
                return filteredList.size() <= 1;
            }
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return true;
    }

}
