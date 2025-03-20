package model;

import java.util.Date;

public class Registration {
    private int registrationID;
    private int userID;
    private String userFullName; // Thêm thuộc tính này
    private String registrationType;
    private Date startDate;
    private Date endDate;
    private String reason;
    private String status;
    private int approvedBy;
    private Date approvalDate;
    private String comments;
    private int householdID;
    private Date createdAt;
    private Date updatedAt;

    // Getter & Setter cho userFullName
    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    // Getter & Setter cho các trường còn lại
    public int getRegistrationID() { return registrationID; }
    public void setRegistrationID(int registrationID) { this.registrationID = registrationID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getRegistrationType() { return registrationType; }
    public void setRegistrationType(String registrationType) { this.registrationType = registrationType; }

    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getApprovedBy() { return approvedBy; }
    public void setApprovedBy(int approvedBy) { this.approvedBy = approvedBy; }

    public Date getApprovalDate() { return approvalDate; }
    public void setApprovalDate(Date approvalDate) { this.approvalDate = approvalDate; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    public int getHouseholdID() { return householdID; }
    public void setHouseholdID(int householdID) { this.householdID = householdID; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }


}
