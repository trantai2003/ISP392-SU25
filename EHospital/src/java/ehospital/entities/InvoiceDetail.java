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
// TABLE 19: InvoiceDetails Entity
// =============================================
@Table(name = "InvoiceDetails")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class InvoiceDetail {
    @Id
    @Column(name = "InvoiceDetailID")
    Integer invoiceDetailId;
    
    @Column(name = "InvoiceID")
    Integer invoiceId;
    
    @Column(name = "ServiceID")
    Integer serviceId;
    
    @Column(name = "Description")
    String description;
    
    @Column(name = "Quantity")
    Integer quantity;
    
    @Column(name = "UnitPrice")
    Double unitPrice;
    
    @Column(name = "TotalPrice")
    Double totalPrice;
    
    @Column(name = "AppointmentID")
    Integer appointmentId;
}