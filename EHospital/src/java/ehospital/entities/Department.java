/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.entities;

import ehospital.anotation.*;
import java.util.Date;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author admin
 */
// =============================================
// TABLE 3: Departments Entity
// =============================================
@Table(name = "Departments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Department {
    @Id
    @Column(name = "DepartmentID")
    Integer departmentId;
    
    @Column(name = "DepartmentName")
    String departmentName;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "Status")
    Boolean status;
}