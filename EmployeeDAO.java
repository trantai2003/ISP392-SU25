package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.entities.Employee;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ehospital.dto.DoctorDTO;
import ehospital.dto.EmployeeDTO;

public class EmployeeDAO extends GenericDAO<Employee, Integer> {
    
    private static final Logger LOGGER = Logger.getLogger(EmployeeDAO.class.getName());
    
    public EmployeeDAO() {
        super(Employee.class);
    }
    
    public List<String> getAllDepartments() throws SQLException {
        String sql = "SELECT DISTINCT DepartmentName FROM Departments WHERE DepartmentName IS NOT NULL ORDER BY DepartmentName";
        List<String> departments = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                departments.add(rs.getString("DepartmentName"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting departments", ex);
            ex.printStackTrace();
            throw ex;
        }
        
        return departments;
    }


    public List<DoctorDTO> getDoctors(String searchTerm, Integer departmentId, String degree, String gender, int page, int pagesize) throws SQLException {
        List<DoctorDTO> doctors = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.Phone, ")
           .append("e.EmployeeCode, e.Gender, e.DateOfBirth, e.Address, e.DepartmentID, ")
           .append("e.Position, e.Specialization, e.Education, e.Degree, ")
           .append("e.ExperienceYears, e.ExperienceDetails, e.HireDate, ")
           .append("e.IsActive, e.CreatedAt, e.UpdatedAt, e.Description, ")
           .append("e.Salary, e.Avatar ")
           .append("FROM Users u ")
           .append("INNER JOIN Employees e ON u.UserID = e.UserID ")
           .append("WHERE u.RoleID = 2 AND u.IsActive = 1 ");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append("AND (u.FullName LIKE ? OR u.Phone LIKE ?) ");
            params.add("%" + searchTerm + "%");
            params.add("%" + searchTerm + "%");
        }

        if (departmentId != null) {
            sql.append("AND e.DepartmentID = ? ");
            params.add(departmentId);
        }

        if (degree != null && !degree.trim().isEmpty()) {
            sql.append("AND e.Degree = ? ");
            params.add(degree);
        }

        if (gender != null && !gender.equals("All")) {
            sql.append("AND e.Gender = ? ");
            params.add(gender);
        }

        sql.append("ORDER BY u.FullName ");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                int startRow = (page - 1) * pagesize;
                int endRow = page * pagesize;

                while (rs.next()) {
                    if (count >= startRow && count < endRow) {
                        DoctorDTO doctor = new DoctorDTO();
                        doctor.setUserId(rs.getInt("UserID"));
                        doctor.setFullName(rs.getString("FullName"));
                        doctor.setEmail(rs.getString("Email"));
                        doctor.setPhone(rs.getString("Phone"));
                        doctor.setEmployeeCode(rs.getString("EmployeeCode"));
                        doctor.setGender(rs.getString("Gender"));
                        doctor.setDateOfBirth(rs.getDate("DateOfBirth"));
                        doctor.setAddress(rs.getString("Address"));
                        doctor.setDepartmentId(rs.getInt("DepartmentID"));
                        doctor.setPosition(rs.getString("Position"));
                        doctor.setSpecialization(rs.getString("Specialization"));
                        doctor.setEducation(rs.getString("Education"));
                        doctor.setDegree(rs.getString("Degree"));
                        doctor.setExperienceYears(rs.getInt("ExperienceYears"));
                        doctor.setExperienceDetails(rs.getString("ExperienceDetails"));
                        doctor.setHireDate(rs.getDate("HireDate"));
                        doctor.setActive(rs.getBoolean("IsActive"));
                        doctor.setCreatedAt(rs.getTimestamp("CreatedAt"));
                        doctor.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                        doctor.setDescription(rs.getString("Description"));
                        doctor.setSalary(rs.getDouble("Salary"));
                        doctor.setAvatar(rs.getString("Avatar"));
                        doctors.add(doctor);
                    }
                    count++;
                    if (count >= endRow) {
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting doctors", ex);
            ex.printStackTrace();
            throw ex;
        }
        return doctors;
    }

    public int getTotalDoctors(String searchTerm, Integer departmentId, String degree, String gender) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM Users u ")
           .append("INNER JOIN Employees e ON u.UserID = e.UserID ")
           .append("WHERE u.RoleID = 2 AND u.IsActive = 1 ");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append("AND (u.FullName LIKE ? OR u.Phone LIKE ?) ");
            params.add("%" + searchTerm + "%");
            params.add("%" + searchTerm + "%");
        }

