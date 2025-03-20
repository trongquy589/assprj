package dao;

import model.Notification;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDAO {

    public Notification getOne(int notificationID) {
        String sql = """
                SELECT n.*, u.FullName 
                FROM Notifications n
                JOIN Users u ON n.UserID = u.UserID
                WHERE n.NotificationID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, notificationID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createNotificationFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        }
        return null;
    }

    public List<Notification> getAll() {
        List<Notification> notifications = new ArrayList<>();
        String sql = """
                SELECT n.*, u.FullName 
                FROM Notifications n
                JOIN Users u ON n.UserID = u.UserID
                ORDER BY n.SentDate DESC
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                notifications.add(createNotificationFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        }
        return notifications;
    }

    public boolean add(Notification notification) {
        String sql = """
                INSERT INTO Notifications (UserID, Message, SentDate, IsRead, Type, ReferenceID)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, notification.getUserID());
            ps.setString(2, notification.getMessage());
            ps.setTimestamp(3, new Timestamp(notification.getSentDate().getTime()));
            ps.setBoolean(4, notification.isRead());
            ps.setString(5, notification.getType());
            if (notification.getReferenceID() != null) {
                ps.setInt(6, notification.getReferenceID());
            } else {
                ps.setNull(6, Types.INTEGER);
            }

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    notification.setNotificationID(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        }
        return false;
    }

    public boolean updateReadStatus(int notificationID, boolean isRead) {
        String sql = "UPDATE Notifications SET IsRead = ? WHERE NotificationID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setBoolean(1, isRead);
            ps.setInt(2, notificationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        }
        return false;
    }

    public boolean delete(int notificationID) {
        String sql = "DELETE FROM Notifications WHERE NotificationID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, notificationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log lỗi
        }
        return false;
    }

    private Notification createNotificationFromResultSet(ResultSet rs) throws SQLException {
        Notification notification = new Notification();
        notification.setNotificationID(rs.getInt("NotificationID"));
        notification.setUserID(rs.getInt("UserID"));
        notification.setMessage(rs.getString("Message"));
        notification.setSentDate(rs.getTimestamp("SentDate"));
        notification.setRead(rs.getBoolean("IsRead"));
        notification.setType(rs.getString("Type"));
        int referenceID = rs.getInt("ReferenceID");
        notification.setReferenceID(rs.wasNull() ? null : referenceID);

        // Lưu FullName thay vì UserID
        notification.setFullName(rs.getString("FullName"));

        return notification;
    }
}
