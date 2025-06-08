/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.entities;

import ehospital.DAO.DepartmentDAO;
import ehospital.anotation.*;
import java.sql.SQLException;
import java.util.Date;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author admin
 */
// =============================================
// TABLE 4: Employees Entity
// =============================================
@Table(name = "Employees")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Employee {

    @Id(auto = false)
    @Column(name = "UserID")
    Integer userId;

    @Column(name = "EmployeeCode")
    String employeeCode;

    @Column(name = "Gender")
    String gender;

    @Column(name = "DateOfBirth")
    Date dateOfBirth;

    @Column(name = "Address")
    String address;

    @Column(name = "DepartmentID")
    Integer departmentId;

    @Column(name = "Position")
    String position;

    @Column(name = "Specialization")
    String specialization;

    @Column(name = "Education")
    String education;

    @Column(name = "Degree")
    String degree;

    @Column(name = "ExperienceYears")
    Integer experienceYears;

    @Column(name = "ExperienceDetails")
    String experienceDetails;

    @Column(name = "HireDate")
    Date hireDate;

    @Column(name = "IsActive")
    Boolean isActive;

    @Column(name = "CreatedAt")
    Date createdAt;

    @Column(name = "UpdatedAt")
    Date updatedAt;

    @Column(name = "Description")
    String description;

    @Column(name = "Salary")
    Double salary;

    Department department;

    public void setDepartmentFunc() {
        try {
            this.department = new DepartmentDAO().getById(departmentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
