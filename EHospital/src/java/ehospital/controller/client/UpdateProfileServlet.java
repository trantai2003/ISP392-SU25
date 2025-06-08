package ehospital.controller.client;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ehospital.DAO.PatientDAO;
import ehospital.DAO.UserDAO;
import ehospital.entities.Patient;
import ehospital.entities.User;

@WebServlet(name = "UpdateProfileServlet", urlPatterns = {"/update-profile"})
public class UpdateProfileServlet extends HttpServlet {

    private UserDAO userDAO;
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
        patientDAO = new PatientDAO();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("userAuth") == null) {
            response.sendRedirect("login");
            return;
        }

        // Get user ID from session
        int userId = (int) session.getAttribute("userId");

        try {
            // Get current user and patient information
            User user = userDAO.getById(userId);
            Patient patient = patientDAO.getPatientByUserId(userId);

            request.setAttribute("user", user);
            request.setAttribute("patient", patient);

            // Forward to update profile page
            request.getRequestDispatcher("client/update-profile.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred while loading profile information.");
            request.getRequestDispatcher("client/update-profile.jsp").forward(request, response);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Check if user is logged in
        if (session.getAttribute("userAuth") == null) {
            response.sendRedirect("login");
            return;
        }

        // Get user ID from session
        int userId = (int) session.getAttribute("userId");

        try {
            // Get current user to preserve password hash
            User currentUser = userDAO.getById(userId);

            // Get form data
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            // Get patient form data
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String address = request.getParameter("address");
            String bloodType = request.getParameter("bloodType");
            String allergies = request.getParameter("allergies");
            String emergencyContact = request.getParameter("emergencyContact");
            String emergencyPhone = request.getParameter("emergencyPhone");
            String patientCode = request.getParameter("patientCode");
            String chronicDiseases = request.getParameter("chronicDiseases");

            // Validate required fields
            if (username == null || username.trim().isEmpty()
                    || fullName == null || fullName.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()) {
                request.setAttribute("error", "Please fill in all required fields.");
                doGet(request, response);
                return;
            }

            // Check if username or email already exists (excluding current user)
            if (userDAO.isUsernameExists(username, userId) && !username.equals(session.getAttribute("username"))) {
                request.setAttribute("error", "Username already exists.");
                doGet(request, response);
                return;
            }

            if (userDAO.isEmailExists(email, userId) && !email.equals(session.getAttribute("email"))) {
                request.setAttribute("error", "Email already exists.");
                doGet(request, response);
                return;
            }

            // Update user information
            User user = new User();
            user.setUserId(userId);
            user.setUsername(username);
            user.setFullName(fullName);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRoleId(3); // Client role
            user.setIsActive(Boolean.TRUE);
            user.setPasswordHash(currentUser.getPasswordHash()); // Preserve password hash
            user.setCreatedAt(currentUser.getCreatedAt()); // Preserve creation date

            userDAO.update(user);

            // Check if patient record exists
            boolean patientExists = patientDAO.isPatientExists(userId);

            // Create or update patient information
            Patient patient = new Patient();
            patient.setUserId(userId);

            // Parse date of birth
            if (dateOfBirthStr != null && !dateOfBirthStr.trim().isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dateOfBirth = dateFormat.parse(dateOfBirthStr);
                patient.setDateOfBirth(new java.sql.Date(dateOfBirth.getTime()));
            }

            patient.setGender(gender);
            patient.setAddress(address);
            patient.setBloodType(bloodType);
            patient.setAllergies(allergies);
            patient.setEmergencyContact(emergencyContact);
            patient.setEmergencyPhone(emergencyPhone);
            patient.setChronicDiseases(chronicDiseases);

            // Create or update patient record
            if (!patientExists) {
                System.out.println("insert");
                patient.setPatientCode(generatePatientCodeByTimestamp());

                patientDAO.insert(patient);
            } else {
                System.out.println("update");
                patient.setPatientCode(patientCode);

                patientDAO.update(patient);
            }

            // Update session attributes
            session.setAttribute("username", username);
            session.setAttribute("fullName", fullName);
            session.setAttribute("email", email);
            session.setAttribute("phone", phone);

            // Redirect to profile page with success message
            response.sendRedirect("profile?success=Profile updated successfully");

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(UpdateProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("error", "An error occurred while updating profile.");
            doGet(request, response);
        }
    }

    public static String generatePatientCodeByTimestamp() {
        String prefix = "PTN";
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        return prefix + timestamp;
    }
}
