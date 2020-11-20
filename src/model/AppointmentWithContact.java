package model;

import DAO.ContactDAOImpl;

/**
 * Object class for appointments that pulls in the associated contact.
 */
public class AppointmentWithContact extends Appointment{

    private String contactName;


    /**
     * Getter.
     * @return name of contact
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter.
     * @param contactName name of contact
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Constructor.
     * @param appointment appointment to associate with contact
     */
    public AppointmentWithContact(Appointment appointment) {
        super(appointment.getAppointmentID(), appointment.getTitle(), appointment.getDescription(), appointment.getLocation(), appointment.getType(), appointment.getStartTime(), appointment.getEndTime(), appointment.getCreateDate(), appointment.getCreatedBy(), appointment.getLastUpdate(), appointment.getLastUpdatedBy(), appointment.getCustomerID(), appointment.getUserID(), appointment.getContactID());
        this.contactName = (new ContactDAOImpl().getContact(appointment.getContactID())).getContactName();

    }
}
