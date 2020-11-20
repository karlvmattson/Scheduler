package utils;

import DAO.AppointmentDAOImpl;
import DAO.ContactDAOImpl;
import DAO.CustomerDAOImpl;
import DAO.DivisionDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Customer;
import model.Division;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportFunctions {


    /**
     * Runs a report and returns as a string. Report names are AppointmentTotals, ContactSchedule, DivisionReport.
     * @param reportName name of report
     * @return report as string
     */
    public static String getReport(String reportName) {
        String report = "";
        switch (reportName) {
            case "AppointmentTotals" :
                report = runAppointmentTotals();
                break;
            case "ContactSchedule" :
                report = runContactSchedule();
                break;

            case "DivisionReport" :
                report = runDivisionReport();
                break;
        }
        return report;
    }

    /**
     * LAMBDA EXPRESSION Used to filter a list by a criteria.
     * Generates a report with the total number of customer appointments by type and month.
     * @return report as string
     */
    public static String runAppointmentTotals() {
        StringBuilder report = new StringBuilder();
        // appointment totals by type and month
        // set up variables
        ObservableList<String> types = new AppointmentDAOImpl().getAppointmentTypes();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ObservableList<Appointment> filteredAppointments;
        int[] monthTotals = new int[12];

        // get items from DB
        try {
            appointments = new AppointmentDAOImpl().getAllAppointments();
        }
        catch(SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        // total up months
        for(Appointment appointment : appointments) {
            monthTotals[appointment.getStartTime().getMonth().getValue()-1]++;
        }

        // build report string
        // totals by type
        String format = "%-30s";
        report.append("---Appointment Totals---\n\n\n");
        report.append("Totals by type:\n\n");
        for(String type : types) {
            report.append(String.format(format,(type + ":")));
            filteredAppointments = appointments.filtered(a -> a.getType().equals(type)); // LAMBDA EXPRESSION to filter objects by type
            report.append(filteredAppointments.size());
            report.append("\n");
        }

        // totals by month
        String[] months = DateFormatSymbols.getInstance().getMonths();  // get names of months
        report.append("\n\nTotals by month:\n\n");
        format = "%-30s%d%s";
        for(int i = 0; i < 12; i++) {
            report.append(String.format(format,(months[i] + ":"),monthTotals[i],"\n"));
        }

        return report.toString();
    }

    /**
     * LAMBDA EXPRESSION Used to filter a list by a criteria.
     * Generates a report with a schedule for each contact in the organization.
     * @return report as string
     */
    public static String runContactSchedule() {

        // schedule for each contact
        // include appointmentID, title, type, description, start/end time, and customerID
        // set up variables
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        List<Appointment> filteredAppointments;
        StringBuilder report = new StringBuilder();

        // get data from DB
        try {
            contacts = new ContactDAOImpl().getAllContacts();
            appointments = new AppointmentDAOImpl().getAllAppointments();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        // build report string
        String format = "%-20s%-20s%-20s%-20s%-18s%-18s%-10s";
        report.append("---Schedule By Contact---\n\n\n");
        for(Contact contact : contacts) {
            report.append(contact.getContactName()).append("\n");
            report.append(String.format(format,"Appointment ID","Title","Type","Description","Start Time","End Time","Customer ID"))
                    .append("\n");
            filteredAppointments = new ArrayList<>(appointments.filtered(a -> a.getContactID() == contact.getContactID()));
            filteredAppointments.sort(Comparator.comparing(Appointment::getStartTime));
            for(Appointment a : filteredAppointments) {
                report.append(String.format(format, a.getAppointmentID(),a.getTitle(),a.getType(),
                        a.getDescription(),TimeFunctions.formatDateTimeForDisplay(a.getStartTime()),
                        TimeFunctions.formatDateTimeForDisplay(a.getEndTime()), a.getCustomerID()))
                        .append("\n");
            }
            report.append("\n");
        }

        return report.toString();
    }

    /**
     * LAMBDA EXPRESSION Used to filter a list by a criteria.
     * Generates a report with list of customers for each division and the total number of appointments for each customer.
     * @return report as string
     */
    public static String runDivisionReport() {
        // list of customers for each division and the number of appointments for each customer

        // set up variables
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        List<Appointment> filteredAppointments;
        List<Customer> filteredCustomers;
        StringBuilder report = new StringBuilder();

        // get data from DB
        try {
            divisions = new DivisionDAOImpl().getAllDivisions();
            appointments = new AppointmentDAOImpl().getAllAppointments();
            customers = new CustomerDAOImpl().getAllCustomers();
        }
        catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        // build report string
        String format = "%s%-30s%s";
        report.append("---Division Report---\n\n\n");
        for(Division division : divisions) {
            filteredCustomers = new ArrayList<>(customers.filtered(a -> a.getDivisionID() == division.getDivisionID()));
            filteredCustomers.sort(Comparator.comparing(Customer::getCustomerName));
            if(filteredCustomers.size() == 0) { continue; } // no customers so skip division
            report.append(division.getDivision()).append("\n");
            report.append(String.format(format,"     ","Customer","Total Appointments")).append("\n");

            for(Customer customer : filteredCustomers) {
                filteredAppointments = appointments.filtered(a -> a.getCustomerID() == customer.getCustomerID());
                report.append(String.format(format,"     ",(customer.getCustomerName() + ":"), filteredAppointments.size())).append("\n");
            }
            report.append("\n");
        }

        return report.toString();
    }
}


