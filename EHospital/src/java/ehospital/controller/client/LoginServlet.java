package ehospital.controller.client;

import ehospital.DAO.UserDAO;
import ehospital.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("/client-login", this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("userAuth") != null) {
            response.sendRedirect("home");
            return;
        }
        
        // Redirect to login page
        request.getRequestDispatcher("/client/login.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.login(username, password, 3); // 3 is for client role

            if (user != null) {
                user.setRoleFunc();
                // Create session and store user information
                HttpSession session = request.getSession();
                session.setAttribute("userAuth", user); 
                session.setAttribute("role", user.getRole().getRoleName());
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("fullName", user.getFullName());

                // Redirect to home page
                response.sendRedirect("home");
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("/client/login.jsp").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/client/login.jsp").forward(request, response);
        }
    }
}
