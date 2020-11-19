package model;

import DAO.DivisionDAOImpl;

import java.time.LocalDateTime;

public class CustomerWithDivision extends Customer{

    private String division;

    public CustomerWithDivision(int customerID, String customerName, String address, String postalCode, String phone, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdatedBy, int divisionID) {
        super(customerID, customerName, address, postalCode, phone, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionID);
        division = new DivisionDAOImpl().getDivision(divisionID).getDivision();

    }
    public CustomerWithDivision(Customer customer) {
        super(customer.getCustomerID(), customer.getCustomerName(), customer.getAddress(), customer.getPostalCode(), customer.getPhone(), customer.getCreateDate(), customer.getCreatedBy(), customer.getLastUpdate(), customer.getLastUpdatedBy(), customer.getDivisionID());
        this.division = (new DivisionDAOImpl().getDivision(customer.getDivisionID())).getDivision();

    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

}
