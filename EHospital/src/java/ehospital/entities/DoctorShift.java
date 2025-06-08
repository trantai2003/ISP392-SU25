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
// TABLE 9: DoctorShifts Entity
// =============================================
@Table(name = "DoctorShifts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class DoctorShift {
    @Id
    @Column(name = "DoctorShiftID")
    Integer doctorShiftId;
    
    @Column(name = "DoctorID")
    Integer doctorId;
    
    @Column(name = "ShiftID")
    Integer shiftId;
    
    @Column(name = "ShiftDate")
    Date shiftDate;
    
    @Column(name = "AssignedAt")
    Date assignedAt;
    
    @Column(name = "Notes")
    String notes;
}

