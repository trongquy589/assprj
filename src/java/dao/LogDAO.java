package dao;

import model.Log;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LogDAO {

    public Log getOne(int logID) {
        String sql = "SELECT * FROM Logs WHERE LogID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, logID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createLogFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Log> getAll() {
        List<Log> logs = new ArrayList<>();
        String sql = "SELECT * FROM Logs";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                logs.add(createLogFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    public boolean add(Log log) {
        String sql = """
                INSERT INTO Logs (UserID, Action, Timestamp, Details)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, log.getUserID());
            ps.setString(2, log.getAction());
            ps.setDate(3, new java.sql.Date(log.getTimestamp().getTime()));
            ps.setString(4, log.getDetails());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Logs are typically not updated
    public boolean delete(int logID) {
        String sql = "DELETE FROM Logs WHERE LogID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, logID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Log createLogFromResultSet(ResultSet rs) throws SQLException {
        Log log = new Log();
        log.setLogID(rs.getInt("LogID"));
        log.setUserID(rs.getInt("UserID"));
        log.setAction(rs.getString("Action"));
        log.setTimestamp(rs.getTimestamp("Timestamp"));
        log.setDetails(rs.getString("Details"));
        return log;
    }
    //Logs không cần enable và disable
}