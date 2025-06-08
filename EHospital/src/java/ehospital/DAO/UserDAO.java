/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.DAO;

import ehospital.JDBC.GenericDAO;
import ehospital.anotation.FindBy;
import ehospital.anotation.Query;
import ehospital.entities.User;
import static ehospital.constant.ServerConnectionInfo.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class UserDAO extends GenericDAO<User, Integer> {

    public UserDAO() {
        super(User.class);
    }

    @Query(sql = """
                 select * from users where
                 (Username = ? or Email = ?) and
                 PasswordHash = ? and
                 RoleID = ? and
                 IsActive = 1
                 """)
    public User login(String account, String password, int roleId) throws SQLException {
        List<User> findingUsers = executeQueryFind(account, account, password, roleId);
        if (!findingUsers.isEmpty()) {
            return findingUsers.get(0);
        }
        return null;
    }

    @FindBy(columns = "RoleID")
    public List<User> getAllByRoleID(Integer roleID) throws SQLException{
        return findByAnd(roleID);
    }
    
    @FindBy(columns = {"RoleID", "IsActive"})
    public List<User> findByRoleID(Integer roleID) throws SQLException {
        return findByAnd(roleID, true);
    }
    
    public boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean isEmailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean isUsernameExists(String username, int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Username = ? and UserID != " + id;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public boolean isEmailExists(String email, int id) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ? and UserID != " + id;

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    public void saveResetToken(String email, String token, Date expiryDate) throws SQLException {
        String sql = "UPDATE Users SET ResetToken = ?, ResetTokenExpiry = ? WHERE Email = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setTimestamp(2, new java.sql.Timestamp(expiryDate.getTime()));
            ps.setString(3, email);

            ps.executeUpdate();
        }
    }

    public User validateResetToken(String token) throws SQLException {
        String sql = "SELECT * FROM Users WHERE ResetToken = ? AND ResetTokenExpiry > ? AND IsActive = 1";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, token);
            ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("UserID"));
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setFullName(rs.getString("FullName"));
                    user.setRoleId(rs.getInt("RoleID"));
                    user.setRoleFunc();
                    return user;
                }
            }
        }

        return null;
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
        String sql = "UPDATE Users SET PasswordHash = ?, ResetToken = NULL, ResetTokenExpiry = NULL WHERE UserID = ?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newPassword); // In production, use proper password hashing
            ps.setInt(2, userId);

            ps.executeUpdate();
        }
    }
}
