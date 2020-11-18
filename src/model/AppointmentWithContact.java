package model;

import DAO.ContactDAOImpl;

import java.time.LocalDateTime;

public class AppointmentWithContact extends Appointment{

    private String contactName;



    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public AppointmentWithContact(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        super(appointmentID, title, description, location, type, startTime, endTime, createDate, createdBy, lastUpdate, lastUpdatedBy, customerID, userID, contactID);
        contactName = (new ContactDAOImpl().getContact(contactID)).getContactName();
    }

    public AppointmentWithContact(Appointment appointment) {
        super(appointment.getAppointmentID(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getType(), appointment.getStartTime(), appointment.getEndTime(), appointment.getCreateDate(), appointment.getCreatedBy(), appointment.getLastUpdate(), appointment.getLastUpdatedBy(), appointment.getCustomerID(), appointment.getUserID(), appointment.getContactID());
        this.contactName = (new ContactDAOImpl().getContact(appointment.getContactID())).getContactName();

    }
}
