package ehospital.controller;

import ehospital.DAO.UserDAO;
import ehospital.entities.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "ChangePasswordController", urlPatterns = {"/change-password"})
public class ChangePasswordController extends HttpServlet {

    private static final String ERROR = "./client/error.jsp";
    private static final String SUCCESS = "./client/change-password.jsp";
    private static final String LOGIN = "./client/login.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            User loginUser = (User) session.getAttribute("userAuth");
            if (loginUser == null) {
                url = LOGIN;
            } else {
                String action = request.getParameter("action");
                if (action == null) {
                    url = SUCCESS;
                } else if (action.equals("change")) {
                    String oldPassword = request.getParameter("oldPassword");
                    String newPassword = request.getParameter("newPassword");
                    String confirmPassword = request.getParameter("confirmPassword");

                    UserDAO userDAO = new UserDAO();

                    // Validate old password
                    if (!userDAO.checkPassword(loginUser.getUserId(), oldPassword)) {
                        request.setAttribute("ERROR", "Current password is incorrect!");
                        url = SUCCESS;
                    } // Validate new password
                    else if (!newPassword.equals(confirmPassword)) {
                        request.setAttribute("ERROR", "New password and confirm password do not match!");
                        url = SUCCESS;
                    } // Validate password length
                    else if (newPassword.length() < 6) {
                        request.setAttribute("ERROR", "New password must be at least 6 characters long!");
                        url = SUCCESS;
                    } else if (oldPassword.equals(newPassword)) {
                        request.setAttribute("ERROR", "New password cannot be the same as the current password!");
                        url = SUCCESS;
                    } // Change password
                    else {
                        boolean check = userDAO.changePassword(loginUser.getUserId(), newPassword);
                        if (check) {
                            request.setAttribute("SUCCESS", "Password changed successfully!");
                        } else {
                            request.setAttribute("ERROR", "Failed to change password!");
                        }
                        url = SUCCESS;
                    }
                }
            }
        } catch (Exception e) {
            log("Error at ChangePasswordController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
