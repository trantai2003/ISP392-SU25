package ehospital.DAO;
import ehospital.JDBC.GenericDAO;
import ehospital.entities.Examination;
import ehospital.dto.ExaminationDTO;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExaminationDAO extends GenericDAO<Examination, Integer> {
    private static final Logger LOGGER = Logger.getLogger(ExaminationDAO.class.getName());

    public ExaminationDAO() {
        super(Examination.class);
    }

    public ExaminationDTO getExaminationByAppointmentId(int appointmentId) throws SQLException {
        String sql = "SELECT e.*, d.FullName as DoctorName, " +
                    "CONVERT(varchar, e.ExamDate, 103) as FormattedDate " +
                    "FROM Examinations e " +
                    "JOIN Users d ON e.DoctorID = d.UserID " +
                    "WHERE e.AppointmentID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, appointmentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ExaminationDTO examination = new ExaminationDTO();
                    examination.setExaminationId(rs.getInt("ExamID"));
                    examination.setAppointmentId(rs.getInt("AppointmentID"));
                    examination.setExaminationDate(rs.getDate("ExamDate"));
                    examination.setFormattedDate(rs.getString("FormattedDate"));
                    examination.setDoctorId(rs.getInt("DoctorID"));
                    examination.setDoctorName(rs.getString("DoctorName"));
                    examination.setSymptoms(rs.getString("Symptoms"));
                    examination.setDiagnosis(rs.getString("Diagnosis"));
                    examination.setVitalSigns(rs.getString("VitalSigns"));
                    examination.setTreatment(rs.getString("Treatment"));
                    examination.setNotes(rs.getString("Notes"));
                    examination.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    
                    return examination;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting examination by appointment ID", ex);
            throw ex;
        }
        
        return null;
    }
    
    
     
    
    
    // Tìm kiếm examinations
    public List<Examination> searchExaminations(String keyword) {
        List<Examination> examinations = new ArrayList<>();
        String sql = """
            SELECT e.*, 
                   p.PatientCode, u1.FullName as PatientName,
                   emp.EmployeeCode, u2.FullName as DoctorName
            FROM Examinations e
            LEFT JOIN PatientRecords pr ON e.RecordID = pr.RecordID
            LEFT JOIN Patients p ON pr.PatientID = p.PatientID
            LEFT JOIN Users u1 ON p.UserID = u1.UserID
            LEFT JOIN Employees emp ON e.DoctorID = emp.EmployeeID
            LEFT JOIN Users u2 ON emp.UserID = u2.UserID
            WHERE u1.FullName LIKE ? OR u2.FullName LIKE ? OR e.Diagnosis LIKE ?
            ORDER BY e.ExamDate DESC
            """;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            stmt.setString(3, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Examination exam = Examination.builder()
                        .examId(rs.getInt("ExamID"))
                        .appointmentId(rs.getObject("AppointmentID") != null ? rs.getInt("AppointmentID") : null)
                        .examDate(rs.getTimestamp("ExamDate"))
                        .doctorId(rs.getInt("DoctorID"))
                        .symptoms(rs.getString("Symptoms"))
                        .diagnosis(rs.getString("Diagnosis"))
                        .vitalSigns(rs.getString("VitalSigns"))
                        .treatment(rs.getString("Treatment"))
                        .notes(rs.getString("Notes"))
                        .createdAt(rs.getTimestamp("CreatedAt"))
                        .build();
                    
                    examinations.add(exam);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return examinations;
    }
    
    // Lấy danh sách bác sĩ cho dropdown
    public List<Object[]> getAllDoctors() {
        List<Object[]> doctors = new ArrayList<>();
        String sql = """
            SELECT e.EmployeeID, u.FullName, d.DepartmentName
            FROM Employees e
            JOIN Users u ON e.UserID = u.UserID
            JOIN UserRoles ur ON u.UserID = ur.UserID
            JOIN Roles r ON ur.RoleID = r.RoleID  
            LEFT JOIN Departments d ON e.DepartmentID = d.DepartmentID
            WHERE r.RoleName = 'Doctor' AND e.IsActive = 1
            ORDER BY u.FullName
            """;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                doctors.add(new Object[]{
                    rs.getInt("EmployeeID"),
                    rs.getString("FullName"),
                    rs.getString("DepartmentName")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }
    
    // Lấy danh sách patient records cho dropdown
    public List<Object[]> getAllPatientRecords() {
        List<Object[]> records = new ArrayList<>();
        String sql = """
            SELECT pr.RecordID, u.FullName, p.PatientCode, pr.AdmissionDate
            FROM PatientRecords pr
            JOIN Patients p ON pr.PatientID = p.PatientID
            JOIN Users u ON p.UserID = u.UserID
            WHERE pr.DischargeDate IS NULL
            ORDER BY pr.AdmissionDate DESC
            """;
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                records.add(new Object[]{
                    rs.getInt("RecordID"),
                    rs.getString("FullName"),
                    rs.getString("PatientCode"),
                    rs.getDate("AdmissionDate")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }
}