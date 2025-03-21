<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Notification"%>
<%@page import="java.util.List"%>
<%
    List<Notification> notificationList = (List<Notification>) request.getAttribute("notificationList");
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Thông báo</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 0;
            }
            .header {
                background-color: #2980b9;
                color: white;
                padding: 10px 0;
                text-align: center;
            }
            .navbar {
                overflow: hidden;
                background-color: #333;
                display: flex;
                justify-content: center;
            }
            .navbar a {
                display: block;
                color: white;
                text-align: center;
                padding: 14px 20px;
                text-decoration: none;
            }
            .navbar a:hover {
                background-color: #ddd;
                color: black;
            }
            .container {
                padding: 20px;
            }
            .notification-list {
                background-color: white;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 900px;
                margin: auto;
            }
            table {
                width: 100%;
                border-collapse: collapse;
            }
            th, td {
                padding: 10px;
                border: 1px solid #ddd;
                text-align: left;
            }
            th {
                background-color: #2980b9;
                color: white;
            }
            .btn-detail {
                background-color: #2980b9;
                color: white;
                padding: 5px 10px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }
            .btn-detail:hover {
                background-color: #1f618d;
            }
            .popup {
                display: none;
                position: fixed;
                top: 50%;
                left: 50%;
                transform: translate(-50%, -50%);
                width: 400px;
                background: white;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
                padding: 20px;
                border-radius: 5px;
                z-index: 1000;
            }
            .popup-content {
                text-align: left;
            }
            .popup .close {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 20px;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <h1>Thông báo</h1>
        </div>
        <div class="navbar">
            <a href="home">Trang chủ</a>
            <a href="citizen">Quản lý công dân</a>
            <a href="dontu">Quản lý đơn từ</a>
            <a href="manageRegistrations.jsp">Quản lý Đơn</a>
            <a href="thongbao">Thông báo</a>
            <a href="support.jsp">Hỗ trợ</a>
        </div>

        <div class="container">
            <div class="notification-list">
                <h2>Danh sách Thông báo</h2>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Người gửi</th> <!-- Hiển thị FullName thay vì UserID -->
                        <th>Nội dung</th>
                        <th>Ngày gửi</th>
                        <th>Trạng thái</th>
                        <th>Loại</th>
                        <th>Hành động</th>
                    </tr>
                    <% if (notificationList != null) { %>
                    <% for (Notification notification : notificationList) { %>
                    <tr>
                        <td><%= notification.getNotificationID() %></td>
                        <td><%= notification.getFullName() %></td> <!-- Hiển thị FullName -->
                        <td><%= notification.getMessage().length() > 50 ? notification.getMessage().substring(0, 50) + "..." : notification.getMessage() %></td>
                        <td><%= notification.getSentDate() %></td>
                        <td><%= notification.isRead() ? "Đã đọc" : "Chưa đọc" %></td>
                        <td><%= notification.getType() %></td>
                        <td>
                            <button class="btn-detail" onclick="showDetails('<%= notification.getNotificationID() %>')">Xem chi tiết</button>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="7">Không có thông báo</td></tr>
                    <% } %>
                </table>
            </div>
        </div>

        <!-- Popup hiển thị chi tiết -->
        <div id="notificationPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Chi tiết Thông báo</h2>
                <p><strong>ID:</strong> <span id="detailID"></span></p>
                <p><strong>Người gửi:</strong> <span id="detailSender"></span></p>
                <p><strong>Nội dung:</strong> <span id="detailMessage"></span></p>
                <p><strong>Ngày gửi:</strong> <span id="detailSentDate"></span></p>
                <p><strong>Trạng thái:</strong> <span id="detailStatus"></span></p>
                <p><strong>Loại:</strong> <span id="detailType"></span></p>
            </div>
        </div>

        <script>
            function showDetails(notificationID) {
            let notifications = {
            <%
                        if (notificationList != null) {
                            for (Notification notification : notificationList) {
            %>
            "<%= notification.getNotificationID() %>": {
            "id": "<%= notification.getNotificationID() %>",
                "sender": "<%= notification.getFullName() %>",
                "message": "<%= notification.getMessage() %>",
                "sentDate": "<%= notification.getSentDate() %>",
                "status": "<%= notification.isRead() ? "Đã đọc" : "Chưa đọc" %>",
                "type": "<%= notification.getType() %>"
            },
            <% 
                            }
                        }
            %>
            };
            let notification = notifications[notificationID];
            if (notification) {
            document.getElementById("detailID").textContent = notification.id;
            document.getElementById("detailSender").textContent = notification.sender;
            document.getElementById("detailMessage").textContent = notification.message;
            document.getElementById("detailSentDate").textContent = notification.sentDate;
            document.getElementById("detailStatus").textContent = notification.status;
            document.getElementById("detailType").textContent = notification.type;
            }

            document.getElementById("notificationPopup").style.display = "block";
            }

            function closePopup() {
            document.getElementById("notificationPopup").style.display = "none";
            }
        </script>
    </body>
</html>
