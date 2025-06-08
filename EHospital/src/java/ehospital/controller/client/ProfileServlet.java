package ehospital.controller.client;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ehospital.DAO.PatientDAO;
import ehospital.entities.Patient;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {
    private PatientDAO patientDAO;

    @Override
    public void init() throws ServletException {
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
        
        // Get patient information
        Patient patient = new Patient();
        try {
            patient = patientDAO.getPatientByUserId(userId);
        } catch (SQLException ex) {
            Logger.getLogger(ProfileServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Set patient information in request
        request.setAttribute("patient", patient);
        
        // Forward to profile page
        request.getRequestDispatcher("client/profile.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 