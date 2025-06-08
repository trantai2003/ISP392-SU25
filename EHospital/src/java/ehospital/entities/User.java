/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.entities;

import ehospital.DAO.EmployeeDAO;
import ehospital.DAO.PatientDAO;
import ehospital.DAO.RoleDAO;
import ehospital.anotation.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author admin
 */
// =============================================
// TABLE 2: Users Entity
// =============================================
@Table(name = "Users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class User {

    @Id
    @Column(name = "UserID")
    Integer userId;

    @Column(name = "Username")
    String username;

    @Column(name = "PasswordHash")
    String passwordHash;

    @Column(name = "FullName")
    String fullName;

    @Column(name = "Email")
    String email;

    @Column(name = "Phone")
    String phone;

    @Column(name = "IsActive")
    Boolean isActive;

    @Column(name = "CreatedAt")
    Date createdAt;

    @Column(name = "UpdatedAt")
    Date updatedAt;

    @Column(name = "RoleID")
    Integer roleId;

    @Column(name = "ResetToken")
    String resetToken;

    @Column(name = "ResetTokenExpiry")
    Date resetTokenExpiry;

    Role role;

    Employee employee;

    Patient patient;

    public List<String> getRoleStrings() {
        List<String> roleStrings = new ArrayList<>();
        roleStrings.add(this.role.getRoleName());
        return roleStrings;
    }

    public void setRoleFunc() {
        try {
            this.role = new RoleDAO().getById(this.roleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setEmployeeFunc() {
        try {
            this.employee = new EmployeeDAO().getById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPatientFunc() {
        try {
            this.patient = new PatientDAO().getById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
