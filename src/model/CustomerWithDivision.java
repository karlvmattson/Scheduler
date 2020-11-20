package model;

import DAO.DivisionDAOImpl;

/**
 * Object class for customers. Pulls in the associated division.
 */
public class CustomerWithDivision extends Customer{

    private String division;

    /**
     * Constructor.
     * @param customer customer
     */
    public CustomerWithDivision(Customer customer) {
        super(customer.getCustomerID(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhone(), customer.getCreateDate(), customer.getCreatedBy(), customer.getLastUpdate(), customer.getLastUpdatedBy(), customer.getDivisionID());
        this.division = (new DivisionDAOImpl().getDivision(customer.getDivisionID())).getDivision();

    }

    /**
     * Getter.
     * @return division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter.
     * @param division division
     */
    public void setDivision(String division) {
        this.division = division;
    }

}
