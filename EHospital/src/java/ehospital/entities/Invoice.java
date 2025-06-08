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
// TABLE 18: Invoices Entity
// =============================================
@Table(name = "Invoices")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Invoice {
    @Id
    @Column(name = "InvoiceID")
    Integer invoiceId;
    
    @Column(name = "InvoiceNumber")
    String invoiceNumber;
    
    @Column(name = "PatientID")
    Integer patientId;
    
    @Column(name = "CreatedDate")
    Date createdDate;
    
    @Column(name = "TotalAmount")
    Double totalAmount;
    
    @Column(name = "PaidAmount")
    Double paidAmount;
    
    @Column(name = "Status")
    String status;
    
    @Column(name = "Notes")
    String notes;
}
