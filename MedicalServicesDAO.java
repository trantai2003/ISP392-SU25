package ehospital.DAO;

import ehospital.dto.MedicalServicesDTO;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MedicalServicesDAO {
    private static final Logger LOGGER = Logger.getLogger(MedicalServicesDAO.class.getName());

    public List<MedicalServicesDTO> getMedicalServices(String searchTerm, Integer departmentId, 
            Double minPrice, Double maxPrice, int page, int pageSize) throws SQLException {
        List<MedicalServicesDTO> services = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ms.ServiceID, ms.ServiceCode, ms.ServiceName, ms.UnitPrice, ")
           .append("ms.Description, ms.IsActive, ms.CreatedAt, ms.Image, ")
           .append("ms.DepartmentID, d.DepartmentName ")
           .append("FROM MedicalServices ms ")
           .append("LEFT JOIN Departments d ON ms.DepartmentID = d.DepartmentID ")
           .append("WHERE ms.IsActive = 1 ");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append("AND (ms.ServiceName LIKE ? OR ms.ServiceCode LIKE ?) ");
            params.add("%" + searchTerm + "%");
            params.add("%" + searchTerm + "%");
        }

        if (departmentId != null) {
            sql.append("AND ms.DepartmentID = ? ");
            params.add(departmentId);
        }

        if (minPrice != null) {
            sql.append("AND ms.UnitPrice >= ? ");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append("AND ms.UnitPrice <= ? ");
            params.add(maxPrice);
        }

        sql.append("ORDER BY ms.ServiceName ");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                int count = 0;
                int startRow = (page - 1) * pageSize;
                int endRow = page * pageSize;

                while (rs.next()) {
                    if (count >= startRow && count < endRow) {
                        MedicalServicesDTO service = new MedicalServicesDTO();
                        service.setServiceId(rs.getInt("ServiceID"));
                        service.setServiceCode(rs.getString("ServiceCode"));
                        service.setServiceName(rs.getString("ServiceName"));
                        service.setUnitPrice(rs.getDouble("UnitPrice"));
                        service.setDescription(rs.getString("Description"));
                        service.setIsActive(rs.getBoolean("IsActive"));
                        service.setCreatedAt(rs.getDate("CreatedAt"));
                        service.setImage(rs.getString("Image"));
                        service.setDepartmentId(rs.getInt("DepartmentID"));
                        service.setDepartmentName(rs.getString("DepartmentName"));
                        services.add(service);
                    }
                    count++;
                    if (count >= endRow) {
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting medical services", ex);
            ex.printStackTrace();
            throw ex;
        }
        return services;
    }

    public int getTotalMedicalServices(String searchTerm, Integer departmentId, 
            Double minPrice, Double maxPrice) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM MedicalServices ms ")
           .append("LEFT JOIN Departments d ON ms.DepartmentID = d.DepartmentID ")
           .append("WHERE ms.IsActive = 1 ");

        List<Object> params = new ArrayList<>();

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sql.append("AND (ms.ServiceName LIKE ? OR ms.ServiceCode LIKE ?) ");
            params.add("%" + searchTerm + "%");
            params.add("%" + searchTerm + "%");
        }

        if (departmentId != null) {
            sql.append("AND ms.DepartmentID = ? ");
            params.add(departmentId);
        }

        if (minPrice != null) {
            sql.append("AND ms.UnitPrice >= ? ");
            params.add(minPrice);
        }

        if (maxPrice != null) {
            sql.append("AND ms.UnitPrice <= ? ");
            params.add(maxPrice);
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
            LOGGER.log(Level.SEVERE, "Error getting total medical services count", ex);
            ex.printStackTrace();
            throw ex;
        }
        return 0;
    }

    public List<MedicalServicesDTO> getAllDepartments() throws SQLException {
        List<MedicalServicesDTO> departments = new ArrayList<>();
        String sql = "SELECT DepartmentID, DepartmentName FROM Departments WHERE Status = 1 ORDER BY DepartmentName";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MedicalServicesDTO dept = new MedicalServicesDTO();
                dept.setDepartmentId(rs.getInt("DepartmentID"));
                dept.setDepartmentName(rs.getString("DepartmentName"));
                departments.add(dept);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting departments", ex);
            ex.printStackTrace();
            throw ex;
        }
        return departments;
    }

    public MedicalServicesDTO getServiceById(int serviceId) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ms.ServiceID, ms.ServiceCode, ms.ServiceName, ms.UnitPrice, ")
           .append("ms.Description, ms.IsActive, ms.CreatedAt, ms.Image, ")
           .append("ms.DepartmentID, d.DepartmentName ")
           .append("FROM MedicalServices ms ")
           .append("LEFT JOIN Departments d ON ms.DepartmentID = d.DepartmentID ")
           .append("WHERE ms.ServiceID = ? AND ms.IsActive = 1");

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            ps.setInt(1, serviceId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    MedicalServicesDTO service = new MedicalServicesDTO();
                    service.setServiceId(rs.getInt("ServiceID"));
                    service.setServiceCode(rs.getString("ServiceCode"));
                    service.setServiceName(rs.getString("ServiceName"));
                    service.setUnitPrice(rs.getDouble("UnitPrice"));
                    service.setDescription(rs.getString("Description"));
                    service.setIsActive(rs.getBoolean("IsActive"));
                    service.setCreatedAt(rs.getDate("CreatedAt"));
                    service.setImage(rs.getString("Image"));
                    service.setDepartmentId(rs.getInt("DepartmentID"));
                    service.setDepartmentName(rs.getString("DepartmentName"));
                    return service;
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting service by ID: " + serviceId, ex);
            ex.printStackTrace();
            throw ex;
        }
        return null;
    }

    public List<MedicalServicesDTO> getNewestServices(int limit) throws SQLException {
        String sql = "SELECT TOP (?) s.*, d.DepartmentName " +
                    "FROM MedicalServices s " +
                    "JOIN Departments d ON s.DepartmentID = d.DepartmentID " +
                    "WHERE s.IsActive = 1 " +
                    "ORDER BY s.CreatedAt DESC";

        List<MedicalServicesDTO> services = new ArrayList<>();
        
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, limit);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MedicalServicesDTO service = new MedicalServicesDTO();
                    service.setServiceId(rs.getInt("ServiceID"));
                    service.setServiceName(rs.getString("ServiceName"));
                    service.setDescription(rs.getString("Description"));
                    service.setUnitPrice(rs.getDouble("UnitPrice"));
                    service.setDepartmentId(rs.getInt("DepartmentID"));
                    service.setDepartmentName(rs.getString("DepartmentName"));
                    service.setImage(rs.getString("Image"));
                    service.setIsActive(rs.getBoolean("IsActive"));
                    service.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    
                    services.add(service);
                }
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Error getting newest services", ex);
            throw ex;
        }
        
        return services;
    }
} 