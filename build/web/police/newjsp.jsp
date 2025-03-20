<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User"%>
<%@page import="java.util.List"%>
<%
    List<User> userList = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Quản lý Công dân</title>
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
            .user-list {
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
            .btn {
                padding: 5px 10px;
                border: none;
                border-radius: 3px;
                cursor: pointer;
            }
            .btn-detail {
                background-color: #2980b9;
                color: white;
            }
            .btn-update {
                background-color: #f1c40f;
                color: black;
            }
            .btn-delete {
                background-color: #e74c3c;
                color: white;
            }
            .btn-add {
                background-color: #27ae60;
                color: white;
                margin-bottom: 10px;
            }
            .btn:hover {
                opacity: 0.8;
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
            .popup .close {
                position: absolute;
                top: 10px;
                right: 15px;
                font-size: 20px;
                cursor: pointer;
            }
            .popup form {
                display: flex;
                flex-direction: column;
            }
            .popup label {
                font-weight: bold;
                margin-top: 10px;
            }
            .popup input {
                padding: 5px;
                margin-top: 5px;
                border: 1px solid #ccc;
                border-radius: 3px;
            }
            .popup button {
                margin-top: 15px;
                padding: 8px;
                border: none;
                border-radius: 3px;
                background-color: #2980b9;
                color: white;
                cursor: pointer;
            }
        </style>
    </head>
    <body>
        <div class="header"><h1>Quản lý Công dân</h1></div>
        <div class="navbar">
            <a href="home">Trang chủ</a>
            <a href="citizen">Quản lý công dân</a>
            <a href="registerTemporaryResidence">Đăng ký tạm trú</a>
            <a href="registerTemporaryAbsence">Đăng ký tạm vắng</a>
            <a href="citizenApplications.jsp">Hồ sơ của tôi</a>
            <a href="thongbao">Thông báo</a>
            <a href="support.jsp">Hỗ trợ</a>
        </div>

        <div class="container">
            <div class="user-list">
                <button class="btn btn-add" onclick="openAddUserPopup()">Thêm User</button>
                <h2>Danh sách Công dân</h2>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Họ và Tên</th>
                        <th>Email</th>
                        <th>Vai trò</th>
                        <th>Phone</th>
                        <th>Hành động</th>
                    </tr>
                    <% if (userList != null) { %>
                    <% for (User user : userList) { %>
                    <tr>
                        <td><%= user.getUserID() %></td>
                        <td><%= user.getFullName() %></td>
                        <td><%= user.getEmail() %></td>
                        <td><%= user.getRole() %></td>
                        <td><%= user.getPhoneNumber() %></td>
                        <td>
                            <button class="btn btn-detail" onclick="showDetails('<%= user.getUserID() %>')">Xem</button>
                            <button class="btn btn-update" onclick="updateUser('<%= user.getUserID() %>')">Sửa</button>
                            <button class="btn btn-delete" onclick="deleteUser('<%= user.getUserID() %>')">Xóa</button>
                        </td>
                    </tr>
                    <% } %>
                    <% } else { %>
                    <tr><td colspan="6">Không có dữ liệu</td></tr>
                    <% } %>
                </table>
            </div>

        </div>
        <div id="userDetailPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Thông tin chi tiết</h2>
                <p><strong>ID:</strong> <span id="detailID"></span></p>
                <p><strong>Họ và Tên:</strong> <span id="detailName"></span></p>
                <p><strong>Email:</strong> <span id="detailEmail"></span></p>
                <p><strong>Vai trò:</strong> <span id="detailRole"></span></p>
                <p><strong>Phone:</strong> <span id="detailPhone"></span></p>
                <p><strong>Ngày sinh:</strong> <span id="detailDob"></span></p>
                <p><strong>Giới tính:</strong> <span id="detailGender"></span></p>
                <p><strong>Địa chỉ:</strong> <span id="detailAddress"></span></p>
                <p><strong>CCCD:</strong> <span id="detailCCCD"></span></p>
                <p><strong>Nơi cấp:</strong> <span id="detailPlaceOfIssue"></span></p>
                <p><strong>Ngày cấp:</strong> <span id="detailDateOfIssue"></span></p>
                <p><strong>Quốc tịch:</strong> <span id="detailNationality"></span></p>
                <p><strong>Dân tộc:</strong> <span id="detailEthnic"></span></p>
                <p><strong>Đạo:</strong> <span id="detailReligion"></span></p>
                <p><strong>Nghề nghiệp:</strong> <span id="detailOccupation"></span></p>
                <p><strong>Ngày phát hành:</strong> <span id="detailCreatedAt"></span></p>
                <p><strong>Ngày cập nhật:</strong> <span id="detailUpdatedAt"></span></p>
            </div>
        </div>
        <div id="userEditPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Chỉnh sửa thông tin</h2>
                <form id="editUserForm" method="post" action="updateUser">
                    <input type="hidden" id="editUserID" name="userID">
                    <div class="form-grid">
                        <div class="form-group">
                            <label>Họ và Tên:</label>
                            <input type="text" id="editName" name="fullName" required>
                        </div>
                        <div class="form-group">
                            <label>Email:</label>
                            <input type="email" id="editEmail" name="email" required>
                        </div>
                        <div class="form-group">
                            <label>Vai trò:</label>
                            <input type="text" id="editRole" name="role" required>
                        </div>
                        <div class="form-group">
                            <label>Phone:</label>
                            <input type="text" id="editPhone" name="phoneNumber" required>
                        </div>
                        <div class="form-group">
                            <label>Ngày sinh:</label>
                            <input type="date" id="editDob" name="dob">
                        </div>
                        <div class="form-group">
                            <label>Giới tính:</label>
                            <input type="text" id="editGender" name="gender">
                        </div>
                        <div class="form-group">
                            <label>Địa chỉ:</label>
                            <input type="text" id="editAddress" name="address">
                        </div>
                        <div class="form-group">
                            <label>CCCD:</label>
                            <input type="text" id="editCCCD" name="identityNumber">
                        </div>
                        <div class="form-group">
                            <label>Nơi cấp:</label>
                            <input type="text" id="editPlaceOfIssue" name="placeOfIssue">
                        </div>
                        <div class="form-group">
                            <label>Ngày cấp:</label>
                            <input type="date" id="editDateOfIssue" name="dateOfIssue">
                        </div>
                        <div class="form-group">
                            <label>Quốc tịch:</label>
                            <input type="text" id="editNationality" name="nationality">
                        </div>
                        <div class="form-group">
                            <label>Dân tộc:</label>
                            <input type="text" id="editEthnic" name="ethnic">
                        </div>
                        <div class="form-group">
                            <label>Đạo:</label>
                            <input type="text" id="editReligion" name="religion">
                        </div>
                        <div class="form-group">
                            <label>Nghề nghiệp:</label>
                            <input type="text" id="editOccupation" name="occupation">
                        </div>
                        <div class="form-group">
                            <label>Ngày phát hành:</label>
                            <input type="datetime-local" id="editCreatedAt" name="createdAt" readonly>
                        </div>
                        <div class="form-group">
                            <label>Ngày cập nhật:</label>
                            <input type="datetime-local" id="editUpdatedAt" name="updatedAt" readonly>
                        </div>
                    </div>
                    <button type="submit">Cập nhật</button>
                </form>
            </div>
        </div>


        <div id="addUserPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup('addUserPopup')">&times;</span>
                <h2>Thêm Người Dùng</h2>
                <form action="addUser" method="post">
                    <label>Họ và Tên:</label>
                    <input type="text" name="fullName" required><br>
                    <label>Email:</label>
                    <input type="email" name="email" required><br>
                    <label>Vai trò:</label>
                    <input type="text" name="role" required><br>
                    <label>Số điện thoại:</label>
                    <input type="text" name="phone" required><br>
                    <button type="submit">Thêm</button>
                </form>
            </div>
        </div>
        <div id="userEditPopup" class="popup">
            <div class="popup-content">
                <span class="close" onclick="closePopup()">&times;</span>
                <h2>Chỉnh sửa thông tin</h2>
                <form id="editUserForm" method="post" action="updateUser">
                    <input type="hidden" id="detailID" name="userID">
                    <label>Họ và Tên:</label>
                    <input type="text" id="detailName" name="fullName" required>
                    <label>Email:</label>
                    <input type="email" id="detailEmail" name="email" required>
                    <label>Vai trò:</label>
                    <input type="text" id="detailRole" name="role" required>
                    <label>Phone:</label>
                    <input type="text" id="detailPhone" name="phoneNumber" required>
                    <label>Ngày sinh:</label>
                    <input type="date" id="detailDob" name="dob">
                    <label>Giới tính:</label>
                    <input type="text" id="detailGender" name="gender">
                    <label>Địa chỉ:</label>
                    <input type="text" id="detailAddress" name="address">
                    <label>CCCD:</label>
                    <input type="text" id="detailCCCD" name="identityNumber">
                    <label>Nơi cấp:</label>
                    <input type="text" id="detailPlaceOfIssue" name="placeOfIssue">
                    <label>Ngày cấp:</label>
                    <input type="date" id="detailDateOfIssue" name="dateOfIssue">
                    <label>Quốc tịch:</label>
                    <input type="text" id="detailNationality" name="nationality">
                    <label>Dân tộc:</label>
                    <input type="text" id="detailEthnic" name="ethnic">
                    <label>Đạo:</label>
                    <input type="text" id="detailReligion" name="religion">
                    <label>Nghề nghiệp:</label>
                    <input type="text" id="detailOccupation" name="occupation">
                    <label>Ngày phát hành:</label>
                    <input type="datetime-local" id="detailCreatedAt" name="createdAt" readonly>
                    <label>Ngày cập nhật:</label>
                    <input type="datetime-local" id="detailUpdatedAt" name="updatedAt" readonly>
                    <button type="submit">Cập nhật</button>
                </form>
            </div>
        </div>

        <script>
            function showDetails(userID) {
            let users = {
            <%
                        if (userList != null) {
                            for (User user : userList) {
            %>
            "<%= user.getUserID() %>": {
            "id": "<%= user.getUserID() %>",
                    "name": "<%= user.getFullName() %>",
                    "email": "<%= user.getEmail() %>",
                    "role": "<%= user.getRole() %>",
                    "phone": "<%= user.getPhoneNumber() %>",
                    "dob": "<%= user.getDateOfBirth() %>",
                    "gender": "<%= user.getGender() %>",
                    "address": "<%= user.getAddress() %>",
                    "cccd": "<%= user.getIdentityNumber() %>",
                    "placeOfIssue": "<%= user.getPlaceOfIssue() %>",
                    "dateOfIssue": "<%= user.getDateOfIssue() %>",
                    "nationality": "<%= user.getNationality() %>",
                    "ethnic": "<%= user.getEthnic() %>",
                    "religion": "<%= user.getReligion() %>",
                    "occupation": "<%= user.getOccupation() %>",
                    "createdAt": "<%= user.getCreatedAt() %>",
                    "updatedAt": "<%= user.getUpdatedAt() %>"
            },
            <% 
                            }
                        }
            %>
            };
            let user = users[userID];
            if (user) {
            document.getElementById("detailID").textContent = user.id;
            document.getElementById("detailName").textContent = user.name;
            document.getElementById("detailEmail").textContent = user.email;
            document.getElementById("detailRole").textContent = user.role;
            document.getElementById("detailPhone").textContent = user.phone;
            document.getElementById("detailDob").textContent = user.dob;
            document.getElementById("detailGender").textContent = user.gender;
            document.getElementById("detailAddress").textContent = user.address;
            document.getElementById("detailCCCD").textContent = user.cccd;
            document.getElementById("detailPlaceOfIssue").textContent = user.placeOfIssue;
            document.getElementById("detailDateOfIssue").textContent = user.dateOfIssue;
            document.getElementById("detailNationality").textContent = user.nationality;
            document.getElementById("detailEthnic").textContent = user.ethnic;
            document.getElementById("detailReligion").textContent = user.religion;
            document.getElementById("detailOccupation").textContent = user.occupation;
            document.getElementById("detailCreatedAt").textContent = user.createdAt;
            document.getElementById("detailUpdatedAt").textContent = user.updatedAt;
            }

            document.getElementById("userDetailPopup").style.display = "block";
            }


            function updateUser(userID) {
            let users = {
            <%
        if (userList != null) {
            for (User user : userList) {
            %>
            "<%= user.getUserID() %>": {
            "id": "<%= user.getUserID() %>",
                    "name": "<%= user.getFullName() %>",
                    "email": "<%= user.getEmail() %>",
                    "role": "<%= user.getRole() %>",
                    "phone": "<%= user.getPhoneNumber() %>",
                    "dob": "<%= user.getDateOfBirth() %>",
                    "gender": "<%= user.getGender() %>",
                    "address": "<%= user.getAddress() %>",
                    "cccd": "<%= user.getIdentityNumber() %>",
                    "placeOfIssue": "<%= user.getPlaceOfIssue() %>",
                    "dateOfIssue": "<%= user.getDateOfIssue() %>",
                    "nationality": "<%= user.getNationality() %>",
                    "ethnic": "<%= user.getEthnic() %>",
                    "religion": "<%= user.getReligion() %>",
                    "occupation": "<%= user.getOccupation() %>",
                    "createdAt": "<%= user.getCreatedAt() %>",
                    "updatedAt": "<%= user.getUpdatedAt() %>"
            },
            <% 
            }
        }
            %>
            };
            let user = users[userID];
            if (user) {
            document.getElementById("editUserID").value = user.id;
            document.getElementById("editName").value = user.name;
            document.getElementById("editEmail").value = user.email;
            document.getElementById("editRole").value = user.role;
            document.getElementById("editPhone").value = user.phone;
            document.getElementById("editDob").value = user.dob;
            document.getElementById("editGender").value = user.gender;
            document.getElementById("editAddress").value = user.address;
            document.getElementById("editCCCD").value = user.cccd;
            document.getElementById("editPlaceOfIssue").value = user.placeOfIssue;
            document.getElementById("editDateOfIssue").value = user.dateOfIssue;
            document.getElementById("editNationality").value = user.nationality;
            document.getElementById("editEthnic").value = user.ethnic;
            document.getElementById("editReligion").value = user.religion;
            document.getElementById("editOccupation").value = user.occupation;
            document.getElementById("editCreatedAt").value = user.createdAt;
            document.getElementById("editUpdatedAt").value = user.updatedAt;
            }

            document.getElementById("userEditPopup").style.display = "block";
            }


            function deleteUser(userID) {
            if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
            window.location.href = "deleteUser?id=" + userID;
            }
            }
            function openAddUserPopup() {
            document.getElementById("addUserPopup").style.display = "block";
            }
            function closePopup(popupId) {
            document.getElementById(popupId).style.display = "none";
            }

// Đóng popup sửa thông tin khi nhấn vào dấu X
            document.querySelectorAll('.close').forEach(item => {
            item.addEventListener('click', function() {
            document.getElementById("userDetailPopup").style.display = "none";
            document.getElementById("userEditPopup").style.display = "none"; // Đóng luôn popup sửa
            });
            });
// Đóng popup khi nhấn ra ngoài
            window.onclick = function(event) {
            let detailPopup = document.getElementById("userDetailPopup");
            let editPopup = document.getElementById("userEditPopup");
            if (event.target === detailPopup) {
            detailPopup.style.display = "none";
            }
            if (event.target === editPopup) {
            editPopup.style.display = "none";
            }
            };


        </script>
    </body>
</html>