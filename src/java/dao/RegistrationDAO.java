package dao;

import model.Registration;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationDAO {

    public List<Registration> getAll() {
        List<Registration> registrations = new ArrayList<>();
        String sql = """
        SELECT r.*, u.FullName AS UserFullName 
        FROM Registrations r
        JOIN Users u ON r.UserID = u.UserID
    """;

        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Registration reg = createRegistrationFromResultSet(rs);
                reg.setUserFullName(rs.getString("UserFullName")); // Gán tên người dùng
                registrations.add(reg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return registrations;
    }

// Cập nhật hàm lấy một đơn
    public Registration getOne(int registrationID) {
        String sql = """
        SELECT r.*, u.FullName AS UserFullName 
        FROM Registrations r
        JOIN Users u ON r.UserID = u.UserID
        WHERE r.RegistrationID = ?
    """;

        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, registrationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Registration reg = createRegistrationFromResultSet(rs);
                reg.setUserFullName(rs.getString("UserFullName")); // Gán tên người dùng
                return reg;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean add(Registration registration) {
        String sql = """
                INSERT INTO Registrations (UserID, RegistrationType, StartDate, EndDate, Reason, Status, ApprovedBy, ApprovalDate, Comments, HouseholdID, IsActive)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'true')
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, registration.getUserID());
            ps.setString(2, registration.getRegistrationType());
            ps.setDate(3, new java.sql.Date(registration.getStartDate().getTime()));
            ps.setDate(4, registration.getEndDate() != null ? new java.sql.Date(registration.getEndDate().getTime()) : null);
            ps.setString(5, registration.getReason());
            ps.setString(6, registration.getStatus());
            ps.setInt(7, registration.getApprovedBy());
            ps.setDate(8, registration.getApprovalDate() != null ? new java.sql.Date(registration.getApprovalDate().getTime()) : null);
            ps.setString(9, registration.getComments());
            ps.setInt(10, registration.getHouseholdID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Registration registration) {
        String sql = """
                UPDATE Registrations
                SET UserID = ?, RegistrationType = ?, StartDate = ?, EndDate = ?, Reason = ?, Status = ?, ApprovedBy = ?, ApprovalDate = ?, Comments = ?, HouseholdID = ?
                WHERE RegistrationID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, registration.getUserID());
            ps.setString(2, registration.getRegistrationType());
            ps.setDate(3, new java.sql.Date(registration.getStartDate().getTime()));
            ps.setDate(4, registration.getEndDate() != null ? new java.sql.Date(registration.getEndDate().getTime()) : null);
            ps.setString(5, registration.getReason());
            ps.setString(6, registration.getStatus());
            ps.setInt(7, registration.getApprovedBy());
            ps.setDate(8, registration.getApprovalDate() != null ? new java.sql.Date(registration.getApprovalDate().getTime()) : null);
            ps.setString(9, registration.getComments());
            ps.setInt(10, registration.getHouseholdID());
            ps.setInt(11, registration.getRegistrationID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int registrationID) {
        String sql = "UPDATE Registrations SET IsActive = 'false' WHERE RegistrationID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, registrationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enable(int registrationID) {
        String sql = "UPDATE Registrations SET IsActive = 'true' WHERE RegistrationID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, registrationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Registration createRegistrationFromResultSet(ResultSet rs) throws SQLException {
        Registration registration = new Registration();
        registration.setRegistrationID(rs.getInt("RegistrationID"));
        registration.setUserID(rs.getInt("UserID"));
        registration.setRegistrationType(rs.getString("RegistrationType"));
        registration.setStartDate(rs.getDate("StartDate"));
        registration.setEndDate(rs.getDate("EndDate"));
        registration.setReason(rs.getString("Reason"));
        registration.setStatus(rs.getString("Status"));
        registration.setApprovedBy(rs.getInt("ApprovedBy"));
        registration.setApprovalDate(rs.getDate("ApprovalDate"));
        registration.setComments(rs.getString("Comments"));
        registration.setHouseholdID(rs.getInt("HouseholdID"));
        registration.setCreatedAt(rs.getDate("CreatedAt"));
        registration.setUpdatedAt(rs.getDate("UpdatedAt"));
        return registration;
    }
public boolean updateStatus(int registrationID, String status) {
    String sql = "UPDATE Registrations SET Status = ? WHERE RegistrationID = ?";
    try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
        ps.setString(1, status);
        ps.setInt(2, registrationID);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

}
