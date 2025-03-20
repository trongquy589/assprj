<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Registration"%>
<%@page import="java.util.List"%>
<%
    List<Registration> registrationList = (List<Registration>) request.getAttribute("registrationList");
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Đơn</title>
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
            .registration-list {
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
                width: 500px;
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
            .status-approved {
                color: white;
                background-color: #27ae60; /* Xanh lá */
                padding: 5px 10px;
                border-radius: 5px;
            }

            .status-rejected {
                color: white;
                background-color: #e74c3c; /* Đỏ */
                padding: 5px 10px;
                border-radius: 5px;
            }

            .status-pending {
                color: white;
                background-color: #f1c40f; /* Vàng */
                padding: 5px 10px;
                border-radius: 5px;
            }

            .btn-accept {
                background-color: #27ae60; /* Xanh lá */
                color: white;
                padding: 5px 10px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }

            .btn-accept:hover {
                background-color: #219150;
            }

            .btn-reject {
                background-color: #e74c3c; /* Đỏ */
                color: white;
                padding: 5px 10px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }

            .btn-reject:hover {
                background-color: #c0392b;
            }

        </style>
    </head>
    <body>
        <div class="header">
            <h1>Quản lý Đơn</h1>
        </div>
        <div class="navbar">
            <a href="home">Trang chủ</a>
            <a href="citizen">Quản lý công dân</a>
            <a href="dontu">Quản lý đơn từ</a>
            <a href="registerTemporaryAbsence">Đăng ký tạm vắng</a>
            <a href="manageRegistrations.jsp">Quản lý Đơn</a>
            <a href="notifications.jsp">Thông báo</a>
            <a href="support.jsp">Hỗ trợ</a>
        </div>

        <div class="container">
            <div class="registration-list">
                <h2>Danh sách Đơn</h2>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Người đăng ký</th>
                        <th>Loại đăng ký</th>
                        <th>Ngày bắt đầu</th>
                        <th>Ngày kết thúc</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    <% if (registrationList != null) { %>
                    <% for (Registration reg : registrationList) { %>
                    <tr>
                        <td><%= reg.getRegistrationID() %></td>
                        <td><%= reg.getUserFullName() %></td> 
                        <td><%= reg.getRegistrationType() %></td>
                        <td><%= reg.getStartDate() %></td>
                        <td><%= reg.getEndDate() %></td>
                        <td>
                            <% String statusClass = "status-pending"; // Mặc định màu vàng
                               if ("Approved".equals(reg.getStatus())) {
                                   statusClass = "status-approved"; // Xanh lá
                               } else if ("Rejected".equals(reg.getStatus())) {
                                   statusClass = "status-rejected"; // Đỏ
                               }
                            %>
                            <span class="<%= statusClass %>"><%= reg.getStatus() %></span>
                        </td>

                        <td>
                            <button class="btn-detail" onclick="showDetails('<%= reg.getRegistrationID() %>')">Xem</button>
                            <button class="btn-accept" onclick="acceptRegistration('<%= reg.getRegistrationID() %>')">Chấp nhận</button>
                            <button class="btn-reject" onclick="rejectRegistration('<%= reg.getRegistrationID() %>')">Từ chối</button>
                        </td>

                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="7">Không có đơn nào</td></tr>
                    <% } %>
                </table>
            </div>
        </div>

        <!-- Popup hiển thị chi tiết -->
        <div id="registrationPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Chi tiết Đơn</h2>
                <p><strong>ID:</strong> <span id="detailID"></span></p>
                <p><strong>Người đăng ký:</strong> <span id="detailUser"></span></p>
                <p><strong>Loại đăng ký:</strong> <span id="detailType"></span></p>
                <p><strong>Ngày bắt đầu:</strong> <span id="detailStartDate"></span></p>
                <p><strong>Ngày kết thúc:</strong> <span id="detailEndDate"></span></p>
                <p><strong>Lý do:</strong> <span id="detailReason"></span></p>
                <p><strong>Trạng thái:</strong> <span id="detailStatus"></span></p>
                <p><strong>Người duyệt:</strong> <span id="detailApprovedBy"></span></p>
                <p><strong>Ngày duyệt:</strong> <span id="detailApprovalDate"></span></p>
                <p><strong>Ghi chú:</strong> <span id="detailComments"></span></p>
            </div>
        </div>

        <script>
            function acceptRegistration(registrationID) {
            if (confirm("Bạn có chắc chắn muốn chấp nhận đơn này?")) {
            fetch('dontu?action=accept&id=' + registrationID, {
            method: 'POST'
            })
                    .then(response => response.json())
                    .then(data => {
                    alert(data.message);
                    location.reload(); // Làm mới trang sau khi cập nhật
                    })
                    .catch(error => console.error('Lỗi:', error));
            }
            }

            function rejectRegistration(registrationID) {
            if (confirm("Bạn có chắc chắn muốn từ chối đơn này?")) {
            fetch('dontu?action=reject&id=' + registrationID, {
            method: 'POST'
            })
                    .then(response => response.json())
                    .then(data => {
                    alert(data.message);
                    location.reload(); // Làm mới trang sau khi cập nhật
                    })
                    .catch(error => console.error('Lỗi:', error));
            }
            }

            function showDetails(registrationID) {
            let registrations = {
            <%
                        if (registrationList != null) {
                            for (Registration reg : registrationList) {
            %>
            "<%= reg.getRegistrationID() %>": {
            "id": "<%= reg.getRegistrationID() %>",
                    "user": "<%= reg.getUserFullName() %>",
                    "type": "<%= reg.getRegistrationType() %>",
                    "startDate": "<%= reg.getStartDate() %>",
                    "endDate": "<%= reg.getEndDate() %>",
                    "reason": "<%= reg.getReason() %>",
                    "status": "<%= reg.getStatus() %>",
                    "approvedBy": "<%= reg.getApprovedBy() %>",
                    "approvalDate": "<%= reg.getApprovalDate() %>",
                    "comments": "<%= reg.getComments() %>"
            },
            <% 
                            }
                        }
            %>
            };
            let registration = registrations[registrationID];
            if (registration) {
            document.getElementById("detailID").textContent = registration.id;
            document.getElementById("detailUser").textContent = registration.user;
            document.getElementById("detailType").textContent = registration.type;
            document.getElementById("detailStartDate").textContent = registration.startDate;
            document.getElementById("detailEndDate").textContent = registration.endDate;
            document.getElementById("detailReason").textContent = registration.reason;
            document.getElementById("detailStatus").textContent = registration.status;
            document.getElementById("detailApprovedBy").textContent = registration.approvedBy;
            document.getElementById("detailApprovalDate").textContent = registration.approvalDate;
            document.getElementById("detailComments").textContent = registration.comments;
            }

            document.getElementById("registrationPopup").style.display = "block";
            }

            function closePopup() {
            document.getElementById("registrationPopup").style.display = "none";
            }


        </script>
    </body>
</html>
