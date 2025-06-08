/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ehospital.entities;

import ehospital.anotation.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
/**
 *
 * @author admin
 */
// =============================================
// TABLE 7: Shifts Entity
// =============================================
@Table(name = "Shifts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Shift {
    @Id
    @Column(name = "ShiftID")
    Integer shiftId;
    
    @Column(name = "ShiftName")
    String shiftName;
    
    @Column(name = "StartTime")
    String startTime;
    
    @Column(name = "EndTime")
    String endTime;
    
    @Column(name = "DaysOfWeek")
    Integer daysOfWeek;
    
    @Column(name = "Description")
    String description;
}
