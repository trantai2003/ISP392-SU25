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
// TABLE 11: Examinations Entity
// =============================================
@Table(name = "Examinations")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Examination {
    @Id
    @Column(name = "ExamID")
    Integer examId;
    
    @Column(name = "RecordID")
    Integer recordId;
    
    @Column(name = "AppointmentID")
    Integer appointmentId;
    
    @Column(name = "ExamDate")
    Date examDate;
    
    @Column(name = "DoctorID")
    Integer doctorId;
    
    @Column(name = "Symptoms")
    String symptoms;
    
    @Column(name = "Diagnosis")
    String diagnosis;
    
    @Column(name = "VitalSigns")
    String vitalSigns;
    
    @Column(name = "Treatment")
    String treatment;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}