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
// TABLE 15: PrescriptionDetails Entity
// =============================================
@Table(name = "PrescriptionDetails")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class PrescriptionDetail {
    @Id
    @Column(name = "PrescriptionDetailID")
    Integer prescriptionDetailId;
    
    @Column(name = "PrescriptionID")
    Integer prescriptionId;
    
    @Column(name = "MedicineID")
    Integer medicineId;
    
    @Column(name = "Quantity")
    Integer quantity;
    
    @Column(name = "Dosage")
    String dosage;
    
    @Column(name = "Frequency")
    String frequency;
    
    @Column(name = "Duration")
    String duration;
    
    @Column(name = "Instructions")
    String instructions;
    
    @Column(name = "UnitPrice")
    Double unitPrice;
}