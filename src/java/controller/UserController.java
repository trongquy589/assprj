/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import dao.UserDAO;
import java.security.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author troqu
 */
@WebServlet(name = "UserController", urlPatterns = {"/citizen"})
public class UserController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getAll();

        request.setAttribute("userList", userList);
//        PrintWriter out= response.getWriter();
//        out.print(employeeList.size()+1);
        request.getRequestDispatcher("police/ListUserPage.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("add".equals(action)) {
            try {
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String role = request.getParameter("role");
                String phone = request.getParameter("phone");
                String identityNumber = request.getParameter("identityNumber");
                String dobStr = request.getParameter("dob");
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                String placeOfIssue = request.getParameter("placeOfIssue");
                String dateOfIssueStr = request.getParameter("dateOfIssue");
                String nationality = request.getParameter("nationality");
                String ethnic = request.getParameter("ethnic");
                String religion = request.getParameter("religion");
                String occupation = request.getParameter("occupation");

                // Chuyển đổi ngày từ String sang java.sql.Date
                java.sql.Date dateOfBirth = null;
                java.sql.Date dateOfIssue = null;
                if (dobStr != null && !dobStr.isEmpty()) {
                    try {
                        dateOfBirth = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (dateOfIssueStr != null && !dateOfIssueStr.isEmpty()) {
                    try {
                        dateOfIssue = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfIssueStr).getTime());
                    } catch (ParseException ex) {
                        Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                // Tạo đối tượng User
                User user = new User();
                user.setFullName(fullName);
                user.setEmail(email);
                user.setPassword("123456"); // Default password, có thể thay đổi sau
                user.setRole(role);
                user.setPhoneNumber(phone);
                user.setIdentityNumber(identityNumber);
                user.setDateOfBirth(dateOfBirth);
                user.setGender(gender);
                user.setAddress(address);
                user.setPlaceOfIssue(placeOfIssue);
                user.setDateOfIssue(dateOfIssue);
                user.setNationality(nationality);
                user.setEthnic(ethnic);
                user.setReligion(religion);
                user.setOccupation(occupation);
                user.setActive(true);
                user.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

                // Thêm vào DB
                UserDAO userDAO = new UserDAO();
                boolean success = userDAO.add(user);

            } catch (Exception ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if ("update".equals(action)) {
            try {
                int userID = Integer.parseInt(request.getParameter("userID"));
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String role = request.getParameter("role");
                String phone = request.getParameter("phoneNumber");
                String identityNumber = request.getParameter("identityNumber");
                String dobStr = request.getParameter("dob");
                String gender = request.getParameter("gender");
                String address = request.getParameter("address");
                String placeOfIssue = request.getParameter("placeOfIssue");
                String dateOfIssueStr = request.getParameter("dateOfIssue");
                String nationality = request.getParameter("nationality");
                String ethnic = request.getParameter("ethnic");
                String religion = request.getParameter("religion");
                String occupation = request.getParameter("occupation");

                java.sql.Date dateOfBirth = null;
                java.sql.Date dateOfIssue = null;
                if (dobStr != null && !dobStr.isEmpty()) {
                    dateOfBirth = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dobStr).getTime());
                }
                if (dateOfIssueStr != null && !dateOfIssueStr.isEmpty()) {
                    dateOfIssue = new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(dateOfIssueStr).getTime());
                }

                // Kiểm tra xem user có tồn tại không trước khi cập nhật
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getOne(userID);
                if (user == null) {
                    request.setAttribute("errorMessage", "User không tồn tại!");
                    request.getRequestDispatcher("citizen.jsp").forward(request, response);
                    return;
                }

                // Cập nhật thông tin
                user.setFullName(fullName);
                user.setEmail(email);
                user.setRole(role);
                user.setPhoneNumber(phone);
                user.setIdentityNumber(identityNumber);
                user.setDateOfBirth(dateOfBirth);
                user.setGender(gender);
                user.setAddress(address);
                user.setPlaceOfIssue(placeOfIssue);
                user.setDateOfIssue(dateOfIssue);
                user.setNationality(nationality);
                user.setEthnic(ethnic);
                user.setReligion(religion);
                user.setOccupation(occupation);
                user.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));

                // Gọi hàm update trong UserDAO
                boolean success = userDAO.update(user);

                if (success) {
                    response.sendRedirect("citizen");
                } else {
                    request.setAttribute("errorMessage", "Cập nhật thất bại!");
                    request.getRequestDispatcher("citizen.jsp").forward(request, response);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                request.setAttribute("errorMessage", "Lỗi hệ thống khi cập nhật!");
                request.getRequestDispatcher("citizen.jsp").forward(request, response);
            }
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
