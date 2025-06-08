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
// TABLE 10: PatientRecords Entity
// =============================================
@Table(name = "PatientRecords")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class PatientRecord {
    @Id
    @Column(name = "RecordID")
    Integer recordId;
    
    @Column(name = "PatientID")
    Integer patientId;
    
    @Column(name = "AdmissionDate")
    Date admissionDate;
    
    @Column(name = "DischargeDate")
    Date dischargeDate;
    
    @Column(name = "AdmissionReason")
    String admissionReason;
    
    @Column(name = "DischargeReason")
    String dischargeReason;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}