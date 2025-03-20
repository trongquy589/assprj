package dao;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public User getOne(int userID) {
        String sql = "SELECT * FROM Users WHERE UserID = ? AND IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
        }
        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(createUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
        }
        return users;
    }

    public boolean add(User user) {
        String sql = """
                INSERT INTO Users (FullName, Email, Password, Role, PhoneNumber, DateOfBirth, Gender, Address, IdentityNumber, PlaceOfIssue, DateOfIssue, Nationality, Ethnic, Religion, Occupation, IsActive)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPhoneNumber());
            ps.setDate(6, user.getDateOfBirth() != null ? new java.sql.Date(user.getDateOfBirth().getTime()) : null);
            ps.setString(7, user.getGender());
            ps.setString(8, user.getAddress());
            ps.setString(9, user.getIdentityNumber());
            ps.setString(10, user.getPlaceOfIssue());
            ps.setDate(11, user.getDateOfIssue() != null ? new java.sql.Date(user.getDateOfIssue().getTime()) : null);
            ps.setString(12, user.getNationality());
            ps.setString(13, user.getEthnic());
            ps.setString(14, user.getReligion());
            ps.setString(15, user.getOccupation());
            ps.setString(16, user.isActive() ? "true" : "false");

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
            return false;
        }
    }

    public boolean update(User user) {
        String sql = """
                UPDATE Users
                SET FullName = ?, Email = ?, Password = ?, Role = ?, PhoneNumber = ?, DateOfBirth = ?, Gender = ?, Address = ?, IdentityNumber = ?, PlaceOfIssue = ?, DateOfIssue = ?, Nationality = ?, Ethnic = ?, Religion = ?, Occupation = ?, IsActive = ?
                WHERE UserID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getRole());
            ps.setString(5, user.getPhoneNumber());
            ps.setDate(6, user.getDateOfBirth() != null ? new java.sql.Date(user.getDateOfBirth().getTime()) : null);
            ps.setString(7, user.getGender());
            ps.setString(8, user.getAddress());
            ps.setString(9, user.getIdentityNumber());
            ps.setString(10, user.getPlaceOfIssue());
            ps.setDate(11, user.getDateOfIssue() != null ? new java.sql.Date(user.getDateOfIssue().getTime()) : null);
            ps.setString(12, user.getNationality());
            ps.setString(13, user.getEthnic());
            ps.setString(14, user.getReligion());
            ps.setString(15, user.getOccupation());
            ps.setString(16, user.isActive() ? "true" : "false");
            ps.setInt(17, user.getUserID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
            return false;
        }
    }

    /*    public boolean delete(int userID) {
        String sql = "DELETE FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
            return false;
        }
    }*/
    // For enable/disable, you might want to add an `IsActive` column to your table
    // and update that instead of deleting.  This is a "soft delete".
    public boolean enable(int userID) {
        String sql = "UPDATE Users SET IsActive = 'true' WHERE UserID = ?"; // Assuming you have an IsActive column
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
            return false;
        }
    }

    public boolean disable(int userID) {
        String sql = "UPDATE Users SET IsActive = 'false' WHERE UserID = ?"; // Assuming you have an IsActive column
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log the error properly
            return false;
        }
    }

    private User createUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserID(rs.getInt("UserID"));
        user.setFullName(rs.getString("FullName"));
        user.setEmail(rs.getString("Email"));
        user.setPassword(rs.getString("Password"));
        user.setRole(rs.getString("Role"));
        user.setPhoneNumber(rs.getString("PhoneNumber"));
        user.setDateOfBirth(rs.getDate("DateOfBirth"));
        user.setGender(rs.getString("Gender"));
        user.setAddress(rs.getString("Address"));
        user.setIdentityNumber(rs.getString("IdentityNumber"));
        user.setPlaceOfIssue(rs.getString("PlaceOfIssue"));
        user.setDateOfIssue(rs.getDate("DateOfIssue"));
        user.setNationality(rs.getString("Nationality"));
        user.setEthnic(rs.getString("Ethnic"));
        user.setReligion(rs.getString("Religion"));
        user.setOccupation(rs.getString("Occupation"));
        user.setActive(rs.getString("IsActive").equals("true"));
        user.setCreatedAt(rs.getTimestamp("CreatedAt"));
        user.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
        return user;
    }
}
