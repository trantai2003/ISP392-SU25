package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Patient;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientDAO extends GenericDAO<Patient, Integer> {
    public PatientDAO() {
        super(Patient.class);
    }
    
    public Patient getPatientByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM Patients WHERE UserID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setUserId(rs.getInt("UserID"));
                    patient.setPatientCode(rs.getString("PatientCode"));
                    patient.setDateOfBirth(rs.getDate("DateOfBirth"));
                    patient.setGender(rs.getString("Gender"));
                    patient.setAddress(rs.getString("Address"));
                    patient.setBloodType(rs.getString("BloodType"));
                    patient.setAllergies(rs.getString("Allergies"));
                    patient.setEmergencyContact(rs.getString("EmergencyContact"));
                    patient.setEmergencyPhone(rs.getString("EmergencyPhone"));
                    patient.setChronicDiseases(rs.getString("ChronicDiseases"));
                    return patient;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
        return null;
    }
    
    public boolean isPatientExists(int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Patients WHERE UserID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }
        
        return false;
    }
   
}