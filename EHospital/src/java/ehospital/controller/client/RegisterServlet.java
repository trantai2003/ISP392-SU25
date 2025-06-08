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

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("/register", this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        if (request.getSession(false) != null && request.getSession().getAttribute("userAuth") != null) {
            response.sendRedirect("home");
            return;
        }
        
        // Forward to registration page
        request.getRequestDispatcher("/client/register.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        // Validate form data
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty()) {
            
            request.setAttribute("error", "All fields are required");
            request.getRequestDispatcher("/client/register.jsp").forward(request, response);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/client/register.jsp").forward(request, response);
            return;
        }

        try {
            UserDAO userDAO = new UserDAO();
            
            // Check if username already exists
            if (userDAO.isUsernameExists(username)) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
                return;
            }

            // Check if email already exists
            if (userDAO.isEmailExists(email)) {
                request.setAttribute("error", "Email already registered");
                request.getRequestDispatcher("/client/register.jsp").forward(request, response);
                return;
            }

            // Create new user
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(password); // In production, use proper password hashing
            newUser.setFullName(fullName);
            newUser.setEmail(email);
            newUser.setPhone(phone);
            newUser.setRoleId(3); // 3 is for client role
            newUser.setIsActive(true);

            // Save user to database
            userDAO.insert(newUser);

            // Set success message and redirect to login
            request.setAttribute("success", "Registration successful! Please login.");
            response.sendRedirect("login");
            
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("/client/register.jsp").forward(request, response);
        }
    }
} 