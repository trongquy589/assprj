package model;

import java.util.Date;

public class Household {

    private int householdID;
    private int headOfHouseholdID;
    private String address;
    private Date createdDate;
    private String ward;
    private String district;
    private String province;
    private boolean isActive;

    // Constructors
    public Household() {
    }

    // Getters and Setters
    public int getHouseholdID() {
        return householdID;
    }

    public void setHouseholdID(int householdID) {
        this.householdID = householdID;
    }

    public int getHeadOfHouseholdID() {
        return headOfHouseholdID;
    }

    public void setHeadOfHouseholdID(int headOfHouseholdID) {
        this.headOfHouseholdID = headOfHouseholdID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
