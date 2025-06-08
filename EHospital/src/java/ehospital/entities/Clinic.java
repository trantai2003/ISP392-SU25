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
// TABLE 6: Clinics Entity
// =============================================
@Table(name = "Clinics")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Clinic {
    @Id
    @Column(name = "ClinicID")
    Integer clinicId;
    
    @Column(name = "ClinicName")
    String clinicName;
    
    @Column(name = "Floor")
    String floor;
    
    @Column(name = "PhoneNumber")
    String phoneNumber;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "IsActive")
    Boolean isActive;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "DepartmentID")
    Integer departmentId;
}
