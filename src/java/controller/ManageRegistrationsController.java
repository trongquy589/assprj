/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.RegistrationDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Registration;
import dao.NotificationsDAO;
import model.Notification;

/**
 *
 * @author troqu
 */
@WebServlet(name = "ManageRegistrationsController", urlPatterns = {"/dontu"})
public class ManageRegistrationsController extends HttpServlet {

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
            out.println("<title>Servlet ManageRegistrationsController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ManageRegistrationsController at " + request.getContextPath() + "</h1>");
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
        RegistrationDAO registrationDAO = new RegistrationDAO();
        List<Registration> registrationList = registrationDAO.getAll();

        request.setAttribute("registrationList", registrationList);
//        PrintWriter out= response.getWriter();
//        out.print(employeeList.size()+1);
        request.getRequestDispatcher("police/manageRegistrations.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String registrationIDStr = request.getParameter("id");
        int registrationID = 0;

        if (registrationIDStr != null && !registrationIDStr.isEmpty()) {
            try {
                registrationID = Integer.parseInt(registrationIDStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.getWriter().write("{\"success\": false, \"message\": \"ID không hợp lệ\"}");
                return;
            }
        } else {
            response.getWriter().write("{\"success\": false, \"message\": \"Thiếu ID\"}");
            return;
        }

        RegistrationDAO registrationDAO = new RegistrationDAO();
        NotificationsDAO notificationsDAO = new NotificationsDAO();
        boolean success = false;
        String message = "";

        if ("accept".equals(action)) {
            success = registrationDAO.updateStatus(registrationID, "Approved");
            if (success) {
                Notification notification = new Notification();
                notification.setMessage("Your registration ID "+ registrationID+ " has been approved.");

                String userIDStr = request.getParameter("userID");

                int userID = 0;

                if (userIDStr != null && !userIDStr.isEmpty()) {
                    try {
                        userID = Integer.parseInt(userIDStr);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        response.getWriter().write("{\"success\": false, \"message\": \"UserID không hợp lệ\"}");
                        return;
                    }
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thiếu UserID\"}");
                    return;
                }

                notification.setUserID(userID);
                notification.setSentDate(new java.util.Date());
                notification.setRead(false);
                notification.setType("Registration");
                notification.setReferenceID(registrationID);
                success = notificationsDAO.add(notification);
            }
            message = success ? "Đơn đã được chấp nhận!" : "Cập nhật thất bại!";
        } else if ("reject".equals(action)) {
            success = registrationDAO.updateStatus(registrationID, "Rejected");

            if (success) {
                String userIDStr = request.getParameter("userID");
                int userID = 0;

                if (userIDStr != null && !userIDStr.isEmpty()) {
                    try {
                        userID = Integer.parseInt(userIDStr);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        response.getWriter().write("{\"success\": false, \"message\": \"UserID không hợp lệ\"}");
                        return;
                    }
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"Thiếu UserID\"}");
                    return;
                }

                Notification notification = new Notification();
                notification.setUserID(userID);
                notification.setMessage("Your registration ID "+ registrationID+ " has been rejected.");
                notification.setSentDate(new java.util.Date());
                notification.setRead(false);
                notification.setType("Registration"); // Dùng giá trị hợp lệ từ CHECK constraint
                notification.setReferenceID(registrationID);

                success = notificationsDAO.add(notification);
            }

            message = success ? "Đơn đã bị từ chối!" : "Cập nhật thất bại!";
        }

        response.setContentType("application/json");
        response.getWriter().write("{\"success\": " + success + ", \"message\": \"" + message + "\"}");
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
