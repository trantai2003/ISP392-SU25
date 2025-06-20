package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.dto.AppointmentDTO;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import ehospital.entities.Appointment;
import ehospital.entities.Clinic;
import ehospital.entities.DoctorNotification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppointmentDAO extends GenericDAO<Appointment, Integer>{
    private static final Logger LOGGER = Logger.getLogger(AppointmentDAO.class.getName());

  public AppointmentDAO() {
        super(Appointment.class);
    }

    public boolean createAppointment(AppointmentDTO appointment) throws SQLException {
        String sql = "INSERT INTO Appointments (PatientID, DoctorID, AppointmentDate, Status, Notes, " +
                    "CreatedAt, DepartmentID, TimeFrame) " +
                    "VALUES (?, ?, ?, 1, ?, GETDATE(), ?, ?)"; // Status 1 = Pending

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, appointment.getPatientId());
            ps.setInt(2, appointment.getDoctorId());
            ps.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            ps.setString(4, appointment.getNotes());
            ps.setInt(5, appointment.getDepartmentId());
            ps.setInt(6, appointment.getTimeFrame());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error creating appointment", ex);
            throw ex;
        }
    }

    public boolean isTimeSlotAvailable(int doctorId, java.util.Date appointmentDate, int timeFrame) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Appointments " +
                    "WHERE DoctorID = ? AND AppointmentDate = ? AND TimeFrame = ? AND Status != 3"; // Status 3 = Cancelled

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, doctorId);
            ps.setDate(2, new java.sql.Date(appointmentDate.getTime()));
            ps.setInt(3, timeFrame);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error checking time slot availability", ex);
            throw ex;
        }
        return false;
    }

    public List<AppointmentDTO> getAllAppointments(int patientId) throws SQLException {
        String sql = "SELECT a.*, d.FullName as DoctorName, dep.DepartmentName, " +
                    "CONVERT(varchar, a.AppointmentDate, 103) as FormattedDate, " +
                    "CASE a.TimeFrame " +
                    "   WHEN 1 THEN 'Morning (8:00 - 12:00)' " +
                    "   WHEN 2 THEN 'Afternoon (13:00 - 17:00)' " +
                    "   WHEN 3 THEN 'Evening (16:00 - 21:00)' " +
                    "END as TimeFrameText, " +
                    "CASE a.Status " +
                    "   WHEN 1 THEN 'Pending' " +
                    "   WHEN 2 THEN 'Completed' " +
                    "   WHEN 3 THEN 'Cancelled' " +
                    "END as StatusText " +
                    "FROM Appointments a " +
                    "JOIN Users d ON a.DoctorID = d.UserID " +
                    "JOIN Departments dep ON a.DepartmentID = dep.DepartmentID " +
                    "WHERE a.PatientID = ? " +
                    "ORDER BY a.AppointmentDate DESC, a.CreatedAt DESC";

        List<AppointmentDTO> appointments = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, patientId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AppointmentDTO appointment = new AppointmentDTO();
                    appointment.setAppointmentId(rs.getInt("AppointmentID"));
                    appointment.setPatientId(rs.getInt("PatientID"));
                    appointment.setDoctorId(rs.getInt("DoctorID"));
                    appointment.setDoctorName(rs.getString("DoctorName"));
                    appointment.setDepartmentId(rs.getInt("DepartmentID"));
                    appointment.setDepartmentName(rs.getString("DepartmentName"));
                    appointment.setAppointmentDate(rs.getDate("AppointmentDate"));
                    appointment.setFormattedDate(rs.getString("FormattedDate"));
                    appointment.setTimeFrame(rs.getInt("TimeFrame"));
                    appointment.setTimeFrameText(rs.getString("TimeFrameText"));
                    appointment.setStatus(rs.getInt("Status"));
                    appointment.setStatusText(rs.getString("StatusText"));
                    appointment.setNotes(rs.getString("Notes"));
                    appointment.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    
                    appointments.add(appointment);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting appointments", ex);
            throw ex;
        }
        
        return appointments;
    }

    public AppointmentDTO getAppointmentById(int appointmentId) throws SQLException {
        String sql = "SELECT a.*, d.FullName as DoctorName, dep.DepartmentName, " +
                    "CONVERT(varchar, a.AppointmentDate, 103) as FormattedDate, " +
                    "CASE a.TimeFrame " +
                    "   WHEN 1 THEN 'Morning (8:00 - 12:00)' " +
                    "   WHEN 2 THEN 'Afternoon (13:00 - 17:00)' " +
                    "   WHEN 3 THEN 'Evening (16:00 - 21:00)' " +
                    "END as TimeFrameText, " +
                    "CASE a.Status " +
                    "   WHEN 1 THEN 'Pending' " +
                    "   WHEN 2 THEN 'Completed' " +
                    "   WHEN 3 THEN 'Cancelled' " +
                    "END as StatusText " +
                    "FROM Appointments a " +
                    "JOIN Users d ON a.DoctorID = d.UserID " +
                    "JOIN Departments dep ON a.DepartmentID = dep.DepartmentID " +
                    "WHERE a.AppointmentID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, appointmentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    AppointmentDTO appointment = new AppointmentDTO();
                    appointment.setAppointmentId(rs.getInt("AppointmentID"));
                    appointment.setPatientId(rs.getInt("PatientID"));
                    appointment.setDoctorId(rs.getInt("DoctorID"));
                    appointment.setDoctorName(rs.getString("DoctorName"));
                    appointment.setDepartmentId(rs.getInt("DepartmentID"));
                    appointment.setDepartmentName(rs.getString("DepartmentName"));
                    appointment.setAppointmentDate(rs.getDate("AppointmentDate"));
                    appointment.setFormattedDate(rs.getString("FormattedDate"));
                    appointment.setTimeFrame(rs.getInt("TimeFrame"));
                    appointment.setTimeFrameText(rs.getString("TimeFrameText"));
                    appointment.setStatus(rs.getInt("Status"));
                    appointment.setStatusText(rs.getString("StatusText"));
                    appointment.setNotes(rs.getString("Notes"));
                    appointment.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    
                    return appointment;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting appointment by ID", ex);
            throw ex;
        }
        
        return null;
    }

    public boolean cancelAppointment(int appointmentId, String reason) throws SQLException {
        String sql = "UPDATE Appointments SET Status = 3, Notes = ? WHERE AppointmentID = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, reason);
            ps.setInt(2, appointmentId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error canceling appointment", ex);
            throw ex;
        }
    }
}