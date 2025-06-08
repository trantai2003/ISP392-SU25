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
// TABLE 12: Medicines Entity
// =============================================
@Table(name = "Medicines")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Medicine {
    @Id
    @Column(name = "MedicineID")
    Integer medicineId;
    
    @Column(name = "MedicineCode")
    String medicineCode;
    
    @Column(name = "Name")
    String name;
    
    @Column(name = "ActiveIngredient")
    String activeIngredient;
    
    @Column(name = "Unit")
    String unit;
    
    @Column(name = "Price")
    Double price;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "IsActive")
    Boolean isActive;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "UpdatedAt")
    Date updatedAt;
}