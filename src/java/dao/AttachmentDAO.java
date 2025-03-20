package dao;

import model.Attachment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AttachmentDAO {

    public Attachment getOne(int attachmentID) {
        String sql = "SELECT * FROM Attachments WHERE AttachmentID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, attachmentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createAttachmentFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Attachment> getAll() {
        List<Attachment> attachments = new ArrayList<>();
        String sql = "SELECT * FROM Attachments";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                attachments.add(createAttachmentFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return attachments;
    }

    public boolean add(Attachment attachment) {
        String sql = """
                INSERT INTO Attachments (RegistrationID, FileName, FilePath, FileType, FileSize, UploadDate)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, attachment.getRegistrationID());
            ps.setString(2, attachment.getFileName());
            ps.setString(3, attachment.getFilePath());
            ps.setString(4, attachment.getFileType());
            ps.setInt(5, attachment.getFileSize());
            ps.setDate(6, new java.sql.Date(attachment.getUploadDate().getTime()));
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(Attachment attachment) {
        String sql = """
                UPDATE Attachments
                SET RegistrationID = ?, FileName = ?, FilePath = ?, FileType = ?, FileSize = ?, UploadDate = ?
                WHERE AttachmentID = ?
                """;
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, attachment.getRegistrationID());
            ps.setString(2, attachment.getFileName());
            ps.setString(3, attachment.getFilePath());
            ps.setString(4, attachment.getFileType());
            ps.setInt(5, attachment.getFileSize());
            ps.setDate(6, new java.sql.Date(attachment.getUploadDate().getTime()));
            ps.setInt(7, attachment.getAttachmentID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int attachmentID) {
        String sql = "DELETE FROM Attachments WHERE AttachmentID = ?";
        try (PreparedStatement ps = DBContext.getInstance().getConnection().prepareStatement(sql)) {
            ps.setInt(1, attachmentID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Attachment createAttachmentFromResultSet(ResultSet rs) throws SQLException {
        Attachment attachment = new Attachment();
        attachment.setAttachmentID(rs.getInt("AttachmentID"));
        attachment.setRegistrationID(rs.getInt("RegistrationID"));
        attachment.setFileName(rs.getString("FileName"));
        attachment.setFilePath(rs.getString("FilePath"));
        attachment.setFileType(rs.getString("FileType"));
        attachment.setFileSize(rs.getInt("FileSize"));
        attachment.setUploadDate(rs.getDate("UploadDate"));
        return attachment;
    }
    //Add enable và disable nếu cần thiết
}