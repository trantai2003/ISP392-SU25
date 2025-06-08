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
// TABLE 22: HealthInsurance Entity
// =============================================
@Table(name = "HealthInsurance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class HealthInsurance {
    @Id
    @Column(name = "InsuranceID")
    Integer insuranceId;
    
    @Column(name = "PatientID")
    Integer patientId;
    
    @Column(name = "InsuranceNumber")
    String insuranceNumber;
    
    @Column(name = "Provider")
    String provider;
    
    @Column(name = "CoverageStartDate")
    Date coverageStartDate;
    
    @Column(name = "CoverageEndDate")
    Date coverageEndDate;
    
    @Column(name = "IsActive")
    Boolean isActive;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}

