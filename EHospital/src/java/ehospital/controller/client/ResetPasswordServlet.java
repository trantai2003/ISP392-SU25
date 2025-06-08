package ehospital.controller.client;

import ehospital.DAO.UserDAO;
import ehospital.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/reset-password"})
public class ResetPasswordServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("/reset-password", this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        
        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.validateResetToken(token);
            
            if (user == null) {
                request.setAttribute("error", "Invalid or expired reset token. Please request a new password reset.");
                request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
                return;
            }
            
            // Forward to reset password page
            request.getRequestDispatcher("/client/reset-password.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred. Please try again later.");
            request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (token == null || token.trim().isEmpty()) {
            response.sendRedirect("login");
            return;
        }

        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/client/reset-password.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.validateResetToken(token);
            
            if (user == null) {
                request.setAttribute("error", "Invalid or expired reset token. Please request a new password reset.");
                request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
                return;
            }
            
            // Update password
            userDAO.updatePassword(user.getUserId(), password);
            
            // Redirect to login page with success message
            response.sendRedirect("login?success=Password has been reset successfully. Please login with your new password.");
            
        } catch (SQLException ex) {
            Logger.getLogger(ResetPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred while resetting your password. Please try again later.");
            request.getRequestDispatcher("/client/reset-password.jsp").forward(request, response);
        }
    }
} 