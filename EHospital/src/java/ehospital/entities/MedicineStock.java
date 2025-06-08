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
// TABLE 13: MedicineStock Entity
// =============================================
@Table(name = "MedicineStock")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MedicineStock {
    @Id
    @Column(name = "StockID")
    Integer stockId;
    
    @Column(name = "MedicineID")
    Integer medicineId;
    
    @Column(name = "Quantity")
    Integer quantity;
    
    @Column(name = "MinimumStock")
    Integer minimumStock;
    
    @Column(name = "ExpiryDate")
    Date expiryDate;
    
    @Column(name = "BatchNumber")
    String batchNumber;
    
    @Column(name = "LastUpdated")
    Date lastUpdated;
}
