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
// TABLE 21: MedicalEquipment Entity
// =============================================
@Table(name = "MedicalEquipment")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MedicalEquipment {
    @Id
    @Column(name = "EquipmentID")
    Integer equipmentId;
    
    @Column(name = "EquipmentName")
    String equipmentName;
    
    @Column(name = "SerialNumber")
    String serialNumber;
    
    @Column(name = "EquipmentType")
    String equipmentType;
    
    @Column(name = "Manufacturer")
    String manufacturer;
    
    @Column(name = "PurchaseDate")
    Date purchaseDate;
    
    @Column(name = "WarrantyEndDate")
    Date warrantyEndDate;
    
    @Column(name = "Status")
    Integer status;
    
    @Column(name = "ClinicID")
    Integer clinicId;
    
    @Column(name = "LastMaintenanceDate")
    Date lastMaintenanceDate;
    
    @Column(name = "Notes")
    String notes;
    
    @Column(name = "CreatedAt")
    Date createdAt;
}