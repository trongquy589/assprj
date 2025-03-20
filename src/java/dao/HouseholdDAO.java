package dao;

import model.Household;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HouseholdDAO {

    public Household getOne(int householdID) {
        String sql = "SELECT * FROM Households WHERE HouseholdID = ? AND IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, householdID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createHouseholdFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Household> getAll() {
        List<Household> households = new ArrayList<>();
        String sql = "SELECT * FROM Households WHERE IsActive = 'true'";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                households.add(createHouseholdFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return households;
    }

    public boolean add(Household household) {
        String sql = """
                INSERT INTO Households (HeadOfHouseholdID, Address, CreatedDate, Ward, District, Province, IsActive)
                VALUES (?, ?, ?, ?, ?, ?, 'true')
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, household.getHeadOfHouseholdID());
            ps.setString(2, household.getAddress());
            ps.setDate(3, new java.sql.Date(household.getCreatedDate().getTime()));
            ps.setString(4, household.getWard());
            ps.setString(5, household.getDistrict());
            ps.setString(6, household.getProvince());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Household household) {
        String sql = """
                UPDATE Households
                SET HeadOfHouseholdID = ?, Address = ?, CreatedDate = ?, Ward = ?, District = ?, Province = ?
                WHERE HouseholdID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, household.getHeadOfHouseholdID());
            ps.setString(2, household.getAddress());
            ps.setDate(3, new java.sql.Date(household.getCreatedDate().getTime()));
            ps.setString(4, household.getWard());
            ps.setString(5, household.getDistrict());
            ps.setString(6, household.getProvince());
            ps.setInt(7, household.getHouseholdID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int householdID) {
        String sql = "UPDATE Households SET IsActive = 'false' WHERE HouseholdID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, householdID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean enable(int householdID) {
        String sql = "UPDATE Households SET IsActive = 'true' WHERE HouseholdID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, householdID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Household createHouseholdFromResultSet(ResultSet rs) throws SQLException {
        Household household = new Household();
        household.setHouseholdID(rs.getInt("HouseholdID"));
        household.setHeadOfHouseholdID(rs.getInt("HeadOfHouseholdID"));
        household.setAddress(rs.getString("Address"));
        household.setCreatedDate(rs.getDate("CreatedDate"));
        household.setWard(rs.getString("Ward"));
        household.setDistrict(rs.getString("District"));
        household.setProvince(rs.getString("Province"));
        household.setActive(rs.getString("IsActive").equals("true"));
        return household;
    }
}
