package model;

import java.time.LocalDateTime;

public class User {
    private int userID;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdated;
    private String lastUpdatedBy;

    /**
     * Constructor.
     * @param userID DB User ID
     * @param userName name of user
     * @param password password of user
     * @param createDate date record created
     * @param createdBy user who created record
     * @param lastUpdated date of last update
     * @param lastUpdatedBy user who last updated record
     */
    public User(int userID, String userName, String password, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdated, String lastUpdatedBy) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the userID
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
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the user name to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the date/time this entry was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the date/time to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the user who created this entry
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the user name to set
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the date/time of the last update
     */
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the date/time to set
     */
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the user who last updated this entry
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
     * Returns a String representation of the object.
     * @return object as String
     */
    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
