/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.LabResult;
import ehospital.dto.LabResultDTO;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class LabResultDAO extends GenericDAO<LabResult, Integer>{
    
    private static final Logger LOGGER = Logger.getLogger(LabResultDAO.class.getName());
    
    public LabResultDAO() {
        super(LabResult.class);
    }
    
    public List<LabResultDTO> getLabResultsByExamId(int examId) throws SQLException {
        String sql = "SELECT l.*, d.FullName as DoctorName, " +
                    "CONVERT(varchar, l.TestDate, 103) as FormattedDate " +
                    "FROM LabResults l " +
                    "JOIN Users d ON l.DoctorID = d.UserID " +
                    "WHERE l.ExamID = ? " +
                    "ORDER BY l.TestDate DESC";

        List<LabResultDTO> results = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, examId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LabResultDTO result = new LabResultDTO();
                    result.setLabResultId(rs.getInt("LabResultID"));
                    result.setExamId(rs.getInt("ExamID"));
                    result.setTestType(rs.getString("TestType"));
                    result.setTestDate(rs.getDate("TestDate"));
                    result.setFormattedDate(rs.getString("FormattedDate"));
                    result.setResult(rs.getString("Result"));
                    result.setNormalRange(rs.getString("NormalRange"));
                    result.setUnit(rs.getString("Unit"));
                    result.setDoctorId(rs.getInt("DoctorID"));
                    result.setDoctorName(rs.getString("DoctorName"));
                    result.setNotes(rs.getString("Notes"));
                    result.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    
                    results.add(result);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting lab results by exam ID", ex);
            throw ex;
        }
        
        return results;
    }
}
