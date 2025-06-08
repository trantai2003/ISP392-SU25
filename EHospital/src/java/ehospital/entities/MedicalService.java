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
// TABLE 17: MedicalServices Entity
// =============================================
@Table(name = "MedicalServices")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class MedicalService {
    @Id
    @Column(name = "ServiceID")
    Integer serviceId;
    
    @Column(name = "CategoryID")
    Integer categoryId;
    
    @Column(name = "ServiceCode")
    String serviceCode;
    
    @Column(name = "ServiceName")
    String serviceName;
    
    @Column(name = "UnitPrice")
    Double unitPrice;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "IsActive")
    Boolean isActive;
    
    @Column(name = "CreatedAt")
    Date createdAt;
    
    @Column(name = "Image")
    String image;
}