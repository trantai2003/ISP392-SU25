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
// TABLE 8: Appointments Entity
// =============================================
@Table(name = "Appointments")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Appointment {
    @Id
    @Column(name = "AppointmentID")
    Integer appointmentId;
    
    @Column(name = "PatientID")
    Integer patientId;
    
    @Column(name = "DoctorID")
    Integer doctorId;
    
    @Column(name = "AppointmentDate")
    Date appointmentDate;
    
    @Column(name = "Status")
    Integer status;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "DepartmentID")
    Integer departmentId;
    
    @Column(name = "TimeFrame")
    Integer timeFrame;
}