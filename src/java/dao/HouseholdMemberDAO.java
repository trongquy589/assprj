package dao;

import model.HouseholdMember;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouseholdMemberDAO {

    public HouseholdMember getOne(int memberID) {
        String sql = "SELECT * FROM HouseholdMembers WHERE MemberID = ? AND IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, memberID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createHouseholdMemberFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<HouseholdMember> getAll() {
        List<HouseholdMember> householdMembers = new ArrayList<>();
        String sql = "SELECT * FROM HouseholdMembers WHERE IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                householdMembers.add(createHouseholdMemberFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return householdMembers;
    }

    public boolean add(HouseholdMember householdMember) {
        String sql = """
                INSERT INTO HouseholdMembers (HouseholdID, UserID, Relationship, IsActive)
                VALUES (?, ?, ?, 'true')
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, householdMember.getHouseholdID());
            ps.setInt(2, householdMember.getUserID());
            ps.setString(3, householdMember.getRelationship());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(HouseholdMember householdMember) {
        String sql = """
                UPDATE HouseholdMembers
                SET HouseholdID = ?, UserID = ?, Relationship = ?
                WHERE MemberID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, householdMember.getHouseholdID());
            ps.setInt(2, householdMember.getUserID());
            ps.setString(3, householdMember.getRelationship());
            ps.setInt(4, householdMember.getMemberID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int memberID) {
        String sql = "UPDATE HouseholdMembers SET IsActive = 'false' WHERE MemberID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, memberID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enable(int memberID) {
        String sql = "UPDATE HouseholdMembers SET IsActive = 'true' WHERE MemberID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, memberID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private HouseholdMember createHouseholdMemberFromResultSet(ResultSet rs) throws SQLException {
        HouseholdMember householdMember = new HouseholdMember();
        householdMember.setMemberID(rs.getInt("MemberID"));
        householdMember.setHouseholdID(rs.getInt("HouseholdID"));
        householdMember.setUserID(rs.getInt("UserID"));
        householdMember.setRelationship(rs.getString("Relationship"));
        householdMember.setActive(rs.getString("IsActive").equals("true"));
        return householdMember;
    }
}
