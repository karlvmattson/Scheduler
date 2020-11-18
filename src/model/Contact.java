package model;

/**
 *
 */
public class Contact {
    private int contactID;
    private String contactName;
    private String email;

    public Contact(int contactID, String contactName, String email) {
        this.contactID = contactID;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * @return the contactID
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
     * @return the contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @param contactName the name to set
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return contactName;
    }
}