        if (departmentId != null) {
            sql.append("AND e.DepartmentID = ? ");
            params.add(departmentId);
        }

        if (degree != null && !degree.trim().isEmpty()) {
            sql.append("AND e.Degree = ? ");
            params.add(degree);
        }

        if (gender != null && !gender.equals("All")) {
            sql.append("AND e.Gender = ? ");
            params.add(gender);
        }

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting total doctors count", ex);
            ex.printStackTrace();
            throw ex;
        }
        return 0;
    }

    public List<String> getAllDegrees() throws SQLException {
        List<String> degrees = new ArrayList<>();
        String sql = "SELECT DISTINCT Degree FROM Employees WHERE Degree IS NOT NULL ORDER BY Degree";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                degrees.add(rs.getString("Degree"));
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting degrees", ex);
            ex.printStackTrace();
            throw ex;
        }
        return degrees;
    }

    public DoctorDTO getDoctorById(int doctorId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT u.UserID, u.FullName, u.Email, u.Phone, ")
           .append("e.EmployeeCode, e.Gender, e.DateOfBirth, e.Address, e.DepartmentID, ")
           .append("e.Position, e.Specialization, e.Education, e.Degree, ")
           .append("e.ExperienceYears, e.ExperienceDetails, e.HireDate, ")
           .append("e.IsActive, e.CreatedAt, e.UpdatedAt, e.Description, ")
           .append("e.Salary, e.Avatar, d.DepartmentName ")
           .append("FROM Users u ")
           .append("INNER JOIN Employees e ON u.UserID = e.UserID ")
           .append("LEFT JOIN Departments d ON e.DepartmentID = d.DepartmentID ")
           .append("WHERE u.UserID = ? AND u.RoleID = 2 AND u.IsActive = 1");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    DoctorDTO doctor = new DoctorDTO();
                    doctor.setUserId(rs.getInt("UserID"));
                    doctor.setFullName(rs.getString("FullName"));
                    doctor.setEmail(rs.getString("Email"));
                    doctor.setPhone(rs.getString("Phone"));
                    doctor.setEmployeeCode(rs.getString("EmployeeCode"));
                    doctor.setGender(rs.getString("Gender"));
                    doctor.setDateOfBirth(rs.getDate("DateOfBirth"));
                    doctor.setAddress(rs.getString("Address"));
                    doctor.setDepartmentId(rs.getInt("DepartmentID"));
                    doctor.setDepartmentName(rs.getString("DepartmentName"));
                    doctor.setPosition(rs.getString("Position"));
                    doctor.setSpecialization(rs.getString("Specialization"));
                    doctor.setEducation(rs.getString("Education"));
                    doctor.setDegree(rs.getString("Degree"));
                    doctor.setExperienceYears(rs.getInt("ExperienceYears"));
                    doctor.setExperienceDetails(rs.getString("ExperienceDetails"));
                    doctor.setHireDate(rs.getDate("HireDate"));
                    doctor.setActive(rs.getBoolean("IsActive"));
                    doctor.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    doctor.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    doctor.setDescription(rs.getString("Description"));
                    doctor.setSalary(rs.getDouble("Salary"));
                    doctor.setAvatar(rs.getString("Avatar"));
                    return doctor;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting doctor by ID: " + doctorId, ex);
            ex.printStackTrace();
            throw ex;
        }
        return null;
    }

    public List<EmployeeDTO> getDoctorsByDepartment(int departmentId) throws SQLException {
        List<EmployeeDTO> doctors = new ArrayList<>();
        String sql = "SELECT e.UserID, e.FullName, e.Degree, e.DepartmentID, d.DepartmentName, " +
                    "e.Specialization, e.Avatar, e.IsActive " +
                    "FROM Employees e " +
                    "LEFT JOIN Departments d ON e.DepartmentID = d.DepartmentID " +
                    "WHERE e.DepartmentID = ?  AND e.IsActive = 1 " + // RoleID 2 is for doctors
                    "ORDER BY e.FullName";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, departmentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeDTO doctor = new EmployeeDTO();
                    doctor.setEmployeeId(rs.getInt("UserID"));
                    doctor.setFullName(rs.getString("FullName"));
                    doctor.setDegree(rs.getString("Degree"));
                    doctor.setDepartmentId(rs.getInt("DepartmentID"));
                    doctor.setDepartmentName(rs.getString("DepartmentName"));
                    doctor.setSpecialization(rs.getString("Specialization"));
                    doctor.setAvatar(rs.getString("Avatar"));
                    doctor.setIsActive(rs.getBoolean("IsActive"));
                    doctors.add(doctor);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting doctors by department: " + departmentId, ex);
            throw ex;
        }
        return doctors;
    }

    public List<DoctorDTO> getTopDoctorsByExperience(int limit) throws SQLException {
        String sql = "SELECT TOP (?) u.UserID, u.FullName, u.Email, u.Phone, " +
                    "e.EmployeeCode, e.Gender, e.DateOfBirth, e.Address, e.DepartmentID, " +
                    "e.Position, e.Specialization, e.Education, e.Degree, " +
                    "e.ExperienceYears, e.ExperienceDetails, e.HireDate, " +
                    "e.IsActive, e.CreatedAt, e.UpdatedAt, e.Description, " +
                    "e.Salary, e.Avatar, d.DepartmentName, " +
                    "CONVERT(varchar, e.ExperienceYears) + ' years' as ExperienceText " +
                    "FROM Users u " +
                    "INNER JOIN Employees e ON u.UserID = e.UserID " +
                    "JOIN Departments d ON e.DepartmentID = d.DepartmentID " +
                    "WHERE u.RoleID = 2 AND u.IsActive = 1 " + // RoleID 2 = Doctor
                    "ORDER BY e.ExperienceYears DESC";

        List<DoctorDTO> doctors = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DoctorDTO doctor = new DoctorDTO();
                    doctor.setUserId(rs.getInt("UserID"));
                    doctor.setFullName(rs.getString("FullName"));
                    doctor.setEmail(rs.getString("Email"));
                    doctor.setPhone(rs.getString("Phone"));
                    doctor.setEmployeeCode(rs.getString("EmployeeCode"));
                    doctor.setGender(rs.getString("Gender"));
                    doctor.setDateOfBirth(rs.getDate("DateOfBirth"));
                    doctor.setAddress(rs.getString("Address"));
                    doctor.setDepartmentId(rs.getInt("DepartmentID"));
                    doctor.setDepartmentName(rs.getString("DepartmentName"));
                    doctor.setPosition(rs.getString("Position"));
                    doctor.setSpecialization(rs.getString("Specialization"));
                    doctor.setEducation(rs.getString("Education"));
                    doctor.setDegree(rs.getString("Degree"));
                    doctor.setExperienceYears(rs.getInt("ExperienceYears"));
                    doctor.setExperienceDetails(rs.getString("ExperienceDetails"));
                    doctor.setHireDate(rs.getDate("HireDate"));
                    doctor.setActive(rs.getBoolean("IsActive"));
                    doctor.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    doctor.setUpdatedAt(rs.getTimestamp("UpdatedAt"));
                    doctor.setDescription(rs.getString("Description"));
                    doctor.setSalary(rs.getDouble("Salary"));
                    doctor.setAvatar(rs.getString("Avatar"));
                    
                    doctors.add(doctor);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting top doctors by experience", ex);
            throw ex;
        }
        
        return doctors;
    }
}