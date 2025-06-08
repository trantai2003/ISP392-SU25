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
// TABLE 5: Patients Entity
// =============================================
@Table(name = "Patients")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Patient {
    @Id(auto = false)
    @Column(name = "UserID")
    Integer userId;
    
    @Column(name = "PatientCode")
    String patientCode;
    
    @Column(name = "Gender")
    String gender;
    
    @Column(name = "DateOfBirth")
    Date dateOfBirth;
    
    @Column(name = "Address")
    String address;
    
    @Column(name = "InsuranceNumber")
    String insuranceNumber;
    
    @Column(name = "EmergencyContact")
    String emergencyContact;
    
    @Column(name = "EmergencyPhone")
    String emergencyPhone;
    
    @Column(name = "BloodType")
    String bloodType;
    
    @Column(name = "Allergies")
    String allergies;
    
    @Column(name = "ChronicDiseases")
    String chronicDiseases;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "UpdatedAt")
    Date updatedAt;
}