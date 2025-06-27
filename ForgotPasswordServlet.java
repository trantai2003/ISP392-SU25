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
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;
import java.util.Date;

@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-password"})
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute("/forgot-password", this);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        if (request.getSession(false) != null && request.getSession().getAttribute("userAuth") != null) {
            response.sendRedirect("home");
            return;
        }
        
        // Forward to forgot password page
        request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");

        try {
            UserDAO userDAO = new UserDAO();
            
            // Check if email exists
            if (!userDAO.isEmailExists(email)) {
                request.setAttribute("error", "No account found with this email address");
                request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
                return;
            }

            // Generate reset token
            String resetToken = UUID.randomUUID().toString();
            Date expiryDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour from now

            // Save reset token to database
            userDAO.saveResetToken(email, resetToken, expiryDate);

            // Send reset email
            sendResetEmail(email, resetToken);

            request.setAttribute("success", "Password reset link has been sent to your email");
            request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ForgotPasswordServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred. Please try again later.");
            request.getRequestDispatcher("/client/forgot-password.jsp").forward(request, response);
        }
    }

    private void sendResetEmail(String toEmail, String resetToken) throws ServletException {
        // Email configuration
        final String fromEmail = "caoanhtai05032004@gmail.com"; // Replace with your email
        final String password = "dlhe nquw vush lxpw"; // Replace with your app password

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset Request - E-Hospital");

            String resetLink = "http://localhost:9999/EHospital/reset-password?token=" + resetToken;
            String emailContent = "Dear User,\n\n"
                    + "You have requested to reset your password. Click the link below to reset your password:\n\n"
                    + resetLink + "\n\n"
                    + "This link will expire in 1 hour.\n\n"
                    + "If you did not request this, please ignore this email.\n\n"
                    + "Best regards,\nE-Hospital Team";

            message.setText(emailContent);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new ServletException("Error sending email", e);
        }
    }
} 