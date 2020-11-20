package model;

import java.time.LocalDateTime;

/**
 *
 */
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime CreateDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**
     * Constructor.
     * @param appointmentID DB appointment ID
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param createDate date record created
     * @param createdBy user who created record
     * @param lastUpdate date of last update
     * @param lastUpdatedBy user who last updated record
     * @param customerID FK customer ID
     * @param userID FK user ID
     * @param contactID FK contact ID
     */
    public Appointment(int appointmentID, String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        CreateDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Constructor.
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param startTime appointment start time
     * @param endTime appointment end time
     * @param createDate date record created
     * @param createdBy user who created record
     * @param lastUpdate date of last update
     * @param lastUpdatedBy user who last updated record
     * @param customerID FK customer ID
     * @param userID FK user ID
     * @param contactID FK contact ID
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int customerID, int userID, int contactID) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        CreateDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * @return the appointment ID
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * @param appointmentID the ID to set
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the appointment start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the time to set
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the appointment end time
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the time to set
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * @return the creation date/time
     */
    public LocalDateTime getCreateDate() {
        return CreateDate;
    }

    /**
     * @param createDate the date/time to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        CreateDate = createDate;
    }

    /**
     * @return the user who created this entry
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the user to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the date/time of the last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the date/time to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the last user who updated this entry
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the user name to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the foreign key of the associated customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * @param customerID the customerID to set
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * @return the foreign key of the associated user
     */
    public int getUserID() {
        return userID;
    }

    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @return the foreign key of the associated contact
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * @param contactID the contactID to set
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Returns a String representation of the object.
     * @return object as String
     */
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", title='" + title + '\'' +
                '}';
    }
}
