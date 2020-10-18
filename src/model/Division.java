package model;

import java.time.LocalDateTime;

public class Division {

    private int divisionID;
    private String division;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;
    private int countryID;

    public Division(int divisionID, String division, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int countryID) {
        this.divisionID = divisionID;
        this.division = division;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.countryID = countryID;
    }

    /**
     * @return the divisionID
     */
    public int getDivisionID() {
        return divisionID;
    }

    /**
     * @param divisionID the divisionID to set
     */
    public void setDivisionID(int divisionID) {
        this.divisionID = divisionID;
    }

    /**
     * @return the name of the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * @param division the division name to set
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * @return the creation date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the creation date to set
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
     * @param createdBy the user name to set as creator
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the time/date of the last update
     */
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the update time to set
     */
    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the user who last updated this entry
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the user name to set as last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the foreign key of the associated Country object
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID the countryID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    @Override
    public String toString() {
        return "Division{" +
                "divisionID=" + divisionID +
                ", division='" + division + '\'' +
                '}';
    }
}
