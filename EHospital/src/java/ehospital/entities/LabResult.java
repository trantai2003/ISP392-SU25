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
// TABLE 16: LabResults Entity
// =============================================
@Table(name = "LabResults")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class LabResult {
    @Id
    @Column(name = "LabResultID")
    Integer labResultId;
    
    @Column(name = "ExamID")
    Integer examId;
    
    @Column(name = "TestType")
    String testType;
    
    @Column(name = "TestDate")
    Date testDate;
    
    @Column(name = "Result")
    String result;
    
    @Column(name = "NormalRange")
    String normalRange;
    
    @Column(name = "Unit")
    String unit;
    
    @Column(name = "DoctorID")
    Integer doctorId;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}
