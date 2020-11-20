package model;

import java.time.LocalDateTime;

/**
 * This class matches the COUNTRIES table in the DB.
 */
public class Country {

    private int countryID;
    private String country;
    private LocalDateTime createDate;
    private String createdBy;
    private LocalDateTime lastUpdate;
    private String lastUpdatedBy;

    /**
     * Constructor.
     * @param countryID DB country ID
     * @param country name of country
     * @param createDate date record created
     * @param createdBy user who created record
     * @param lastUpdate date of last update
     * @param lastUpdatedBy user who last updated record
     */
    public Country(int countryID, String country, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy) {
        this.countryID = countryID;
        this.country = country;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * @return the country ID
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     * @param countryID the country ID to set
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     * @return the country name
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country name to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the date the country entry was created
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the create date to set
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the user who created the country entry
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the name of the user who created the country entry
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * @return the last time this entry was updated
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
     * @return the last user who updated this entry
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * @param lastUpdatedBy the username to set
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Returns country name as a String.
     * @return country name
     */
    public String toString() {
        return country;
    }
}
